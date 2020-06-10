package com.hust.datn.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.Category;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/user/order-preview")
	public String orderPreview(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);
		
		String avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", avatar);
		
		model.addAttribute("order", orderService.createOrderDTO(account.getId()));
		
		return "user/order-preview";
	}
}
