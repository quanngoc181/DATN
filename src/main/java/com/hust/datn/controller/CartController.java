package com.hust.datn.controller;

import java.util.ArrayList;
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

import com.hust.datn.dto.CartDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Cart;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.service.CartService;

@Controller
public class CartController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;
	
	@GetMapping("/user/cart")
	public String index(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);
		
		List<Cart> carts = cartRepository.findByUserId(account.getId());
		List<CartDTO> dtos = new ArrayList<>();
		for (Cart cart : carts) {
			dtos.add(cartService.getCartDTO(cart));
		}
		model.addAttribute("carts", dtos);
		
		return "/user/cart";
	}
	
	@PostMapping("/user/cart/delete")
	@ResponseBody
	public void deleteCart(String id) {
		UUID cartId = UUID.fromString(id);
		cartRepository.deleteById(cartId);
	}
	
	@GetMapping("/user/cart/count")
	@ResponseBody
	public int countCart(Authentication auth) {
		UUID userId = accountRepository.findByUsername(auth.getName()).getId();
		return cartRepository.countByUserId(userId);
	}
	
	@PostMapping("/user/cart/update-amount")
	@ResponseBody
	public int updateAmount(String id, int amount) {
		Cart cart = cartRepository.findById(UUID.fromString(id)).get();
		cart.setAmount(amount);
		
		cartRepository.save(cart);
		
		return cartService.getDetailCost(cart);
	}
	
	@GetMapping("/user/cart/total-cost")
	@ResponseBody
	public int totalCost(Authentication auth) {
		int total = 0;
		
		UUID userId = accountRepository.findByUsername(auth.getName()).getId();
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		for (Cart cart : carts) {
			total += cartService.getDetailCost(cart);
		}
		
		return total;
	}
}
