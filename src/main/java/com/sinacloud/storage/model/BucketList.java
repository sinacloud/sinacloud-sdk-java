package com.sinacloud.storage.model;

import java.util.List;

public class BucketList {
	private List<String> buckets;
	private int bucketsCount;
	private String timestamp;
	private int bucketsUsedSize;
	private int bucketsTotalObjectCount;
	public List<String> getBuckets() {
		return buckets;
	}
	public void setBuckets(List<String> buckets) {
		this.buckets = buckets;
	}
	public int getBucketsCount() {
		return bucketsCount;
	}
	public void setBucketsCount(int bucketsCount) {
		this.bucketsCount = bucketsCount;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getBucketsUsedSize() {
		return bucketsUsedSize;
	}
	public void setBucketsUsedSize(int bucketsUsedSize) {
		this.bucketsUsedSize = bucketsUsedSize;
	}
	public int getBucketsTotalObjectCount() {
		return bucketsTotalObjectCount;
	}
	public void setBucketsTotalObjectCount(int bucketsTotalObjectCount) {
		this.bucketsTotalObjectCount = bucketsTotalObjectCount;
	}
	@Override
	public String toString() {
		return "BucketList [buckets=" + buckets + ", bucketsCount=" + bucketsCount + ", timestamp=" + timestamp
				+ ", bucketsUsedSize=" + bucketsUsedSize + ", bucketsTotalObjectCount=" + bucketsTotalObjectCount + "]";
	}
	
}
