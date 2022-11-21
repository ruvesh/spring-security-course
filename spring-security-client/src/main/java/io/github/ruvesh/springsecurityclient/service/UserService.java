package io.github.ruvesh.springsecurityclient.service;

import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.model.UserModel;

public interface UserService {
	
	User registerUser(UserModel userModel) throws PasswordMismatchException, PasswordLengthViolationException;

}
