package io.github.ruvesh.springsecurityclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.model.UserModel;
import io.github.ruvesh.springsecurityclient.repository.UserRepository;
import io.github.ruvesh.springsecurityclient.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserModel userModel) throws PasswordMismatchException, PasswordLengthViolationException{	
		if(!userModel.getPassword().equals(userModel.getRepeatPassword())) throw new PasswordMismatchException();	
		if(userModel.getPassword().length() < 8 || userModel.getPassword().length() > 32) throw new PasswordLengthViolationException();
		User user = User
					.builder()
					.firstName(userModel.getFirstName())
					.lastName(userModel.getLastName())
					.email(userModel.getEmail())
					.role("USER")
					.password(passwordEncoder.encode(userModel.getPassword()))
					.build();
		return userRepository.save(user);
	}

}
