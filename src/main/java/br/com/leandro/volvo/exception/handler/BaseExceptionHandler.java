package br.com.leandro.volvo.exception.handler;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leandro.volvo.dto.error.BaseErrorDto;
import br.com.leandro.volvo.dto.error.SpringDefaultError;
import br.com.leandro.volvo.exception.BaseException;

@ControllerAdvice
public class BaseExceptionHandler {

	private ObjectMapper mapper;

	@Autowired
	public BaseExceptionHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<BaseErrorDto> handlerBaseException(HttpServletRequest req, BaseException ex) {
		BaseErrorDto error = buildBaseError(req, ex);
		return ResponseEntity.status(error.getHttpCode()).body(error);
	}

	@ExceptionHandler(HttpStatusCodeException.class)
	public ResponseEntity<BaseErrorDto> handlerHttpStatusException(HttpServletRequest req, HttpStatusCodeException ex) {
		BaseErrorDto error = buildHttpError(req, ex);
		return ResponseEntity.status(error.getHttpCode()).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseErrorDto> handlerDefaultException(HttpServletRequest req, Exception ex) {
		BaseErrorDto error = buildDefaultError(req, ex);
		return ResponseEntity.status(error.getHttpCode()).body(error);
	}

	private BaseErrorDto buildDefaultError(HttpServletRequest req, Exception ex) {
		BaseErrorDto error = new BaseErrorDto();

		error.setCode(-9999);
		error.setMessage(ex.getMessage());
		error.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setPath(req.getRequestURI());
		error.setMethod(getMethod(ex));

		logger.error("Exception caught: ", ex);

		return error;
	}

	private BaseErrorDto buildHttpError(HttpServletRequest req, HttpStatusCodeException ex) {
		BaseErrorDto error;
		String respBody = ex.getResponseBodyAsString();

		logger.error("Error body received: {}", respBody);

		try {
			if (respBody.contains("\"timestamp\":")) {
				SpringDefaultError springError = mapper.readValue(respBody, SpringDefaultError.class);
				error = buildErrorFromSpring(springError, req, ex);
			} else {
				error = buildDefaultError(req, ex);
			}
		} catch (Exception e) {
			error = buildDefaultError(req, ex);
		}
		return error;
	}

	private BaseErrorDto buildErrorFromSpring(SpringDefaultError springError, HttpServletRequest req,
			HttpStatusCodeException ex) {
		BaseErrorDto error = buildDefaultError(req, ex);

		error.setCode(-9000 - ex.getStatusCode().value());
		error.setMessage(springError.getMessage());
		error.setDetail(
				String.format("Error: (%s) received from path %s", springError.getError(), springError.getPath()));
		error.setHttpCode(ex.getStatusCode().value());

		return error;
	}

	private BaseErrorDto buildBaseError(HttpServletRequest req, BaseException ex) {
		BaseErrorDto error = buildDefaultError(req, ex);

		error.setCode(ex.getCode());
		error.setDetail(ex.getDetail());
		error.setHttpCode(ex.getHttpCode().value());

		return error;
	}

	private String getMethod(Exception ex) {
		return Arrays.stream(ex.getStackTrace()).filter(e -> e.getClassName().startsWith("br.com.leandro.volvo"))
				.findFirst()
				.map(e -> String.format("%s.%s():%s", e.getClassName(), e.getMethodName(), e.getLineNumber()))
				.orElse("");
	}
}
