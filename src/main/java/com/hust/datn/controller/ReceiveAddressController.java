package com.hust.datn.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;

@Controller
public class ReceiveAddressController {
	@Autowired
	AccountRepository accountRepository;
	
	@PostMapping("/user/receive-address/add")
	public String addReceiveAddress(Authentication auth, @ModelAttribute ReceiveAddress receiveAddress) {
		Account account = accountRepository.getByUsername(auth.getName());
		
		account.addReceiveAddress(receiveAddress);
		
		accountRepository.save(account);
		
		return "redirect:/user/update-info";
	}
	
	@GetMapping("/user/receive-address/count")
	@ResponseBody
	public int countReceiveAddress(Authentication auth) {
		Account account = accountRepository.getByUsername(auth.getName());
		
		return account.countReceiveAddress();
	}
	
	@PostMapping("/user/receive-address/set-default")
	@ResponseBody
	public void setDefaultAddress(Authentication auth, String id) {
		Account account = accountRepository.getByUsername(auth.getName());

		account.setDefaultAddress(UUID.fromString(id));
		
		accountRepository.save(account);
		
		return;
	}
	
	@PostMapping("/user/receive-address/delete")
	@ResponseBody
	public void deleteReceiveAddress(Authentication auth, String id) throws InternalException {
		Account account = accountRepository.getByUsername(auth.getName());

		account.deleteReceiveAddress(UUID.fromString(id));
		
		accountRepository.save(account);
		
		return;
	}
}
