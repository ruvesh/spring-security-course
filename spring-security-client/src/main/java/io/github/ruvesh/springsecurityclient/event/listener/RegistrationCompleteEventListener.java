package io.github.ruvesh.springsecurityclient.event.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.github.ruvesh.springsecurityclient.entity.User;
import io.github.ruvesh.springsecurityclient.event.RegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		String applicationUrl = event.getApplicationUrl();
		
		log.info(applicationUrl);
	}

}
