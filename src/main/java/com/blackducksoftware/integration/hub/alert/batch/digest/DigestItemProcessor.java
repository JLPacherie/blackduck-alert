package com.blackducksoftware.integration.hub.alert.batch.digest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.blackducksoftware.integration.hub.alert.channel.email.EmailEvent;
import com.blackducksoftware.integration.hub.alert.channel.hipchat.HipChatEvent;
import com.blackducksoftware.integration.hub.alert.datasource.entity.NotificationEntity;
import com.blackducksoftware.integration.hub.alert.event.AbstractChannelEvent;

public class DigestItemProcessor implements ItemProcessor<List<NotificationEntity>, List<AbstractChannelEvent>> {
    private final static Logger logger = LoggerFactory.getLogger(RealTimeItemReader.class);

    @Override
    public List<AbstractChannelEvent> process(final List<NotificationEntity> notificationData) throws Exception {
        logger.info("Notification Entity Count: {}", notificationData.size());

        final List<AbstractChannelEvent> events = processNotifications(notificationData);

        if (events.isEmpty()) {
            return null;
        } else {
            return events;
        }
    }

    private List<AbstractChannelEvent> processNotifications(final List<NotificationEntity> notificationList) {

        if (notificationList == null) {
            return new ArrayList<>(0);
        } else {
            final List<AbstractChannelEvent> events = new ArrayList<>(notificationList.size());
            notificationList.forEach(notification -> {
                events.add(new EmailEvent(notification));
                events.add(new HipChatEvent(notification));
            });
            return events;
        }
    }
}