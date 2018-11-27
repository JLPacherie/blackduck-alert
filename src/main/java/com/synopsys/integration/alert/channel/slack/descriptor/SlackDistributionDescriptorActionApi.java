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
package com.synopsys.integration.alert.channel.slack.descriptor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.alert.channel.event.DistributionEvent;
import com.synopsys.integration.alert.channel.slack.SlackChannel;
import com.synopsys.integration.alert.common.descriptor.config.DescriptorActionApi;
import com.synopsys.integration.alert.common.field.CommonDistributionFields;

@Component
public class SlackDistributionDescriptorActionApi extends DescriptorActionApi {

    @Autowired
    public SlackDistributionDescriptorActionApi(final SlackChannel slackChannel) {
        super(slackChannel);
    }

    @Override
    public void validateConfig(final CommonDistributionFields commonDistributionFields, final Map<String, String> fieldErrors) {
        final String webhook = commonDistributionFields.getStringValue(SlackUIConfig.KEY_WEBHOOK);
        final String channelName = commonDistributionFields.getStringValue(SlackUIConfig.KEY_CHANNEL_NAME);
        if (StringUtils.isBlank(webhook)) {
            fieldErrors.put("webhook", "A webhook is required.");
        }
        if (StringUtils.isBlank(channelName)) {
            fieldErrors.put("channelName", "A channel name is required.");
        }
    }

    @Override
    public DistributionEvent createTestEvent(final CommonDistributionFields commonDistributionFields) {
        return null;
    }
}
