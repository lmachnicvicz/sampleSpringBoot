package br.com.leandro.volvo.exception;

public class DepartmentNotFoundException extends BaseException {

	private static final long serialVersionUID = -5139435003116752231L;

	private static final ReturnCodes code = ReturnCodes.DEPARTMENT_NOT_FOUND;

	public DepartmentNotFoundException(Long departmentId) {
		super(code.getCode(), code.getMessage(), departmentId.toString(), code.getHttpCode());
	}

	public DepartmentNotFoundException(Long departmentId, Throwable ex) {
		super(code.getCode(), code.getMessage(), departmentId.toString(), code.getHttpCode(), ex);
	}

}
