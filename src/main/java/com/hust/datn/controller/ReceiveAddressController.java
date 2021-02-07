package com.hust.datn.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.command.AddAddressCommand;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.ReceiveAddressRepository;
import com.hust.datn.service.ConstantService;

@Controller
public class ReceiveAddressController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ReceiveAddressRepository receiveAddressRepository;
	
	@Autowired
	ConstantService constantService;
	
	@PostMapping("/user/receive-address/add")
	@ResponseBody
	public ModelAndView addReceiveAddress(Authentication auth, @Valid AddAddressCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		Account account = accountRepository.findByUsername(auth.getName());
		
		ReceiveAddress address = new ReceiveAddress(null, command.addressName, command.name, command.phone, command.address, false);
		address.setAccount(account);
		
		receiveAddressRepository.save(address);
		
		return new ModelAndView("partial/receive-address1", "address", address);
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
	@ResponseBody
	public ModelAndView editReceiveAddress(Authentication auth, @Valid AddAddressCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		Optional<ReceiveAddress> optional = receiveAddressRepository.findById(UUID.fromString(command.id));
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy địa chỉ");
		
		ReceiveAddress address = optional.get();
		address.setAddressName(command.getAddressName());
		address.setName(command.getName());
		address.setAddress(command.getAddress());
		address.setPhone(command.getPhone());
		
		receiveAddressRepository.save(address);
		
		return new ModelAndView("partial/receive-address1", "address", address);
	}
}
