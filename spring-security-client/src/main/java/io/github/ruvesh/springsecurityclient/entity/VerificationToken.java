package io.github.ruvesh.springsecurityclient.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.github.ruvesh.springsecurityclient.util.CommonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verification_token")
@Data
@NoArgsConstructor
public class VerificationToken {
	private static final int EXPIRY_TIME_IN_MINUTES = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private Date expiryTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFICATION_TOKEN"))
	private User user;

	public VerificationToken(User user, String token) {
		super();
		this.user = user;
		this.token = token;
		this.expiryTime = CommonUtil.calculateExpiryTime(EXPIRY_TIME_IN_MINUTES);
	}
}
