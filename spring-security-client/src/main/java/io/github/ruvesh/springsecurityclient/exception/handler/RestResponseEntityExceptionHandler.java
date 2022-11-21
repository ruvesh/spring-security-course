package io.github.ruvesh.springsecurityclient.exception.handler;


import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.model.ExceptionMessageModel;
import io.github.ruvesh.springsecurityclient.model.ExceptionMessageModel.ExceptionMessageModelBuilder;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ExceptionMessageModel> handlePasswordMismatchException(PasswordMismatchException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.BAD_REQUEST)
												.build();
		return ResponseEntity.badRequest().body(responseMessage);
	}
	
	@ExceptionHandler(PasswordLengthViolationException.class)
	public ResponseEntity<ExceptionMessageModel> handlePasswordLengthViolationException(PasswordLengthViolationException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.BAD_REQUEST)
												.build();
		return ResponseEntity.badRequest().body(responseMessage);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)	
	public ResponseEntity<ExceptionMessageModel> handleConstraintViolationException(ConstraintViolationException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.BAD_REQUEST)
												.detailMessage(exception.getConstraintViolations().toString())
												.build();
		return ResponseEntity.badRequest().body(responseMessage);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionMessageModel> handleDataIntegrityViolationException(DataIntegrityViolationException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.BAD_REQUEST)
												.detailMessage(exception.getRootCause().toString())
												.build();
		return ResponseEntity.badRequest().body(responseMessage);
	}
	
	private ExceptionMessageModelBuilder commonExceptionMessageModelBuilder(Exception exception, ServletWebRequest request, HttpStatus status) {
		return ExceptionMessageModel.builder()
									.code(status.value())
									.message(exception.getMessage())
									.status(status)
									.path(request.getRequest().getRequestURI());
	}
}