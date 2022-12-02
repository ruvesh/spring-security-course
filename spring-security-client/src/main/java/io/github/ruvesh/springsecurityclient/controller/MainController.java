package io.github.ruvesh.springsecurityclient.controller;



import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {
	
	
	@GetMapping("/hello")
	public String hello(Principal principal) {
		return "Hello " + principal.getName();
	}

}
