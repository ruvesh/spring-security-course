package io.github.ruvesh.springsecurityclient.dto;

import io.github.ruvesh.springsecurityclient.entity.User;
import lombok.Data;

@Data
public class UserDTO {
	
	private String firstName;
	private String lastName;
	private String email;
	
	public UserDTO(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
	}

}
