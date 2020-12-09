package br.com.leandro.volvo.dto.error;

public class BaseErrorDto {
	private Integer code;
	private String message;
	private String detail;
	private Integer httpCode;
	private String path;
	private String method;

	public BaseErrorDto() {
	}

	public BaseErrorDto(Integer code, String message, String detail, Integer httpCode, String path, String method) {
		this.code = code;
		this.message = message;
		this.detail = detail;
		this.httpCode = httpCode;
		this.path = path;
		this.method = method;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "BaseErrorDto [code=" + code + ", message=" + message + ", detail=" + detail + ", httpCode=" + httpCode
				+ ", path=" + path + ", method=" + method + "]";
	}

}
