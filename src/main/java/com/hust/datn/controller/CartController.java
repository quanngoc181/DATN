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
		
		String avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", avatar);
		
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
}
