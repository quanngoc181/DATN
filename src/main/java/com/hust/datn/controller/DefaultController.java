package com.hust.datn.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
		Account account = new Account(UUID.randomUUID(), LocalDateTime.now(), null, "quan", "123456");
		accountRepository.save(account);
		
		List<Account> accounts = accountRepository.findAll();
		
		for (Account acc : accounts) {
			System.out.println("\nId: " + acc.getId());
			System.out.println("\nUsername: " + acc.getUsername());
			System.out.println("\nPassword: " + acc.getPassword());
			System.out.println("\nCreateAt: " + acc.getCreateAt());
			System.out.println("\nUpdateAt: " + acc.getUpdateAt());
		}
		
		return "index";
	}

}
