package io.github.ruvesh.springsecurityclient.service;

import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.exception.UserAlreadyVerifiedException;
import io.github.ruvesh.springsecurityclient.exception.UserBlockedException;
import io.github.ruvesh.springsecurityclient.exception.UserNotFoundException;
import io.github.ruvesh.springsecurityclient.exception.UserVerificationException;
import io.github.ruvesh.springsecurityclient.model.UserModel;

public interface UserService {
	
	User registerUser(UserModel userModel) throws PasswordMismatchException, PasswordLengthViolationException;

	void saveVerificationToken(User user, String token);

	void verifyUser(String token) throws UserVerificationException, UserAlreadyVerifiedException;

	User checkUserAndVerificationStatus(String emailId) throws UserNotFoundException, UserAlreadyVerifiedException, UserBlockedException;

}
