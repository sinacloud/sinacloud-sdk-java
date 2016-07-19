/*
 * Copyright 2012-2013 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinacloud.scs.services.scs.transfer.internal;

import java.util.Collection;

import com.sinacloud.scs.SCSClientException;
import com.sinacloud.scs.SCSServiceException;
import com.sinacloud.scs.event.ProgressListenerChain;
import com.sinacloud.scs.services.scs.transfer.MultipleFileUpload;
import com.sinacloud.scs.services.scs.transfer.TransferProgress;
import com.sinacloud.scs.services.scs.transfer.Upload;

/**
 * Multiple file upload when uploading an entire directory.
 */
public class MultipleFileUploadImpl extends MultipleFileTransfer implements MultipleFileUpload {

    private final String keyPrefix;
    private final String bucketName;
    
    public MultipleFileUploadImpl(String description, TransferProgress transferProgress,
            ProgressListenerChain progressListenerChain, String keyPrefix, String bucketName, Collection<? extends Upload> subTransfers) {
        super(description, transferProgress, progressListenerChain, subTransfers);
        this.keyPrefix = keyPrefix;
        this.bucketName = bucketName;
    }
    
    /**
     * @deprecated Replaced by {@link #MultipleFileUploadImpl(String, TransferProgress, ProgressListenerChain, String, String, Collection)}
     */
    @Deprecated
    public MultipleFileUploadImpl(String description, TransferProgress transferProgress,
            com.sinacloud.scs.services.scs.transfer.internal.ProgressListenerChain progressListenerChain, String keyPrefix, String bucketName, Collection<? extends Upload> subTransfers) {
        this(description, transferProgress, progressListenerChain.transformToGeneralProgressListenerChain(), 
                keyPrefix, bucketName, subTransfers);
    }

    /**
     * Returns the key prefix of the virtual directory being uploaded to.
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }
    
    /**
     * Returns the name of the bucket to which files are uploaded.
     */
    public String getBucketName() {
        return bucketName;
    }
    
    /**
     * Waits for this transfer to complete. This is a blocking call; the current
     * thread is suspended until this transfer completes.
     *
     * @throws SCSClientException
     *             If any errors were encountered in the client while making the
     *             request or handling the response.
     * @throws SCSServiceException
     *             If any errors occurred in Amazon S3 while processing the
     *             request.
     * @throws InterruptedException
     *             If this thread is interrupted while waiting for the transfer
     *             to complete.
     */
    @Override
    public void waitForCompletion()
            throws SCSClientException, SCSServiceException, InterruptedException {
        if (subTransfers.isEmpty())
            return;
        super.waitForCompletion();
    }

}
