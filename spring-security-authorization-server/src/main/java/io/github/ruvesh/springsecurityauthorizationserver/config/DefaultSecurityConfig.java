package io.github.ruvesh.springsecurityauthorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.github.ruvesh.springsecurityauthorizationserver.service.CustomAuthenticationProvider;

@EnableWebSecurity
public class DefaultSecurityConfig {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).formLogin(Customizer.withDefaults());
		return http.build();
	}

	@Autowired
	public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
	}

}
