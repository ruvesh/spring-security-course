package io.github.ruvesh.springsecurityauthorizationserver.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "service_users",
		uniqueConstraints = @UniqueConstraint(
					columnNames = "email"
				)
		)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	
	private String email;
	
	@Column(length = 60)
	private String password;
	
	private String role;
	
	@Builder.Default
	private Boolean isEnabled=false;
	
	@Builder.Default
	private Boolean isBlocked=false;
	
}
