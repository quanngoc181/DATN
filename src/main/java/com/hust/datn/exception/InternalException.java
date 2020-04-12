package com.hust.datn.exception;

public class InternalException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InternalException() {
		this.message = "Internal Server Exception";
	}

	public InternalException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
