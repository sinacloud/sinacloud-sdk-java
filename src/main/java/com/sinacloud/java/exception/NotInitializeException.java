package com.sinacloud.java.exception;

/**
 * 未初始化异常
 * 通过代表调用某一些方法，而在调用这些方法之前需要先初始化。
 * @author SAE
 *
 */
public class NotInitializeException extends RuntimeException {
	private static final long serialVersionUID = -5277666496944553358L;
	public NotInitializeException() {
		super();
	}
	
	public NotInitializeException(String name) {
		super(name);
	}
}
