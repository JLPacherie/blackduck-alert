/**
 * hub-alert
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
package com.blackducksoftware.integration.hub.alert.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.hub.alert.datasource.entity.global.GlobalHubConfigEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.global.GlobalHubRepository;
import com.blackducksoftware.integration.hub.alert.descriptor.ProviderDescriptor;
import com.blackducksoftware.integration.hub.alert.provider.hub.controller.global.GlobalHubConfigActions;
import com.blackducksoftware.integration.hub.alert.provider.hub.controller.global.GlobalHubConfigRestModel;

@Component
public class HubProvider implements ProviderDescriptor {
    public static final String PROVIDER_NAME = "provider_hub";

    private final GlobalHubRepository repository;
    private final GlobalHubConfigActions globalHubConfigActions;

    @Autowired
    public HubProvider(final GlobalHubRepository repository, final GlobalHubConfigActions globalHubConfigActions) {
        this.repository = repository;
        this.globalHubConfigActions = globalHubConfigActions;
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public Class<GlobalHubConfigEntity> getGlobalEntityClass() {
        return GlobalHubConfigEntity.class;
    }

    @Override
    public Class<GlobalHubConfigRestModel> getGlobalRestModelClass() {
        return GlobalHubConfigRestModel.class;
    }

    @Override
    public GlobalHubRepository getGlobalRepository() {
        return repository;
    }

    @Override
    public GlobalHubConfigActions getGlobalConfigActions() {
        return globalHubConfigActions;
    }

}
