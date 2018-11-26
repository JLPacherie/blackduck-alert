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
package com.synopsys.integration.alert.channel.slack.model;

import static org.junit.Assert.assertNull;

import com.synopsys.integration.alert.channel.slack.mock.MockSlackRestModel;
import com.synopsys.integration.alert.web.model.CommonDistributionRestModelTest;

public class SlackConfigRestModelTest extends CommonDistributionRestModelTest<SlackDistributionConfig> {

    @Override
    public void assertRestModelFieldsNull(final SlackDistributionConfig restModel) {
        assertNull(restModel.getChannelName());
        assertNull(restModel.getChannelUsername());
        assertNull(restModel.getWebhook());
    }

    @Override
    public void assertRestModelFieldsFull(final SlackDistributionConfig restModel) {
        assertEquals(getMockUtil().getWebhook(), restModel.getWebhook());
        assertEquals(getMockUtil().getChannelName(), restModel.getChannelName());
        assertEquals(getMockUtil().getChannelUsername(), restModel.getChannelUsername());
    }

    @Override
    public Class<SlackDistributionConfig> getRestModelClass() {
        return SlackDistributionConfig.class;
    }

    @Override
    public MockSlackRestModel getMockUtil() {
        return new MockSlackRestModel();
    }

}
