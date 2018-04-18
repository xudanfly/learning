package com.jianeye.cuckoo3.webside.exception;

/**
 * 
 * AjaxException.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption ajax异常,针对ajax请求处理的Exception
 */
public class AjaxException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AjaxException() {
		super();
	}

	public AjaxException(String message) {
		super(message);
	}

	public AjaxException(Throwable cause) {
		super(cause);
	}

	public AjaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public AjaxException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
    	super(message, cause, enableSuppression, writableStackTrace);
    }
}
