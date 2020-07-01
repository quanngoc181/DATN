package com.hust.datn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hust.datn.dto.SettingDTO;
import com.hust.datn.service.ConstantService;

@Controller
public class DefaultController {
	@Autowired
	ConstantService constantService;
	
	@GetMapping("/")
	public String Default(Authentication auth, Model model) {
		if(auth == null) {
			SettingDTO setting = constantService.getAll();
			model.addAttribute("setting", setting);
			return "index";
		} else if(auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
			return "redirect:/admin";
		}
		return "redirect:/user";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
