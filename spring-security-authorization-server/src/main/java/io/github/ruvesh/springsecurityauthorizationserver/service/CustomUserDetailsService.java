package io.github.ruvesh.springsecurityauthorizationserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.ruvesh.springsecurityauthorizationserver.entity.User;
import io.github.ruvesh.springsecurityauthorizationserver.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User with id: " + email + " does not exist!"));
		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), user.getIsEnabled(),
				true, true, !user.getIsBlocked(), getAuthorities(List.of(user.getRole())));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
		return authorities;
	}

}
