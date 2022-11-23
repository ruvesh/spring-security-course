package io.github.ruvesh.springsecurityclient.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -2205196112718628868L;
	
	private String email;

	@Override
	public String getMessage() {
		return "User not found with email: " + this.email;
	}

	public UserNotFoundException(String email) {
		super();
		this.email = email;
	}
	
}
