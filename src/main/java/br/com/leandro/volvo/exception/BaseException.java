package br.com.leandro.volvo.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -8956473218592897778L;

	private final Integer code;
	private final String detail;
	private final HttpStatus httpCode;

	public BaseException(Integer code, String msg, String detail, HttpStatus httpCode) {
		super(msg);
		this.code = code;
		this.detail = detail;
		this.httpCode = httpCode;
	}

	public BaseException(Integer code, String msg, String detail, HttpStatus httpCode, Throwable e) {
		super(msg, e);
		this.code = code;
		this.detail = detail;
		this.httpCode = httpCode;
	}

	public Integer getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}

	public HttpStatus getHttpCode() {
		return httpCode;
	}

}
