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
	 *  当前bucket占用的大小
	 */
	private int bucketSize;
	
	/**
	 * 修改时间
	 */
	private String timestamp;
	
	/**
	 * 当前bucket下文件的数量
	 */
	private int objectFilecount;

	/**
	 * 获取bucket名称
	 * @return
	 */
	public String getBucketName() {
		return bucketName;
	}

	/**
	 * 设置bucket名称
	 * @param bucketName
	 */
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	/**
	 * 获取bucket大小
	 * @return
	 */
	public int getBucketSize() {
		return bucketSize;
	}

	/**
	 * 设置bucket大小
	 * @param bucketSize
	 */
	public void setBucketSize(int bucketSize) {
		this.bucketSize = bucketSize;
	}

	/**
	 * 获取文件数量
	 * @return
	 */
	public int getObjectFilecount() {
		return objectFilecount;
	}

	/**
	 * 设置bucket文件数量
	 * @param objectFilecount
	 */
	public void setObjectFilecount(int objectFilecount) {
		this.objectFilecount = objectFilecount;
	}

	/**
	 * 获取时间戳
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * 设置时间戳
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	@Override
	public String toString() {
		return "Bucket [bucketName=" + bucketName + ", bucketSize=" + bucketSize + ", timestamp=" + timestamp
				+ ", objectFilecount=" + objectFilecount + "]";
	}

	
}
