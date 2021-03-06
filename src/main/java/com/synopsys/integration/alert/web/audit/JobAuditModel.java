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
package com.synopsys.integration.alert.web.audit;

import com.synopsys.integration.alert.web.model.MaskedModel;

public class JobAuditModel extends MaskedModel {
    private String timeAuditCreated;
    private String timeLastSent;
    private String status;

    public JobAuditModel() {
    }

    public JobAuditModel(final String timeAuditCreated, final String timeLastSent, final String status) {
        this.timeAuditCreated = timeAuditCreated;
        this.timeLastSent = timeLastSent;
        this.status = status;
    }

    public String getTimeAuditCreated() {
        return timeAuditCreated;
    }

    public String getTimeLastSent() {
        return timeLastSent;
    }

    public String getStatus() {
        return status;
    }
}
