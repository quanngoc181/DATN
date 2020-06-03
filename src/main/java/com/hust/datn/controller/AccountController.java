package com.hust.datn.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.service.AccountService;
import com.hust.datn.utilities.DateUtilities;

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
	@ResponseBody
	public void register1(@Valid RegisterAccountCommand command, BindingResult result) throws InternalException {
		if(result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}

		command.validate();

		if (userDetailsManager.userExists(command.username))
			throw new InternalException("Tài khoản này đã tồn tại");

		UserDetails user = User.builder().username(command.username)
				.password(passwordEncoder.encode(command.password)).roles("USER").build();
		userDetailsManager.createUser(user);

		int accNum = accountService.generateAccountNumber();
		Account account = new Account(null, command.username, command.firstName, command.lastName, accNum,
				DateUtilities.parseDate(command.birthday), command.gender, command.phone, command.email, command.address, 0, null);
		account.initReceiveAddress();
		accountRepository.save(account);
	}
}
