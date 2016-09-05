package com.sinacloud.storage.model;

import java.util.Arrays;

/**
 *  storage对象文件
 * @author nero
 */
public class ObjectFile {
	
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小 int类型
	 */
	private int fileSize;
	/**
	 * 文件内容 byte数组
	 */
	private byte[] content;
	/**
	 * 获取文件名称
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置文件名称
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取文件大小
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * 设置文件大小
	 * @param fileSize
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * 获取文件内容
	 * @return
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * 设置文件内容
	 * @param content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ObjectFile [fileName=" + fileName + ", fileSize=" + fileSize + ", content=" + Arrays.toString(content)
				+ "]";
	}
	
	
}
