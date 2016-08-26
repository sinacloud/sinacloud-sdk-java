package com.sinacloud.storage.model;

import java.util.Arrays;

/**
 *  storage对象文件
 * @author nero
 */
public class ObjectFile {
	
	private String fileName;
	private int fileSize;
	private byte[] content;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ObjectFile [fileName=" + fileName + ", fileSize=" + fileSize + ", content=" + Arrays.toString(content)
				+ "]";
	}
	
	
}
