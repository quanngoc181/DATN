package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;

@Controller
public class AccountController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String register1(@ModelAttribute RegisterAccountCommand command, Model model) {
		try {
			command.validate();
			
			if(userDetailsManager.userExists(command.username))
				throw new Exception("Username exist");

			UserDetails user = User
					.builder()
					.username(command.username)
					.password(passwordEncoder.encode(command.password))
					.roles("USER")
					.build();
			
			userDetailsManager.createUser(user);
			
			Account account = new Account(null, command.username, command.firstName, command.lastName);
			accountRepository.save(account);

			model.addAttribute("message", "Registered successfully");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "register";
	}
}