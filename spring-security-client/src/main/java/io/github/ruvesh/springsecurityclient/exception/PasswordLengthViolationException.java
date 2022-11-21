package io.github.ruvesh.springsecurityclient.exception;

public class PasswordLengthViolationException extends Exception {

	private static final long serialVersionUID = -1164803016859425206L;

	@Override
	public String getMessage() {
		return "Password Length should be between 8 to 32 characters";
	}
	
	

}
