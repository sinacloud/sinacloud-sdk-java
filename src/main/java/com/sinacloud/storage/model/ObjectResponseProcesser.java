package com.sinacloud.storage.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 处理object相关的请求
 * @author root
 *
 */
public class ObjectResponseProcesser {
	/**
	 * 返回的消息体
	 */
	private byte[] bodyContent;
	/**
	 * 返回的文件列表
	 */
	private List<ObjectFile> objectList;
	/**
	 * 返回的信息码
	 */
	private int responseCode;
	/**
	 * 返回的信息
	 */
	private String responseMessage;
	/**
	 * 返回的信息头
	 */
	private Map<String,List<String>> responseHeaders;
	public byte[] getBodyContent() {
		return bodyContent;
	}
	public void setBodyContent(byte[] bodyContent) {
		this.bodyContent = bodyContent;
	}
	public List<ObjectFile> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<ObjectFile> objectList) {
		this.objectList = objectList;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	@Override
	public String toString() {
		return "ObjectResponseProcesser [errMsg=" +", bodyContent="
				+ Arrays.toString(bodyContent) + ", objectList=" + objectList + ", responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + "]";
	}
	
}
