package com.hust.datn.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
	@GetMapping("/")
	public String Default(Authentication auth) {
		if(auth == null)
			return "index";
		else if(auth.getAuthorities().size() == 1)
			return "redirect:/user";
		return "redirect:/admin";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
