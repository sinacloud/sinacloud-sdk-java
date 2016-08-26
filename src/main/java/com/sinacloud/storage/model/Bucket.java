package com.sinacloud.storage.model;

import java.util.List;

/**
 * 
 * storage bucket对象
 * @author nero
 *
 */
public class Bucket {
	/**
	 * bucket 名称
	 */
	private String bucketName;
	
	/**
	 *  当前bucket下文件列表
	 */
	private int bucketSize;
	
	private String timestamp;
	
	private int objectFilecount;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public int getBucketSize() {
		return bucketSize;
	}

	public void setBucketSize(int bucketSize) {
		this.bucketSize = bucketSize;
	}

	public int getObjectFilecount() {
		return objectFilecount;
	}

	public void setObjectFilecount(int objectFilecount) {
		this.objectFilecount = objectFilecount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	@Override
	public String toString() {
		return "Bucket [bucketName=" + bucketName + ", bucketSize=" + bucketSize + ", timestamp=" + timestamp
				+ ", objectFilecount=" + objectFilecount + "]";
	}

	
}
