package io.github.ruvesh.springsecurityclient.exception;

public class UserBlockedException extends Exception {

	private static final long serialVersionUID = 1581908173058525334L;
	
	private String email;

	public UserBlockedException(String email) {
		super();
		this.email = email;
	}

	@Override
	public String getMessage() {
		return "User with email: " + this.email + " has been blocked due to possible violation of guidelines.";
	}
	
	

}
