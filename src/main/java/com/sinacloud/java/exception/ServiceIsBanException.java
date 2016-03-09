package com.sinacloud.java.exception;

/**
 * 当前调用的服务已经被禁用
 * @author SAE
 *
 */
public class ServiceIsBanException extends RuntimeException {
	private static final long serialVersionUID = -4641235873055135237L;
	public ServiceIsBanException() {
		super();
	}
	
	public ServiceIsBanException(String name) {
		super(name);
	}
}