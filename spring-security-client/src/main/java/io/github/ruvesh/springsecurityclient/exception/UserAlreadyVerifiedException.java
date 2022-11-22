package io.github.ruvesh.springsecurityclient.exception;

public class UserAlreadyVerifiedException extends Exception {

	private static final long serialVersionUID = -3444836619602540182L;

	@Override
	public String getMessage() {
		return "User is already verified. Re-verification is not required.";
	}
	
	
}
