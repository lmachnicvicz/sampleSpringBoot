package br.com.leandro.volvo.exception;

public class UserNotFoundException extends BaseException {

	private static final long serialVersionUID = -7219132816779985274L;
	
	private static final ReturnCodes code = ReturnCodes.USER_NOT_FOUND;

	public UserNotFoundException(Long userId) {
		super(code.getCode(), code.getMessage(), userId.toString(), code.getHttpCode());
	}

	public UserNotFoundException(Long userId, Throwable ex) {
		super(code.getCode(), code.getMessage(), userId.toString(), code.getHttpCode(), ex);
	}

}
