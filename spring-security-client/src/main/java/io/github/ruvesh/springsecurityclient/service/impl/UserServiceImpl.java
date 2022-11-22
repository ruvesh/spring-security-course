package io.github.ruvesh.springsecurityclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.entity.VerificationToken;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.exception.UserAlreadyVerifiedException;
import io.github.ruvesh.springsecurityclient.exception.UserVerificationException;
import io.github.ruvesh.springsecurityclient.model.UserModel;
import io.github.ruvesh.springsecurityclient.repository.UserRepository;
import io.github.ruvesh.springsecurityclient.repository.VerificationTokenRepository;
import io.github.ruvesh.springsecurityclient.service.UserService;
import io.github.ruvesh.springsecurityclient.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserModel userModel) throws PasswordMismatchException, PasswordLengthViolationException {
		if (!userModel.getPassword().equals(userModel.getRepeatPassword()))
			throw new PasswordMismatchException();
		if (userModel.getPassword().length() < 8 || userModel.getPassword().length() > 32)
			throw new PasswordLengthViolationException();
		User user = User.builder().firstName(userModel.getFirstName()).lastName(userModel.getLastName())
				.email(userModel.getEmail()).role("USER").password(passwordEncoder.encode(userModel.getPassword()))
				.build();
		return userRepository.save(user);
	}

	@Override
	public void saveVerificationToken(User user, String token) {
		VerificationToken verificationToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verificationToken);

	}

	@Override
	public void verifyUser(String token) throws UserVerificationException, UserAlreadyVerifiedException {
		VerificationToken verificationToken = verificationTokenRepository.getByToken(token)
				.orElseThrow(() -> new UserVerificationException("Invalid Token"));
		if(CommonUtil.isTokenExpired(verificationToken.getExpiryTime())) throw new UserVerificationException("Token has expired");
		User user = verificationToken.getUser();
		if(user.getIsEnabled()) throw new UserAlreadyVerifiedException();
		user.setIsEnabled(true);
		userRepository.save(user);
	}

}
