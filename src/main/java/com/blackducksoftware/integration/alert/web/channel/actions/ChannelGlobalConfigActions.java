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
package com.blackducksoftware.integration.alert.web.channel.actions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.alert.common.ContentConverter;
import com.blackducksoftware.integration.alert.common.annotation.SensitiveFieldFinder;
import com.blackducksoftware.integration.alert.common.descriptor.config.DescriptorConfig;
import com.blackducksoftware.integration.alert.common.exception.AlertException;
import com.blackducksoftware.integration.alert.database.entity.DatabaseEntity;
import com.blackducksoftware.integration.alert.web.exception.AlertFieldException;
import com.blackducksoftware.integration.alert.web.model.Config;
import com.blackducksoftware.integration.exception.IntegrationException;
import com.google.common.collect.Maps;

@Component
public class ChannelGlobalConfigActions extends ConfigActions {

    @Autowired
    public ChannelGlobalConfigActions(final ContentConverter contentConverter) {
        super(contentConverter);
    }

    @Override
    public boolean doesConfigExist(final Long id, final DescriptorConfig descriptor) {
        return id != null && descriptor.readEntity(id).isPresent();
    }

    @Override
    public List<? extends Config> getConfig(final Long id, final DescriptorConfig descriptor) throws AlertException {
        if (id != null) {
            final Optional<? extends DatabaseEntity> foundEntity = descriptor.readEntity(id);
            if (foundEntity.isPresent()) {
                final Config restModel = descriptor.populateConfigFromEntity(foundEntity.get());
                if (restModel != null) {
                    final Config maskedRestModel = maskRestModel(restModel);
                    return Arrays.asList(maskedRestModel);
                }
            }
            return Collections.emptyList();
        }
        final List<? extends DatabaseEntity> databaseEntities = descriptor.readEntities();
        final List<Config> restModels = getConvertedRestModels(databaseEntities, descriptor);
        return maskRestModels(restModels);
    }

    protected List<Config> getConvertedRestModels(final List<? extends DatabaseEntity> entities, final DescriptorConfig descriptor) throws AlertException {
        final List<Config> restModels = new ArrayList<>(entities.size());
        for (final DatabaseEntity entity : entities) {
            restModels.add(descriptor.populateConfigFromEntity(entity));
        }
        return restModels;
    }

    public Config maskRestModel(final Config restModel) throws AlertException {
        final Class<? extends Config> restModelClass = restModel.getClass();
        try {
            final Set<Field> sensitiveFields = SensitiveFieldFinder.findSensitiveFields(restModelClass);

            for (final Field sensitiveField : sensitiveFields) {
                boolean isFieldSet = false;
                sensitiveField.setAccessible(true);
                final Object sensitiveFieldValue = sensitiveField.get(restModel);
                if (sensitiveFieldValue != null) {
                    final String sensitiveFieldString = (String) sensitiveFieldValue;
                    if (StringUtils.isNotBlank(sensitiveFieldString)) {
                        isFieldSet = true;
                    }
                }
                sensitiveField.set(restModel, null);

                final Field fieldIsSet = restModelClass.getDeclaredField(sensitiveField.getName() + "IsSet");
                fieldIsSet.setAccessible(true);
                final boolean sensitiveIsSetFieldValue = (boolean) fieldIsSet.get(restModel);
                if (!sensitiveIsSetFieldValue) {
                    fieldIsSet.setBoolean(restModel, isFieldSet);
                }

            }
        } catch (final NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new AlertException(e.getMessage(), e);
        }
        return restModel;
    }

    public List<Config> maskRestModels(final List<Config> restModels) throws AlertException {
        final List<Config> maskedRestModels = new ArrayList<>();
        for (final Config restModel : restModels) {
            maskedRestModels.add(maskRestModel(restModel));
        }
        return maskedRestModels;
    }

    @Override
    public void deleteConfig(final Long id, final DescriptorConfig descriptor) {
        if (id != null) {
            descriptor.deleteEntity(id);
        }
    }

    @Override
    public DatabaseEntity saveConfig(final Config restModel, final DescriptorConfig descriptor) throws AlertException {
        if (restModel != null) {
            final DatabaseEntity createdEntity = descriptor.populateEntityFromConfig(restModel);
            if (createdEntity != null) {
                final DatabaseEntity savedEntity = descriptor.saveEntity(createdEntity);
                return savedEntity;
            }
        }
        return null;
    }

    @Override
    public String validateConfig(Config restModel, final DescriptorConfig descriptor) throws AlertFieldException {
        final List<? extends DatabaseEntity> globalConfigs = descriptor.readEntities();
        if (globalConfigs.size() == 1) {
            try {
                restModel = updateNewConfigWithSavedConfig(restModel, globalConfigs.get(0));
            } catch (final AlertException e) {
                return "Error updating config.";
            }
        }
        return validateConfigInternal(restModel, descriptor);
    }

    protected String validateConfigInternal(final Config config, final DescriptorConfig descriptorConfig) throws AlertFieldException {
        final Map<String, String> fieldErrors = Maps.newHashMap();
        descriptorConfig.validateConfig(config, fieldErrors);
        if (!fieldErrors.isEmpty()) {
            throw new AlertFieldException(fieldErrors);
        }
        return "Valid";
    }

    @Override
    public String testConfig(Config restModel, final DescriptorConfig descriptor) throws IntegrationException {
        final List<? extends DatabaseEntity> globalConfigs = descriptor.readEntities();
        if (globalConfigs.size() == 1) {
            restModel = updateNewConfigWithSavedConfig(restModel, globalConfigs.get(0));
            return testConfigInternal(restModel, descriptor);
        }
        return "Global Config did not have the expected number of rows: Expected 1, but found " + globalConfigs.size();
    }

    protected String testConfigInternal(final Config config, final DescriptorConfig descriptorConfig) throws IntegrationException {
        final DatabaseEntity entity = descriptorConfig.populateEntityFromConfig(config);
        descriptorConfig.testConfig(entity);
        return "Succesfully sent test message.";
    }

    @Override
    public DatabaseEntity saveNewConfigUpdateFromSavedConfig(final Config restModel, final DescriptorConfig descriptor) throws AlertException {
        if (restModel != null && StringUtils.isNotBlank(restModel.getId())) {
            try {
                DatabaseEntity createdEntity = descriptor.populateEntityFromConfig(restModel);
                createdEntity = updateNewConfigWithSavedConfig(createdEntity, restModel.getId(), descriptor);
                if (createdEntity != null) {
                    final DatabaseEntity savedEntity = descriptor.saveEntity(createdEntity);
                    return savedEntity;
                }
            } catch (final Exception e) {
                throw new AlertException(e.getMessage(), e);
            }
        }
        return null;
    }

    public DatabaseEntity updateNewConfigWithSavedConfig(final DatabaseEntity newConfig, final String id, final DescriptorConfig descriptor) throws AlertException {
        if (StringUtils.isNotBlank(id)) {
            final Long longId = getContentConverter().getLongValue(id);
            final Optional<? extends DatabaseEntity> savedConfig = descriptor.readEntity(longId);
            if (savedConfig.isPresent()) {
                return updateNewConfigWithSavedConfig(newConfig, savedConfig.get());
            }
        }
        return newConfig;
    }

    public <T> T updateNewConfigWithSavedConfig(final T newConfig, final DatabaseEntity savedConfig) throws AlertException {
        try {
            final Class<?> newConfigClass = newConfig.getClass();

            final Set<Field> sensitiveFields = SensitiveFieldFinder.findSensitiveFields(newConfigClass);
            for (final Field field : sensitiveFields) {
                field.setAccessible(true);
                final Object value = field.get(newConfig);
                if (value == null || StringUtils.isBlank(value.toString())) {
                    if (savedConfig != null) {
                        final Class<?> savedConfigClass = savedConfig.getClass();
                        Field savedField = null;
                        try {
                            savedField = savedConfigClass.getDeclaredField(field.getName());
                        } catch (final NoSuchFieldException e) {
                            continue;
                        }
                        savedField.setAccessible(true);
                        final String savedValue = (String) savedField.get(savedConfig);
                        field.set(newConfig, savedValue);
                    }
                }
            }
        } catch (final SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new AlertException(e.getMessage(), e);
        }

        return newConfig;
    }

}
