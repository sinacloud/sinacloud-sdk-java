package com.sinacloud.storage.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 处理bucket相关请求返回的类
 * @author  nero
 */
public class BucketResponseProcesser {
	/**
	 * 返回的body信息
	 */
	private byte[] bodyContent;
	/**
	 * 返回的bucket列表
	 */
	private List<Bucket> bucketList;
	/**
	 * 返回信息码
	 */
	private int responseCode;
	/**
	 * 返回信息
	 */
	private String responseMessage;
	/**
	 * 返回请求头
	 */
	private Map<String,List<String>> responseHeaders;
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public byte[] getBodyContent() {
		return bodyContent;
	}
	public void setBodyContent(byte[] bodyContent) {
		this.bodyContent = bodyContent;
	}
	public List<Bucket> getBucketList() {
		return bucketList;
	}
	public void setBucketList(List<Bucket> bucketList) {
		this.bucketList = bucketList;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	@Override
	public String toString() {
		return "ResponseProcesser [errMsg=" + ", bodyContent="
				+ Arrays.toString(bodyContent) + ", bucketList=" + bucketList
				+ ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", responseHeaders="
				+ responseHeaders + "]";
	}
	
	
}
