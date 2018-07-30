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
package com.blackducksoftware.integration.alert.channel.hipchat;

import java.util.Map;
import java.util.Set;

import javax.jms.MessageListener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.alert.channel.event.ChannelEvent;
import com.blackducksoftware.integration.alert.common.descriptor.ChannelDescriptor;
import com.blackducksoftware.integration.alert.database.channel.hipchat.HipChatDistributionConfigEntity;
import com.blackducksoftware.integration.alert.database.channel.hipchat.HipChatDistributionRepositoryAccessor;
import com.blackducksoftware.integration.alert.database.channel.hipchat.HipChatGlobalConfigEntity;
import com.blackducksoftware.integration.alert.database.channel.hipchat.HipChatGlobalRepositoryAccessor;
import com.blackducksoftware.integration.alert.database.entity.DatabaseEntity;
import com.blackducksoftware.integration.alert.database.entity.EntityPropertyMapper;
import com.blackducksoftware.integration.alert.web.channel.model.HipChatDistributionConfig;
import com.blackducksoftware.integration.alert.web.channel.model.HipChatGlobalConfig;
import com.blackducksoftware.integration.alert.web.model.CommonDistributionConfig;
import com.blackducksoftware.integration.alert.web.model.Config;
import com.blackducksoftware.integration.alert.workflow.startup.AlertStartupProperty;
import com.blackducksoftware.integration.exception.IntegrationException;

@Component
public class HipChatDescriptor extends ChannelDescriptor {
    private final HipChatChannel hipChatChannel;
    private final EntityPropertyMapper entityPropertyMapper;

    @Autowired
    public HipChatDescriptor(final HipChatChannel hipChatChannel, final HipChatDistributionContentConverter hipChatDistributionContentConverter, final HipChatGlobalContentConverter hipChatGlobalContentConverter,
            final HipChatDistributionRepositoryAccessor hipChatDistributionRepositoryAccessor, final HipChatGlobalRepositoryAccessor hipChatGlobalRepositoryAccessor, final EntityPropertyMapper entityPropertyMapper) {
        super(HipChatChannel.COMPONENT_NAME, HipChatChannel.COMPONENT_NAME, hipChatGlobalContentConverter, hipChatGlobalRepositoryAccessor, hipChatDistributionContentConverter, hipChatDistributionRepositoryAccessor);
        this.hipChatChannel = hipChatChannel;
        this.entityPropertyMapper = entityPropertyMapper;
    }

    @Override
    public void validateDistributionConfig(final CommonDistributionConfig restModel, final Map<String, String> fieldErrors) {
        final HipChatDistributionConfig hipChatRestModel = (HipChatDistributionConfig) restModel;
        if (StringUtils.isBlank(hipChatRestModel.getRoomId())) {
            fieldErrors.put("roomId", "A Room Id is required.");
        } else if (!StringUtils.isNumeric(hipChatRestModel.getRoomId())) {
            fieldErrors.put("roomId", "Room Id must be an integer value");
        }
    }

    @Override
    public void testDistributionConfig(final CommonDistributionConfig restModel, final ChannelEvent event) throws IntegrationException {
        final HipChatDistributionConfigEntity hipChatEntity = (HipChatDistributionConfigEntity) getDistributionContentConverter().populateDatabaseEntityFromRestModel(restModel);
        hipChatChannel.sendAuditedMessage(event, hipChatEntity);
    }

    @Override
    public MessageListener getChannelListener() {
        return hipChatChannel;
    }

    @Override
    public void validateGlobalConfig(final Config restModel, final Map<String, String> fieldErrors) {
        final HipChatGlobalConfig hipChatRestModel = (HipChatGlobalConfig) restModel;
        if (StringUtils.isBlank(hipChatRestModel.getApiKey())) {
            fieldErrors.put("apiKey", "ApiKey can't be blank");
        }
    }

    @Override
    public void testGlobalConfig(final DatabaseEntity entity) throws IntegrationException {
        final HipChatGlobalConfigEntity hipChatEntity = (HipChatGlobalConfigEntity) entity;
        hipChatChannel.testGlobalConfig(hipChatEntity);
    }

    @Override
    public Set<AlertStartupProperty> getGlobalEntityPropertyMapping() {
        return entityPropertyMapper.mapEntityToProperties(getName(), HipChatGlobalConfigEntity.class);
    }

    @Override
    public Config getGlobalRestModelObject() {
        return new HipChatGlobalConfig();
    }

}
