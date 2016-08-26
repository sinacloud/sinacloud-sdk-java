package com.sinacloud.storage.model;

import java.util.List;

public class ObjectFileList {
	private List<String> objectList;
	private int objectCount;
	private String timestamp;
	private int totalObjectUsedSize;
	public List<String> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<String> objectList) {
		this.objectList = objectList;
	}
	public int getObjectCount() {
		return objectCount;
	}
	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getTotalObjectUsedSize() {
		return totalObjectUsedSize;
	}
	public void setTotalObjectUsedSize(int totalObjectUsedSize) {
		this.totalObjectUsedSize = totalObjectUsedSize;
	}
	@Override
	public String toString() {
		return "ObjectFileList [objectList=" + objectList + ", objectCount=" + objectCount + ", timestamp=" + timestamp
				+ ", totalObjectUsedSize=" + totalObjectUsedSize + "]";
	}
	
	
}
