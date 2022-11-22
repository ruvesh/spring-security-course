package io.github.ruvesh.springsecurityclient.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVerificationException extends Exception {

	private static final long serialVersionUID = 8177939344435830788L;

	private String reason;

	@Override
	public String getMessage() {
		return "User Verification Failed: " + this.reason;
	}

	public UserVerificationException(String reason) {
		super();
		this.reason = reason;
	}

}
