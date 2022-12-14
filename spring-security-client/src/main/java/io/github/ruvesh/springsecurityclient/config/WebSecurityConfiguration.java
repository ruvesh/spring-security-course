package io.github.ruvesh.springsecurityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfiguration {

	private static final String[] PERMITTED_URLS = { "/users/**" };
	private static final String[] PROTECTED_URLS = { "/api/**" };

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.antMatchers(PERMITTED_URLS).permitAll()
				.antMatchers(PROTECTED_URLS).authenticated()
				.and()
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/api-client-oidc"))
				.oauth2Client(Customizer.withDefaults());
		
		return http.build();
	}
}
