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
import java.util.Optional;
import java.util.Set;

public class FieldValueModel {
    private Collection<String> values;
    private boolean isSet;

    private FieldValueModel() {
        this(null, false);
    }

    public FieldValueModel(final Collection<String> values, final boolean isSet) {
        this.values = values;
        this.isSet = isSet;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(final Collection<String> values) {
        this.values = values;
    }

    public Optional<String> getValue() {
        return values.stream().findFirst();
    }

    public void setValue(final String value) {
        values = Set.of(value);
    }

    public boolean isSet() {
        return isSet;
    }

    public void setIsSet(final boolean set) {
        isSet = set;
    }
}
