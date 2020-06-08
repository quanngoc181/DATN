package com.hust.datn.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hust.datn.command.RegisterAccountCommand;
import com.hust.datn.command.ResetPasswordCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.ResetPasswordToken;
import com.hust.datn.entity.Users;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.ResetPasswordTokenRepository;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.service.AccountService;
import com.hust.datn.service.EmailService;
import com.hust.datn.utilities.DateUtilities;

@Controller
public class AccountController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	AccountService accountService;
	
	@Autowired
	EmailService emailService;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	@ResponseBody
	public void register1(@Valid RegisterAccountCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}

		command.validate();

		if (userDetailsManager.userExists(command.username))
			throw new InternalException("Tài khoản này đã tồn tại");

		UserDetails user = User.builder().username(command.username).password(passwordEncoder.encode(command.password))
				.roles("USER").build();
		userDetailsManager.createUser(user);

		int accNum = accountService.generateAccountNumber();
		Account account = new Account(null, command.username, command.firstName, command.lastName, accNum,
				DateUtilities.parseDate(command.birthday), command.gender, command.phone, command.email,
				command.address, 0, null);
		account.initReceiveAddress();
		accountRepository.save(account);
	}

	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	@ResponseBody
	public String forgotPassword1(String username) throws InternalException {
		Account account = accountRepository.findByUsername(username);
		if(account == null)
			throw new InternalException("Tài khoản không tồn tại");
		
		UUID userId = account.getId();
		UUID token = UUID.randomUUID();
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime expiredTime = time.plusHours(1);
		
		ResetPasswordToken rpt = resetPasswordTokenRepository.findByUserId(userId);
		if(rpt == null)
			resetPasswordTokenRepository.save(new ResetPasswordToken(userId, token, expiredTime));
		else {
			rpt.setToken(token);
			rpt.setExpiredTime(expiredTime);
			resetPasswordTokenRepository.save(rpt);
		}
		
		try {
			emailService.sendResetPasswordEmail(account.getEmail(), token, username);
		} catch (MailException ex) {
			throw new InternalException("Không thể gửi email đến " + account.getEmail());
		}

		return "Vui lòng kiểm tra hòm thư " + account.getEmail() + " để lấy lại mật khẩu";
	}
	
	@GetMapping("/reset-password")
	public String resetPassword(String rpt, Model model) {
		ResetPasswordToken reset = resetPasswordTokenRepository.findByToken(UUID.fromString(rpt));
		
		if(reset == null || reset.getExpiredTime().compareTo(LocalDateTime.now()) < 0) return "redirect:/";
		
		model.addAttribute("rpt", rpt);
		
		return "reset-password";
	}
	
	@PostMapping("/reset-password")
	@ResponseBody
	public void resetPassword1(@Valid ResetPasswordCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}

		command.validate();
		
		ResetPasswordToken rpt = resetPasswordTokenRepository.findByToken(UUID.fromString(command.token));
		
		if(rpt == null)
			throw new InternalException("Đã có lỗi, đặt mật khẩu thất bại");
		
		Account account = accountRepository.findById(rpt.getUserId()).get();
		Users user = userRepository.findByUsername(account.getUsername());
		user.setPassword(passwordEncoder.encode(command.newpass));
		
		userRepository.save(user);
	}
}
