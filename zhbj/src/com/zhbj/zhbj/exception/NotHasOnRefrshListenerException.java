package com.zhbj.zhbj.exception;

/**
 * û�����ü����쳣
 * 
 * @author wfy
 * 
 */
public class NotHasOnRefrshListenerException extends RuntimeException {

	private static final long serialVersionUID = 7646790111226297506L;

	public NotHasOnRefrshListenerException() {
		super();
	}

	public NotHasOnRefrshListenerException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NotHasOnRefrshListenerException(String detailMessage) {
		super(detailMessage);
	}

	public NotHasOnRefrshListenerException(Throwable throwable) {
		super(throwable);
	}

}
