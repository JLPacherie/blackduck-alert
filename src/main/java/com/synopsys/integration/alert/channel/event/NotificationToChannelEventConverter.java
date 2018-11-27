/**
 * blackduck-alert
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
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
package com.synopsys.integration.alert.channel.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.alert.common.field.CommonDistributionFields;
import com.synopsys.integration.alert.common.model.AggregateMessageContent;

@Component
public class NotificationToChannelEventConverter {
    private final Logger logger = LoggerFactory.getLogger(NotificationToChannelEventConverter.class);
    private final ChannelEventProducer channelEventProducer;

    @Autowired
    public NotificationToChannelEventConverter(final ChannelEventProducer channelEventProducer) {
        this.channelEventProducer = channelEventProducer;
    }

    public List<DistributionEvent> convertToEvents(final Map<CommonDistributionFields, List<AggregateMessageContent>> messageContentMap) {
        final List<DistributionEvent> distributionEvents = new ArrayList<>();
        final Set<? extends Map.Entry<CommonDistributionFields, List<AggregateMessageContent>>> jobMessageContentEntries = messageContentMap.entrySet();
        for (final Map.Entry<CommonDistributionFields, List<AggregateMessageContent>> entry : jobMessageContentEntries) {
            final CommonDistributionFields jobConfig = entry.getKey();
            final List<AggregateMessageContent> contentList = entry.getValue();
            for (final AggregateMessageContent content : contentList) {
                distributionEvents.add(channelEventProducer.createChannelEvent(jobConfig, content));
            }
        }
        logger.debug("Created {} events.", distributionEvents.size());
        return distributionEvents;
    }

}
