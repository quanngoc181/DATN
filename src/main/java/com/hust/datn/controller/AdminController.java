package com.hust.datn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.AccountDTO;
import com.hust.datn.dto.StatisticalDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.AuthoritiesRepository;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.service.StatisticalService;

@Controller
public class AdminController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	StatisticalService statisticalService;
	
	@GetMapping("/admin")
	public String adminIndex(Model model) {
		List<Account> accounts = accountRepository.findAll();
		
		int allAccount = accounts.size();
		
		int enabledAccount = userRepository.countByEnabled(true);
		int disabledAccount = allAccount - enabledAccount;
		
		int adminAccount = authoritiesRepository.countByAuthority("ROLE_ADMIN");
		int memberAccount = allAccount - adminAccount;
		
		List<String> monthsAccount = statisticalService.getListCreateMonth(accounts);
		List<Integer> amountsAccount = statisticalService.getAccountAmountByMonths(accounts);
		
		model.addAttribute("account", new StatisticalDTO(allAccount, enabledAccount, disabledAccount, adminAccount, memberAccount, monthsAccount, amountsAccount));
		
		return "admin/index";
	}
	
	@GetMapping("/admin/getInfo")
	@ResponseBody
	public AccountDTO getInfo(Authentication auth) {
		Account account = accountRepository.findByUsername(auth.getName());
		return new AccountDTO(account);
	}
}
