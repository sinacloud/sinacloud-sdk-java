/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
package com.sinacloud.scs.retry;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;

import com.sinacloud.scs.ClientConfiguration;
import com.sinacloud.scs.SCSClientException;
import com.sinacloud.scs.SCSServiceException;
import com.sinacloud.scs.SCSWebServiceRequest;

/**
 * This class includes a set of pre-defined retry policies, including default
 * policies used by SDK.
 */
public class PredefinedRetryPolicies {
    
    /* SDK default */
    
    /** SDK default max retry count **/
    public static int DEFAULT_MAX_ERROR_RETRY = 3;
    
    /** SDK default retry policy **/
    public static final RetryPolicy DEFAULT;
    
    /**
     * The SDK default back-off strategy, which increases exponentially up to a
     * max amount of delay. It also applies a larger scale factor upon service
     * throttling exception.
     */
    public static final RetryPolicy.BackoffStrategy DEFAULT_BACKOFF_STRATEGY = new SDKDefaultBackoffStrategy();
    
    /**
     * The SDK default retry condition, which checks for various conditions in
     * the following order:
     * <ul>
     *   <li>Never retry on requests with non-repeatable content;
     *   <li>Retry on client exceptions caused by IOException;
     *   <li>Retry on service exceptions that are either 500 internal server
     *       errors, 503 service unavailable errors, service throttling errors or
     *       clock skew errors.
     * </ul>
     */
    public static final RetryPolicy.RetryCondition DEFAULT_RETRY_CONDITION = new SDKDefaultRetryCondition();
    
    static {
        DEFAULT = getDefaultRetryPolicy();
    }
    
    /**
     * Returns the SDK default retry policy. This policy will honor the
     * maxErrorRetry set in ClientConfiguration.
     * 
     * @see ClientConfiguration#setMaxErrorRetry(int)
     */
    public static RetryPolicy getDefaultRetryPolicy() {
//        return new RetryHandler(DEFAULT_MAX_ERROR_RETRY);
    	return new RetryPolicy(DEFAULT_RETRY_CONDITION,
                DEFAULT_BACKOFF_STRATEGY,
                DEFAULT_MAX_ERROR_RETRY,
                true);
    }
    
    
    /**
     * Returns the SDK default retry policy with the specified max retry count.
     */
    public static HttpRequestRetryHandler getDefaultRetryPolicyWithCustomMaxRetries(int maxErrorRetry) {
        return new RetryHandler(maxErrorRetry);
    }
    
    /** A private class that implements the default back-off strategy. **/
    private static class SDKDefaultBackoffStrategy implements RetryPolicy.BackoffStrategy {
        
        /** Base sleep time (milliseconds) for general exceptions. **/
        private static final int SCALE_FACTOR = 300;
        
        /** Base sleep time (milliseconds) for throttling exceptions. **/
        private static final int THROTTLING_SCALE_FACTOR = 500;
        
        private static final int THROTTLING_SCALE_FACTOR_RANDOM_RANGE = THROTTLING_SCALE_FACTOR / 4;
        
        /** Maximum exponential back-off time before retrying a request */
        private static final int MAX_BACKOFF_IN_MILLISECONDS = 20 * 1000;
        
        /** For generating a random scale factor **/
        private final Random random = new Random();
        
        /** {@inheritDoc} */
        @Override
        public final long delayBeforeNextRetry(SCSWebServiceRequest originalRequest,
                                               SCSClientException exception,
                                               int retries) {
            if (retries <= 0) return 0;
            
            int scaleFactor;
            if (exception instanceof SCSServiceException
                    && RetryUtils.isThrottlingException((SCSServiceException)exception)) {
                scaleFactor = THROTTLING_SCALE_FACTOR + random.nextInt(THROTTLING_SCALE_FACTOR_RANDOM_RANGE);
            } else {
                scaleFactor = SCALE_FACTOR;
            }
            
            long delay = (1 << retries) * scaleFactor;
            delay = Math.min(delay, MAX_BACKOFF_IN_MILLISECONDS);
            
            return delay;
        }
    }
    
    /**
     * The default implementation of RetryCondition used by the SDK. User could
     * extend this class to provide additional custom conditions.
     * The default implementation checks for various conditions in
     * the following order:
     * <ul>
     *   <li>Retry on client exceptions caused by IOException;
     *   <li>Retry on service exceptions that are either 500 internal server
     *       errors, 503 service unavailable errors, service throttling errors or
     *       clock skew errors.
     * </ul>
     */
    public static class SDKDefaultRetryCondition implements RetryPolicy.RetryCondition {

//        @Override
        public boolean shouldRetry(SCSWebServiceRequest originalRequest,
                                   SCSClientException exception,
                                   int retriesAttempted) {
            // Always retry on client exceptions caused by IOException
            if (exception.getCause() instanceof IOException) return true;
            
            // Only retry on a subset of service exceptions
            if (exception instanceof SCSServiceException) {
                SCSServiceException ase = (SCSServiceException)exception;

                /*
                 * For 500 internal server errors and 503 service
                 * unavailable errors, we want to retry, but we need to use
                 * an exponential back-off strategy so that we don't overload
                 * a server with a flood of retries.
                 */
                if (ase.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR
                    || ase.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                    return true;
                }

                /*
                 * Throttling is reported as a 400 error from newer services. To try
                 * and smooth out an occasional throttling error, we'll pause and
                 * retry, hoping that the pause is long enough for the request to
                 * get through the next time.
                 */
                if (RetryUtils.isThrottlingException(ase)) return true;
                
                /*
                 * Clock skew exception. If it is then we will get the time offset 
                 * between the device time and the server time to set the clock skew
                 * and then retry the request.
                 */
                if (RetryUtils.isClockSkewError(ase)) return true;
            }
            
            return false;
        }
        
    }
}
