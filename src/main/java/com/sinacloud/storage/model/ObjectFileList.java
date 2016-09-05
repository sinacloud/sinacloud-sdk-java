package com.sinacloud.storage.model;

import java.util.List;

public class ObjectFileList {
	/**
	 * 对象文件名称列表
	 */
	private List<String> objectList;
	/**
	 * 对象文件数量
	 */
	private int objectCount;
	/**
	 * 修改时间
	 * 
	 */
	private String timestamp;
	/**
	 * 暂用空间大小
	 */
	private int totalObjectUsedSize;
	
	/**
	 * 获取文件列表
	 * @return
	 */
	public List<String> getObjectList() {
		return objectList;
	}
	/**
	 * 设置文件列表
	 * @param objectList
	 */
	public void setObjectList(List<String> objectList) {
		this.objectList = objectList;
	}
	/**
	 * 获取文件数量
	 * @return
	 */
	public int getObjectCount() {
		return objectCount;
	}
	/**
	 * 设置文件数量
	 * @param objectCount
	 */
	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
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
	/**
	 * 获取所有文件的大小
	 * @return
	 */
	public int getTotalObjectUsedSize() {
		return totalObjectUsedSize;
	}
	/**
	 * 设置所有文件大小
	 * @param totalObjectUsedSize
	 */
	public void setTotalObjectUsedSize(int totalObjectUsedSize) {
		this.totalObjectUsedSize = totalObjectUsedSize;
	}
	@Override
	public String toString() {
		return "ObjectFileList [objectList=" + objectList + ", objectCount=" + objectCount + ", timestamp=" + timestamp
				+ ", totalObjectUsedSize=" + totalObjectUsedSize + "]";
	}
	
	
}
