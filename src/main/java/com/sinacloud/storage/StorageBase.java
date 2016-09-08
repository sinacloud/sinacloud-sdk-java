package com.sinacloud.storage;


import java.util.TimeZone;

import org.apache.log4j.Logger;


/**
 * 
 * @author storage操作基础类
 */
public class StorageBase{
	/**
	 *  请求的方法类型
	 */
	protected static final String POST ="POST";
	
	protected static final String GET = "GET";
	
	protected static final String PUT = "PUT";
	
	protected static final String DELETE = "DELETE";
	
	protected static final String HEAD = "HEAD";
	
	/**
	 * storage 请求地址
	 */
	protected static String baseurl = "https://api.sinas3.com/v1/SAE_";
	
	/**
	 * 发送请求时所需的access key
	 */
	protected String accessKey;
	/**
	 * 发送请求是所需的secret key
	 */
	protected String secretKey;
	/**
	 * 当前应用的名称
	 */
	protected String appName;

	
	/**
	 * 错误信息
	 */
	protected String errMsg = "success!";
	/**
	 * 错误状态码
	 */
	protected int errNo = 0;
	
	private static Logger logger = Logger.getLogger(StorageBase.class.getName());
	
	
	protected static final String HEADER_DATE_FORMAT = "EEE',' d MMM yyyy HH:mm:ss 'GMT'";
	protected static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	protected static int internal_timeout = 5000;

	/**
	 * 返回运行过程中的错误信息
	 * 
	 * @return 错误信息
	 */
	public String getErrmsg() {
		return this.errMsg;
	}

	/**
	 * 返回运行过程中的错误代码
	 * 
	 * @return 错误代码
	 */
	public int getErrno() {
		return this.errNo;
	}

}
