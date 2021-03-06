/**
 * blackduck-alert
 *
 * Copyright (C) 2019 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.alert.provider.blackduck.tasks;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.synopsys.integration.alert.common.FilePersistenceUtil;
import com.synopsys.integration.alert.common.model.DateRange;
import com.synopsys.integration.alert.database.entity.NotificationContent;
import com.synopsys.integration.alert.provider.blackduck.BlackDuckProperties;
import com.synopsys.integration.alert.provider.blackduck.BlackDuckProvider;
import com.synopsys.integration.alert.workflow.NotificationManager;
import com.synopsys.integration.alert.workflow.scheduled.ScheduledTask;
import com.synopsys.integration.blackduck.api.generated.view.NotificationView;
import com.synopsys.integration.blackduck.rest.BlackDuckRestConnection;
import com.synopsys.integration.blackduck.service.BlackDuckServicesFactory;
import com.synopsys.integration.blackduck.service.NotificationService;
import com.synopsys.integration.log.Slf4jIntLogger;
import com.synopsys.integration.rest.RestConstants;

@Component
public class BlackDuckAccumulator extends ScheduledTask {
    public static final String DEFAULT_CRON_EXPRESSION = "0 0/1 * 1/1 * *";
    public static final String ENCODING = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(BlackDuckAccumulator.class);

    private final BlackDuckProperties blackDuckProperties;
    private final NotificationManager notificationManager;
    private final FilePersistenceUtil filePersistenceUtil;
    private final String searchRangeFileName;

    @Autowired
    public BlackDuckAccumulator(final TaskScheduler taskScheduler, final BlackDuckProperties blackDuckProperties,
        final NotificationManager notificationManager, final FilePersistenceUtil filePersistenceUtil) {
        super(taskScheduler, "blackduck-accumulator-task");
        this.blackDuckProperties = blackDuckProperties;
        this.notificationManager = notificationManager;
        this.filePersistenceUtil = filePersistenceUtil;
        // TODO: do not store a file with the timestamp save this information into a database table for tasks.  Perhaps a task metadata object stored in the database.
        searchRangeFileName = String.format("%s-last-search.txt", getTaskName());
    }

    public String getSearchRangeFileName() {
        return searchRangeFileName;
    }

    public String formatDate(final Date date) {
        return RestConstants.formatDate(date);
    }

    @Override
    public void run() {
        accumulate();
    }

    public void accumulate() {
        logger.info("### Accumulator Starting Operation...");
        try {
            if (!filePersistenceUtil.exists(getSearchRangeFileName())) {
                initializeSearchRangeFile();
            }
            final DateRange dateRange = createDateRange(getSearchRangeFileName());
            final Date nextSearchStartTime = accumulate(dateRange);
            final String nextSearchStartString = formatDate(nextSearchStartTime);
            logger.info("Accumulator Next Range Start Time: {} ", nextSearchStartString);
            saveNextSearchStart(nextSearchStartString);
        } catch (final IOException ex) {
            logger.error("Error occurred accumulating data! ", ex);
        } finally {
            final Optional<Long> nextRun = getMillisecondsToNextRun();
            if (nextRun.isPresent()) {
                final Long seconds = TimeUnit.MILLISECONDS.toSeconds(nextRun.get());
                logger.debug("Accumulator next run: {} seconds", seconds);
            }
            logger.info("### Accumulator Finished Operation.");
        }
    }

    protected void initializeSearchRangeFile() throws IOException {
        ZonedDateTime zonedDate = ZonedDateTime.now();
        zonedDate = zonedDate.withZoneSameInstant(ZoneOffset.UTC);
        zonedDate = zonedDate.withSecond(0).withNano(0);
        final Date date = Date.from(zonedDate.toInstant());
        filePersistenceUtil.writeToFile(getSearchRangeFileName(), formatDate(date));
    }

    protected void saveNextSearchStart(final String nextSearchStart) throws IOException {
        filePersistenceUtil.writeToFile(getSearchRangeFileName(), nextSearchStart);
    }

    protected DateRange createDateRange(final String lastSearchFileName) {
        ZonedDateTime zonedEndDate = ZonedDateTime.now();
        zonedEndDate = zonedEndDate.withZoneSameInstant(ZoneOffset.UTC);
        zonedEndDate = zonedEndDate.withSecond(0).withNano(0);
        ZonedDateTime zonedStartDate = zonedEndDate;
        final Date endDate = Date.from(zonedEndDate.toInstant());

        Date startDate = Date.from(zonedStartDate.toInstant());
        try {
            if (filePersistenceUtil.exists(lastSearchFileName)) {
                final String lastRunValue = readSearchStartTime(lastSearchFileName);
                final Date startTime = parseDateString(lastRunValue);
                zonedStartDate = ZonedDateTime.ofInstant(startTime.toInstant(), zonedEndDate.getZone());
            } else {
                zonedStartDate = zonedEndDate.minusMinutes(1);
            }
            startDate = Date.from(zonedStartDate.toInstant());
        } catch (final IOException | ParseException e) {
            logger.error("Error creating date range", e);
        }
        return DateRange.of(startDate, endDate);
    }

    protected String readSearchStartTime(final String lastSearchFileName) throws IOException {
        return filePersistenceUtil.readFromFile(lastSearchFileName);
    }

    protected Date parseDateString(final String date) throws ParseException {
        return RestConstants.parseDateString(date);
    }

    protected Date accumulate(final DateRange dateRange) {
        final Date currentStartTime = dateRange.getStart();
        Optional<Date> latestNotificationCreatedAtDate = Optional.empty();

        final List<NotificationView> notifications = read(dateRange);
        if (!notifications.isEmpty()) {
            final List<NotificationView> sortedNotifications = sort(notifications);
            final List<NotificationContent> contentList = process(sortedNotifications);
            write(contentList);
            latestNotificationCreatedAtDate = getLatestNotificationCreatedAtDate(sortedNotifications);
        }
        return calculateNextStartTime(latestNotificationCreatedAtDate, currentStartTime);
    }

    protected List<NotificationView> read(final DateRange dateRange) {
        final Optional<BlackDuckRestConnection> optionalConnection = blackDuckProperties.createRestConnectionAndLogErrors(logger);
        if (optionalConnection.isPresent()) {
            try {
                final BlackDuckServicesFactory blackDuckServicesFactory = blackDuckProperties.createBlackDuckServicesFactory(optionalConnection.get(), new Slf4jIntLogger(logger));
                final Date startDate = dateRange.getStart();
                final Date endDate = dateRange.getEnd();
                logger.info("Accumulating Notifications Between {} and {} ", RestConstants.formatDate(startDate), RestConstants.formatDate(endDate));
                final NotificationService notificationService = blackDuckServicesFactory.createNotificationService();

                final List<NotificationView> notificationViews = notificationService.getAllNotifications(startDate, endDate);
                logger.debug("Read Notification Count: {}", notificationViews.size());
                return notificationViews;
            } catch (final Exception ex) {
                logger.error("Error Reading notifications", ex);
            }
        }
        return List.of();
    }

    protected List<NotificationContent> process(final List<NotificationView> notifications) {
        logger.info("Processing accumulated notifications");
        return notifications
                   .stream()
                   .map(this::createContent)
                   .collect(Collectors.toList());
    }

    protected void write(final List<NotificationContent> contentList) {
        logger.info("Writing Notifications...");
        contentList.forEach(notificationManager::saveNotification);
    }

    private List<NotificationView> sort(final List<NotificationView> notifications) {
        return notifications
                   .stream()
                   .sorted(Comparator.comparing(NotificationView::getCreatedAt))
                   .collect(Collectors.toList());
    }

    private NotificationContent createContent(final NotificationView notification) {
        final Date createdAt = Date.from(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toInstant());
        final Date providerCreationTime = notification.getCreatedAt();
        final String provider = BlackDuckProvider.COMPONENT_NAME;
        final String notificationType = notification.getType().name();
        final String jsonContent = notification.getJson();
        final NotificationContent content = new NotificationContent(createdAt, provider, providerCreationTime, notificationType, jsonContent);
        return content;
    }

    // Expects that the notifications are sorted oldest to newest
    private Optional<Date> getLatestNotificationCreatedAtDate(final List<NotificationView> sortedNotificationList) {
        if (!sortedNotificationList.isEmpty()) {
            final int lastIndex = sortedNotificationList.size() - 1;
            final NotificationView notificationView = sortedNotificationList.get(lastIndex);
            return Optional.of(notificationView.getCreatedAt());
        }
        return Optional.empty();
    }

    private Date calculateNextStartTime(final Optional<Date> latestNotificationCreatedAt, final Date currentStartDate) {
        Date newStartDate = currentStartDate;
        if (latestNotificationCreatedAt.isPresent()) {
            final Date latestNotification = latestNotificationCreatedAt.get();
            ZonedDateTime newSearchStart = ZonedDateTime.ofInstant(latestNotification.toInstant(), ZoneOffset.UTC);
            // increment 1 millisecond
            newSearchStart = newSearchStart.plusNanos(1000000);
            newStartDate = Date.from(newSearchStart.toInstant());
            logger.info("Notifications found; updating to latest notification found");
        } else {
            logger.info("No notifications found; using current search time");
        }
        return newStartDate;
    }
}
