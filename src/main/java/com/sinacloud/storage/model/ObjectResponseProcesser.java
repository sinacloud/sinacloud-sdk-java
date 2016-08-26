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
	private String errMsg;
	private int errno;
	private byte[] bodyContent;
	private List<ObjectFile> objectList;
	private int responseCode;
	private String responseMessage;
	private Map<String,List<String>> responseHeaders;
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
		return "ObjectResponseProcesser [errMsg=" + errMsg + ", errno=" + errno + ", bodyContent="
				+ Arrays.toString(bodyContent) + ", objectList=" + objectList + ", responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + "]";
	}
	
}
