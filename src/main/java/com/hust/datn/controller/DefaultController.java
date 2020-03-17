package com.hust.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

	@GetMapping("/")
	public String Default() {
		return "index";
	}
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}
	
	@GetMapping("/admin")
	public String adminIndex() {
		return "admin/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
