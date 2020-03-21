package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;

@Controller
public class UserController {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}
	
	@GetMapping("/user/login-success")
	public String loginSuccess(Authentication auth) {
		if(auth.getAuthorities().size() == 2)
			return "redirect:/admin";
		else return "redirect:/user";
	}
	
	@GetMapping("/user/change-password")
	public String changePassword() {
		return "user/change-password";
	}
	
	@PostMapping("/user/change-password")
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
		
		return "user/change-password";
	}
	
	@GetMapping("/user/update-info")
	public String updateInfo(Authentication auth, Model model) {
		Account account = accountRepository.getByUsername(auth.getName());
		
		model.addAttribute("account", account);
		
		return "user/update-info";
	}
	
	@PostMapping("/user/update-info")
	public String updateInfo1(Authentication auth, RegisterAccountCommand command, Model model) {
		String username = auth.getName();
		
		Account account = accountRepository.getByUsername(username);
		account.setFirstName(command.firstName);
		account.setLastName(command.lastName);
		
		accountRepository.save(account);
		
		model.addAttribute("message", "Update info successfully");
		model.addAttribute("account", account);
		
		return "user/update-info";
	}
}
