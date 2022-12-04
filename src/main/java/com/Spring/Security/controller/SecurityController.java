package com.Spring.Security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
//	@Autowired
//	UserRepository repo;
	
//	@Autowired
//	AuthorityRepository Arepo;
	
	@GetMapping("/")
	public String account1() {
		
		return "Welcome from Account";
	}
	
	@GetMapping("/account")
	public String account() {
		
		return "Welcome from Account";
	}
	
	@GetMapping("/contact")
	public String contact() {
		
		return "Welcome from Contact";
	}

}
