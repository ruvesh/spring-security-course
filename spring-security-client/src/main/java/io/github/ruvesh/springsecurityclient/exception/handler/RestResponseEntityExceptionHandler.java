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
import io.github.ruvesh.springsecurityclient.exception.UserAlreadyVerifiedException;
import io.github.ruvesh.springsecurityclient.exception.UserBlockedException;
import io.github.ruvesh.springsecurityclient.exception.UserNotFoundException;
import io.github.ruvesh.springsecurityclient.exception.UserVerificationException;
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
	
	@ExceptionHandler(UserVerificationException.class)
	public ResponseEntity<ExceptionMessageModel> handleUserVerificationException(UserVerificationException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.UNAUTHORIZED)
				.build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
	}
	
	@ExceptionHandler(UserAlreadyVerifiedException.class)
	public ResponseEntity<ExceptionMessageModel> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.CONFLICT)
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMessage);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionMessageModel> handleUserNotFoundException(UserNotFoundException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.NOT_FOUND)
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
	}
	
	@ExceptionHandler(UserBlockedException.class)
	public ResponseEntity<ExceptionMessageModel> handleUserBlockedException(UserBlockedException exception, ServletWebRequest request){
		ExceptionMessageModel responseMessage = commonExceptionMessageModelBuilder(exception, request, HttpStatus.FORBIDDEN)
				.build();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseMessage);
	}
	
	private ExceptionMessageModelBuilder commonExceptionMessageModelBuilder(Exception exception, ServletWebRequest request, HttpStatus status) {
		return ExceptionMessageModel.builder()
									.code(status.value())
									.message(exception.getMessage())
									.status(status)
									.path(request.getRequest().getRequestURI());
	}
}
