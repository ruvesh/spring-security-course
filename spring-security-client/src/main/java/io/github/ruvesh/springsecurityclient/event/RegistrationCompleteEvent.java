package io.github.ruvesh.springsecurityclient.event;

import org.springframework.context.ApplicationEvent;

import io.github.ruvesh.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 9145578542864865543L;

	private User user;

	private String applicationBaseUrl;

	public RegistrationCompleteEvent(User user, String applicationBaseUrl) {
		super(user);
		this.user = user;
		this.applicationBaseUrl = applicationBaseUrl;
	}

}
