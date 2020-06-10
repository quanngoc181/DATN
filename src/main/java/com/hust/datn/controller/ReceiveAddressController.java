package com.hust.datn.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.service.ConstantService;

@Controller
public class ReceiveAddressController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ConstantService constantService;
	
	@PostMapping("/user/receive-address/add")
	public String addReceiveAddress(Authentication auth, @ModelAttribute ReceiveAddress receiveAddress) {
		Account account = accountRepository.findByUsername(auth.getName());
		
		account.addReceiveAddress(receiveAddress);
		
		accountRepository.save(account);
		
		return "redirect:/user/update-info";
	}
	
	@GetMapping("/user/receive-address/add")
	@ResponseBody
	public ModelAndView addReceiveAddress(Authentication auth) throws InternalException {
		Account account = accountRepository.findByUsername(auth.getName());
		
		int receiveLimit = constantService.getAll().receiveAddressLimit;
		
		int count = account.countReceiveAddress();
		if(count >= receiveLimit)
			throw new InternalException("Số lượng địa chỉ đã đạt tối đa");
		
		return new ModelAndView("partial/add-receive-address");
	}
	
	@PostMapping("/user/receive-address/set-default")
	@ResponseBody
	public void setDefaultAddress(Authentication auth, String id) {
		Account account = accountRepository.findByUsername(auth.getName());

		account.setDefaultAddress(UUID.fromString(id));
		
		accountRepository.save(account);
	}
	
	@PostMapping("/user/receive-address/delete")
	@ResponseBody
	public void deleteReceiveAddress(Authentication auth, String id) throws InternalException {
		Account account = accountRepository.findByUsername(auth.getName());

		account.deleteReceiveAddress(UUID.fromString(id));
		
		accountRepository.save(account);
		
		return;
	}
	
	@GetMapping("/user/receive-address/edit")
	@ResponseBody
	public ModelAndView editReceiveAddress(Authentication auth, String id) throws InternalException {
		Account account = accountRepository.findByUsername(auth.getName());
		
		ReceiveAddress address = account.findReceiveAddress(UUID.fromString(id));
		if(address == null)
			throw new InternalException("Không tìm thấy địa chỉ");
		
		return new ModelAndView("partial/edit-receive-address", "address", address);
	}
	
	@PostMapping("/user/receive-address/edit")
	public String editReceiveAddress(Authentication auth, @ModelAttribute ReceiveAddress receiveAddress) {
		Account account = accountRepository.findByUsername(auth.getName());
		
		account.editReceiveAddress(receiveAddress);
		
		accountRepository.save(account);
		
		return "redirect:/user/update-info";
	}
}
