package com.sinacloud.java.exception;

/**
 * 禁止调用异常，一些方法，构造函数禁止调用，如果调用了则抛出此异常
 * @author SAE
 *
 */
public class BanCallException extends RuntimeException {
	private static final long serialVersionUID = -4210430253397260570L;
	public BanCallException() {
		super();
	}
	
	public BanCallException(String name) {
		super(name);
	}
}