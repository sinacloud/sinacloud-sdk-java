package com.sinacloud.storage.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 处理bucket相关请求返回的类
 * @author  nero
 */
public class BucketResponseProcesser {
	private String errMsg;
	private int errno;
	private byte[] bodyContent;
	private List<Bucket> bucketList;
	private int responseCode;
	private String responseMessage;
	private Map<String,List<String>> responseHeaders;
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
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
		return "ResponseProcesser [errMsg=" + errMsg + ", errno=" + errno + ", bodyContent="
				+ Arrays.toString(bodyContent) + ", bucketList=" + bucketList
				+ ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", responseHeaders="
				+ responseHeaders + "]";
	}
	
	
}
