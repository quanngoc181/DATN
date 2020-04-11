package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsManager userDetailsManager;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String register1(@ModelAttribute RegisterAccountCommand command, RedirectAttributes ra) {
		try {
			command.validate();

			if (userDetailsManager.userExists(command.username))
				throw new Exception("Tài khoản này đã tồn tại");

			UserDetails user = User.builder().username(command.username)
					.password(passwordEncoder.encode(command.password)).roles("USER").build();

			userDetailsManager.createUser(user);

			int accNum = accountService.generateAccountNumber();

			Account account = new Account(null, command.username, command.firstName, command.lastName, accNum,
					command.birthday, command.gender, command.phone, command.email, command.address, 0, null);
			
			account.initReceiveAddress();

			accountRepository.save(account);

			ra.addFlashAttribute("message", "Đăng ký tài khoản thành công");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/register";
	}
}
