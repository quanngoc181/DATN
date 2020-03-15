package com.hust.datn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;

@Controller
public class DefaultController {
	
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/")
	public String Default() {
		List<Account> accounts = accountRepository.findAll();
		
		for (Account account : accounts) {
			System.out.println("\nId: " + account.getId());
			System.out.println("\nUsername: " + account.getUsername());
			System.out.println("\nPassword: " + account.getPassword());
		}
		
		return "index";
	}

}
