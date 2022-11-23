package io.github.ruvesh.springsecurityclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.entity.VerificationToken;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.exception.UserAlreadyVerifiedException;
import io.github.ruvesh.springsecurityclient.exception.UserBlockedException;
import io.github.ruvesh.springsecurityclient.exception.UserNotFoundException;
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
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseGet(() -> new VerificationToken(user, token));
		verificationTokenRepository.save(verificationToken);

	}

	@Override
	public void verifyUser(String token) throws UserVerificationException, UserAlreadyVerifiedException {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new UserVerificationException("Invalid Token"));
		User user = verificationToken.getUser();
		if(user.getIsEnabled()) throw new UserAlreadyVerifiedException();
		if(CommonUtil.isTokenExpired(verificationToken.getExpiryTime())) {
			verificationTokenRepository.delete(verificationToken);
			throw new UserVerificationException("Token has expired");
		}
		user.setIsEnabled(true);
		userRepository.save(user);
	}

	@Override
	public User checkUserAndVerificationStatus(String emailId) throws UserNotFoundException, UserAlreadyVerifiedException, UserBlockedException {
		User user = userRepository.findByEmail(emailId).orElseThrow(() -> new UserNotFoundException(emailId));
		if(user.getIsBlocked()) throw new UserBlockedException(emailId);
		if(user.getIsEnabled()) throw new UserAlreadyVerifiedException();
		return user;
	}
}
