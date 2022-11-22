package io.github.ruvesh.springsecurityclient.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.ruvesh.springsecurityclient.dto.UserDTO;
import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.event.RegistrationCompleteEvent;
import io.github.ruvesh.springsecurityclient.exception.PasswordLengthViolationException;
import io.github.ruvesh.springsecurityclient.exception.PasswordMismatchException;
import io.github.ruvesh.springsecurityclient.exception.UserAlreadyVerifiedException;
import io.github.ruvesh.springsecurityclient.exception.UserVerificationException;
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

	private static final String VERIFY_RESEND_URL = "/users/%s/verify/resend";

	@PostMapping("/register")
	public ResponseEntity<RestResponseModel> registerUser(@RequestBody UserModel userModel,
			final HttpServletRequest request) throws PasswordMismatchException, PasswordLengthViolationException {

		User user = userService.registerUser(userModel);
		String applicationBaseUrl = CommonUtil.prepareApplicationUrlFromHttpRequest(request);
		eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationBaseUrl));
		return ResponseEntity.status(HttpStatus.CREATED).body(RestResponseModel.builder()
				.message("User Created Successfully. A verification mail has been sent to the registed email id.")
				.extras(List.of(CommonUtil.prepareRestEndpoint(applicationBaseUrl,
						String.format(VERIFY_RESEND_URL, user.getId()), null)))
				.code(HttpStatus.CREATED.value()).status(HttpStatus.CREATED).data(new UserDTO(user)).build());
	}

	@GetMapping("/verify")
	public ResponseEntity<RestResponseModel> verifyUser(@RequestParam("token") String token)
			throws UserVerificationException, UserAlreadyVerifiedException {
		userService.verifyUser(token);
		return ResponseEntity.ok(
				RestResponseModel.builder().message("User Verification Successful. You may now log in to the platform.")
						.status(HttpStatus.OK).code(HttpStatus.OK.value()).build());
	}

	@GetMapping("/{id}/verify/resend")
	public ResponseEntity<RestResponseModel> resendVerificationLink(@PathVariable("id") Long userId, final HttpServletRequest request) {
		// TODO
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
				.body(RestResponseModel.builder().message("Will be added in future commits")
						.code(HttpStatus.NOT_IMPLEMENTED.value()).status(HttpStatus.NOT_IMPLEMENTED).build());
	}
}
