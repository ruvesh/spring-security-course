package io.github.ruvesh.springsecurityclient.exception;

public class PasswordMismatchException extends Exception {

	private static final long serialVersionUID = 5839346944854820371L;
	

	@Override
	public String getMessage() {
		return "Passwords do not match";
	}
	
}
