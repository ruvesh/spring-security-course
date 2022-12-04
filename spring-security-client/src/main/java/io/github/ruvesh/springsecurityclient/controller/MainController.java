package io.github.ruvesh.springsecurityclient.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	private WebClient webClient;

	@GetMapping("/hello")
	public String hello(Principal principal) {
		return "Hello " + principal.getName();
	}

	@GetMapping("/authorizedhello")
	public String authorizedHelloFromResourceServer(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client) {
		return webClient
				.get()
				.uri("http://127.0.0.1:8090/protected/authorizedhello")
				.attributes(oauth2AuthorizedClient(client))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
}
