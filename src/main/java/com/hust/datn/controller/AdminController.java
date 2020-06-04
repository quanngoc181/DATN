package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.AccountDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;

@Controller
public class AdminController {
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/admin")
	public String adminIndex() {
		return "admin/index";
	}
	
	@GetMapping("/admin/getInfo")
	@ResponseBody
	public AccountDTO getInfo(Authentication auth) {
		Account account = accountRepository.findByUsername(auth.getName());
		return new AccountDTO(account);
	}
}
