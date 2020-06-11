package com.hust.datn.controller;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.ReceiveAddressRepository;
import com.hust.datn.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ReceiveAddressRepository receiveAddressRepository;
	
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
	
	@GetMapping("/user/order-preview/get-address")
	@ResponseBody
	public ModelAndView getAddress(Authentication auth) {
		Account account = accountRepository.findByUsername(auth.getName());
		
		return new ModelAndView("partial/address-list", "addresses", account.getReceiveAddresses());
	}
	
	@PostMapping("/user/order-preview/change-address")
	@ResponseBody
	public ModelAndView changeAddress(String id) {
		ReceiveAddress address = receiveAddressRepository.findById(UUID.fromString(id)).get();
		
		return new ModelAndView("partial/receive-address", "address", address);
	}
}
