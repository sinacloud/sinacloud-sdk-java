/*
 * Copyright 2012-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
package com.sinacloud.scs.auth;
import static com.sinacloud.scs.SDKGlobalConfiguration.ACCESS_KEY_SYSTEM_PROPERTY;
import static com.sinacloud.scs.SDKGlobalConfiguration.SECRET_KEY_SYSTEM_PROPERTY;

import com.sinacloud.scs.SCSClientException;


/**
 * {@link AWSCredentialsProvider} implementation that provides credentials by
 * looking at the <code>aws.accessKeyId</code> and <code>aws.secretKey</code>
 * Java system properties.
 */
public class SystemPropertiesCredentialsProvider implements AWSCredentialsProvider {

    public AWSCredentials getCredentials() {
        if (System.getenv(ACCESS_KEY_SYSTEM_PROPERTY) != null &&
            System.getenv(SECRET_KEY_SYSTEM_PROPERTY) != null) {
            return new BasicAWSCredentials(
                    System.getenv(ACCESS_KEY_SYSTEM_PROPERTY),
                    System.getenv(SECRET_KEY_SYSTEM_PROPERTY));
        }

        throw new SCSClientException(
                "Unable to load AWS credentials from Java system properties " +
                "(" + ACCESS_KEY_SYSTEM_PROPERTY + " and " + SECRET_KEY_SYSTEM_PROPERTY + ")");
    }

    public void refresh() {}

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}