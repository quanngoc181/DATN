package com.hust.datn.exception;

import org.springframework.http.HttpStatus;

public class Error {
	public HttpStatus code;
	public String message;

	public Error() {

	}

	public Error(HttpStatus code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
