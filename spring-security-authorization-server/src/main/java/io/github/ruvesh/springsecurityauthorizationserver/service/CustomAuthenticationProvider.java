package io.github.ruvesh.springsecurityauthorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails user = userDetailsService.loadUserByUsername(username);
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
}
