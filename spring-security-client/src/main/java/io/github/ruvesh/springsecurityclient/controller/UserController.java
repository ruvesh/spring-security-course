package io.github.ruvesh.springsecurityclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ruvesh.springsecurityclient.dto.UserDTO;
import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.event.RegistrationCompleteEvent;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.model.RestResponseModel;
import io.github.ruvesh.springsecurityclient.model.UserModel;
import io.github.ruvesh.springsecurityclient.service.UserService;
import io.github.ruvesh.springsecurityclient.util.CommonUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@PostMapping("/register")
	public ResponseEntity<RestResponseModel> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) throws PasswordMismatchException, PasswordLengthViolationException{
		
		User user = userService.registerUser(userModel);
		eventPublisher.publishEvent(new RegistrationCompleteEvent(user, CommonUtil.prepareApplicationUrlFromHttpRequest(request)));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body
				(
						RestResponseModel
							.builder()
							.message("User Created Successfully. A verification mail has been sent to the registed email id.")
							.code(HttpStatus.CREATED.value())
							.status(HttpStatus.CREATED)
							.data(new UserDTO(user)).build()
				);
	}
}
