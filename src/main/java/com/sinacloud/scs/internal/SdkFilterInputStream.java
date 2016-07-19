/*
 * Copyright 2013-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.sinacloud.scs.internal;

import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * Base class for AWS Java SDK specific {@link FilterInputStream}.
 */
public class SdkFilterInputStream extends FilterInputStream {//implements MetricAware {
    protected SdkFilterInputStream(InputStream in) {
        super(in);
    }

//    @Override
//    public boolean isMetricActivated() {
//        if (in instanceof MetricAware) {
//            MetricAware metricAware = (MetricAware)in;
//            return metricAware.isMetricActivated();
//        }
//        return false;
//    }
}
