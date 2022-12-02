package io.github.ruvesh.springsecurityauthorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException, DisabledException, LockedException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails user = userDetailsService.loadUserByUsername(username);
		checkUserEnabledAndAccountNonLocked(user);
		return checkPassword(user, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private Authentication checkPassword(UserDetails user, String password) throws BadCredentialsException {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Bad Credentials");
		}
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	private void checkUserEnabledAndAccountNonLocked(UserDetails user) throws DisabledException, LockedException {
		if (!user.isEnabled())
			throw new DisabledException("Account is disabled. Please verify with the link we sent on email.");
		if (!user.isAccountNonLocked())
			throw new LockedException(
					"Your account has been locked due to violations. Please reach out to us over email if you think this is a mistake at our end",
					null);

	}
}
