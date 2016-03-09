package com.sinacloud.java.exception;

/**
 * 服务未开启异常，当所调用的服务在控制台面板中未开启则会抛出此异常
 * @author SAE
 *
 */
public class ServiceNotOpenException extends RuntimeException{
	private static final long serialVersionUID = -6392453842942271416L;

	public ServiceNotOpenException() {
		super();
	}
	
	public ServiceNotOpenException(String name) {
		super(name);
	}
}