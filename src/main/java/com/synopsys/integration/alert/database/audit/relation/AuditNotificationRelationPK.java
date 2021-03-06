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
package com.synopsys.integration.alert.database.audit.relation;

import java.io.Serializable;

public class AuditNotificationRelationPK implements Serializable {
    private static final long serialVersionUID = -9015966905838645720L;
    private Long auditEntryId;
    private Long notificationId;

    public AuditNotificationRelationPK() {
        // JPA requires default constructor definitions
    }

    public Long getAuditEntryId() {
        return auditEntryId;
    }

    public void setAuditEntryId(final Long auditEntryId) {
        this.auditEntryId = auditEntryId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(final Long notificationId) {
        this.notificationId = notificationId;
    }
}
