package com.sinacloud.storage.model;

import java.util.List;

public class BucketList {
	/**
	 * 当前账户下所有的bucket名称
	 */
	private List<String> buckets;
	/**
	 * 当前账户下所有的bucket数量
	 */
	private int bucketsCount;
	/**
	 * 修改时间
	 */
	private String timestamp;
	/**
	 * 所有bucket占用的大小
	 */
	private int bucketsUsedSize;
	/**
	 *  所有bucket占用的大小
	 */
	private int bucketsTotalObjectCount;
	
	/**
	 * 获取bucket列表
	 * @return
	 */
	public List<String> getBuckets() {
		return buckets;
	}
	/**
	 * 设置bucket列表
	 * @param buckets
	 */
	public void setBuckets(List<String> buckets) {
		this.buckets = buckets;
	}
	/**
	 * 获取bucket数量
	 * @return
	 */
	public int getBucketsCount() {
		return bucketsCount;
	}
	/**
	 * 设置bucket数量
	 * @param bucketsCount
	 */
	public void setBucketsCount(int bucketsCount) {
		this.bucketsCount = bucketsCount;
	}
	/**
	 * 设置时间戳
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * 获取时间戳
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 获取所有bucket占用的大小
	 * @return
	 */
	public int getBucketsUsedSize() {
		return bucketsUsedSize;
	}
	/**
	 * 设置所有bucket占用的大小
	 * @param bucketsUsedSize
	 */
	public void setBucketsUsedSize(int bucketsUsedSize) {
		this.bucketsUsedSize = bucketsUsedSize;
	}
	/**
	 * 获取所有bucket中文件的总数
	 * @return
	 */
	public int getBucketsTotalObjectCount() {
		return bucketsTotalObjectCount;
	}
	/**
	 * 设置所有的bucket文件总数
	 * @param bucketsTotalObjectCount
	 */
	public void setBucketsTotalObjectCount(int bucketsTotalObjectCount) {
		this.bucketsTotalObjectCount = bucketsTotalObjectCount;
	}
	@Override
	public String toString() {
		return "BucketList [buckets=" + buckets + ", bucketsCount=" + bucketsCount + ", timestamp=" + timestamp
				+ ", bucketsUsedSize=" + bucketsUsedSize + ", bucketsTotalObjectCount=" + bucketsTotalObjectCount + "]";
	}
	
}
