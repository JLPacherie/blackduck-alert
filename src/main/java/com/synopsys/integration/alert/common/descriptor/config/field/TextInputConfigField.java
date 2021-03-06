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
package com.synopsys.integration.alert.common.descriptor.config.field;

import com.synopsys.integration.alert.common.enumeration.FieldGroup;
import com.synopsys.integration.alert.common.enumeration.FieldType;

public class TextInputConfigField extends ConfigField {
    public static TextInputConfigField create(final String key, final String label) {
        return new TextInputConfigField(key, label, false, false);
    }

    public static TextInputConfigField createGrouped(final String key, final String label, final FieldGroup group) {
        return new TextInputConfigField(key, label, false, false, group);
    }

    public static TextInputConfigField createRequired(final String key, final String label) {
        return new TextInputConfigField(key, label, true, false);
    }

    public TextInputConfigField(final String key, final String label, final boolean required, final boolean sensitive, final FieldGroup group) {
        super(key, label, FieldType.TEXT_INPUT.getFieldTypeName(), required, sensitive, group);
    }

    public TextInputConfigField(final String key, final String label, final boolean required, final boolean sensitive) {
        super(key, label, FieldType.TEXT_INPUT.getFieldTypeName(), required, sensitive);
    }

}
