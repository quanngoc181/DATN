package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsManager userDetailsManager;

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
	
	@GetMapping("/login-success")
	public String loginSuccess(Authentication auth) {
		if(auth.getAuthorities().size() == 2)
			return "redirect:/admin";
		else return "redirect:/user";
	}
	
	@GetMapping("/change-password")
	public String changePassword() {
		return "change-password";
	}
	
	@PostMapping("/change-password")
	public String changePassword1(Authentication auth, String oldpass, String newpass, String confirmpass, Model model) {
		String password = userDetailsManager.loadUserByUsername(auth.getName()).getPassword();
		
		if(newpass.equals(confirmpass)) {
			if(!oldpass.equals(newpass)) {
				if(passwordEncoder.matches(oldpass, password)) {
					userDetailsManager.changePassword(password, passwordEncoder.encode(newpass));
					model.addAttribute("message", "Password changed successfully");
				} else {
					model.addAttribute("message", "Wrong current password");
				}
			} else {
				model.addAttribute("message", "No change in password");
			}
		} else {
			model.addAttribute("message", "Wrong confirm password");
		}
		
		return "change-password";
	}

}
