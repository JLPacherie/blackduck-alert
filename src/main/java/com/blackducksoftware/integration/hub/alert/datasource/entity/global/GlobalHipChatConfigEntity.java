/**
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
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
package com.blackducksoftware.integration.hub.alert.datasource.entity.global;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(schema = "alert", name = "global_hipchat_config")
public class GlobalHipChatConfigEntity extends GlobalChannelConfigEntity {
    private static final long serialVersionUID = 2791949172564090134L;

    @Column(name = "api_key")
    private String apiKey;

    public GlobalHipChatConfigEntity() {
    }

    public GlobalHipChatConfigEntity(final String apiKey) {
        this.apiKey = apiKey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this, RecursiveToStringStyle.JSON_STYLE);
        reflectionToStringBuilder.setExcludeFieldNames("apiKey");
        return reflectionToStringBuilder.build();
    }

}
