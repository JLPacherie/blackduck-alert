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
package com.synopsys.integration.alert.web.model;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.synopsys.integration.alert.common.configuration.FieldAccessor;
import com.synopsys.integration.alert.database.api.configuration.ConfigurationFieldModel;

public class FieldModel extends Config {
    private final Map<String, FieldValueModel> keyToValues;
    private final String descriptorName;
    private final String context;

    private FieldModel() {
        this(null, null, null);
    }

    public FieldModel(final String descriptorName, final String context, final Map<String, FieldValueModel> keyToValues) {
        this.descriptorName = descriptorName;
        this.context = context;
        this.keyToValues = keyToValues;
    }

    public FieldModel(final String configId, final String descriptorName, final String context, final Map<String, FieldValueModel> keyToValues) {
        super(configId);
        this.descriptorName = descriptorName;
        this.context = context;
        this.keyToValues = keyToValues;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public String getContext() {
        return context;
    }

    public Map<String, FieldValueModel> getKeyToValues() {
        return keyToValues;
    }

    public FieldValueModel getField(final String key) {
        return keyToValues.get(key);
    }

    public FieldAccessor convertToFieldAccessor() {
        final Map<String, ConfigurationFieldModel> fields = convertToConfigurationFieldModelMap();
        return new FieldAccessor(fields);
    }

    public Map<String, ConfigurationFieldModel> convertToConfigurationFieldModelMap() {
        return keyToValues
                   .entrySet()
                   .stream()
                   .collect(Collectors.toMap(Map.Entry::getKey, entry -> createConfigurationFieldModel(entry.getKey(), entry.getValue().getValues(), entry.getValue().isSet())));
    }

    public void putField(final String key, final FieldValueModel field) {
        keyToValues.put(key, field);
    }

    private ConfigurationFieldModel createConfigurationFieldModel(final String key, final Collection<String> values, final boolean isSet) {
        final ConfigurationFieldModel configurationFieldModel = ConfigurationFieldModel.create(key);
        configurationFieldModel.setFieldValues(values);
        return configurationFieldModel;
    }

}
