package com.sinacloud.java.exception;

/**
 * 初始化错误，如初始化accesskey，secretkey 为空，或者为空字符等情况。
 * @author SAE
 *
 */
public class InitializeErrorException extends RuntimeException {
	private static final long serialVersionUID = 4272121355243891172L;
	public InitializeErrorException() {
		super();
	}
	
	public InitializeErrorException(String name) {
		super(name);
	}
}
