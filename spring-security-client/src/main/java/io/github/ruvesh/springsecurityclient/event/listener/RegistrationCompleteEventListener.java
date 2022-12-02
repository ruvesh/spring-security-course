package io.github.ruvesh.springsecurityclient.event.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.event.RegistrationCompleteEvent;
import io.github.ruvesh.springsecurityclient.service.UserService;
import io.github.ruvesh.springsecurityclient.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	private static final String VERIFICATION_URL = "/users/verify";

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		String token = UUID.randomUUID().toString();
		User user = event.getUser();
		String verificationUrl = CommonUtil.prepareRestEndpoint(event.getApplicationBaseUrl(), VERIFICATION_URL,
				prepareQueryParameters(token));
		userService.saveVerificationToken(user, token);
		// TODO Add Email trigger framework
		log.info(verificationUrl);
	}

	private Map<String, String> prepareQueryParameters(String token) {
		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("token", token);
		return queryParameters;
	}

}
