/*
 * Copyright (C) 2017 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.synopsys.integration.alert.channel.hipchat.mock;

import com.google.gson.JsonObject;
import com.synopsys.integration.alert.channel.hipchat.HipChatChannel;
import com.synopsys.integration.alert.database.channel.hipchat.HipChatGlobalConfigEntity;
import com.synopsys.integration.alert.mock.MockGlobalEntityUtil;
import com.synopsys.integration.alert.web.model.Config;

public class MockHipChatGlobalEntity extends MockGlobalEntityUtil<HipChatGlobalConfigEntity> {
    private String apiKey;
    private boolean apiKeyIsSet;
    private Long id;
    private String hostServer;

    public MockHipChatGlobalEntity() {
        this("ApiKey", false, 1L, HipChatChannel.HIP_CHAT_API);
    }

    private MockHipChatGlobalEntity(final String apiKey, final boolean apiKeyIsSet, final Long id, final String hostServer) {
        this.apiKey = apiKey;
        this.apiKeyIsSet = apiKeyIsSet;
        this.id = id;
        this.hostServer = hostServer;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isApiKeyIsSet() {
        return apiKeyIsSet;
    }

    public void setApiKeyIsSet(final boolean apiKeyIsSet) {
        this.apiKeyIsSet = apiKeyIsSet;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setHostServer(final String hostServer) {
        this.hostServer = hostServer;
    }

    @Override
    public Config createGlobalConfig() {
        final HipChatGlobalConfig config = new HipChatGlobalConfig(id.toString(), apiKey, true, hostServer);
        return config;
    }

    @Override
    public Config createEmptyGlobalConfig() {
        return new HipChatGlobalConfig();
    }

    @Override
    public HipChatGlobalConfigEntity createGlobalEntity() {
        final HipChatGlobalConfigEntity configEntity = new HipChatGlobalConfigEntity(apiKey, hostServer);
        configEntity.setId(id);
        return configEntity;
    }

    @Override
    public String getGlobalEntityJson() {
        final JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("hostServer", hostServer);
        return json.toString();
    }

    @Override
    public HipChatGlobalConfigEntity createEmptyGlobalEntity() {
        return new HipChatGlobalConfigEntity();
    }

}
