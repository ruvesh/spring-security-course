package io.github.ruvesh.springsecurityresourceserver.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedResourceController {

	@GetMapping("/authorizedhello")
	public String authorizedHello(Principal principal) {
		return "Hello " + principal.getName();
	}
}
