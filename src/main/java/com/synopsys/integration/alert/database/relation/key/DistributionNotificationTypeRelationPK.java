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
package com.synopsys.integration.alert.database.relation.key;

import java.io.Serializable;

public class DistributionNotificationTypeRelationPK implements Serializable {
    private static final long serialVersionUID = 8164991480684683756L;
    private Long commonDistributionConfigId;
    private String notificationType;

    public DistributionNotificationTypeRelationPK() {
        // JPA requires default constructor definitions
    }

    public Long getCommonDistributionConfigId() {
        return commonDistributionConfigId;
    }

    public void setCommonDistributionConfigId(final Long commonDistributionConfigId) {
        this.commonDistributionConfigId = commonDistributionConfigId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(final String notificationType) {
        this.notificationType = notificationType;
    }
}
