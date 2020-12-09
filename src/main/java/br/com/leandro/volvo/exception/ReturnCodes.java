package br.com.leandro.volvo.exception;

import org.springframework.http.HttpStatus;

public enum ReturnCodes {
	//@formatter:off
	DEPARTMENT_NOT_FOUND(-1001, "Department not found", HttpStatus.NOT_FOUND),
	USER_NOT_FOUND(-1002, "User not found", HttpStatus.NOT_FOUND);
	//@formatter:on

	private Integer code;
	private String message;
	private HttpStatus httpCode;

	private ReturnCodes(Integer code, String message, HttpStatus httpCode) {
		this.code = code;
		this.message = message;
		this.httpCode = httpCode;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpCode() {
		return httpCode;
	}

}
