package com.hust.datn.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	AccountRepository accountRepository;
	
	public AccountService() {
	}
	
	public int generateAccountNumber() {
		LocalDate now = LocalDate.now();
		List<Account> accounts = accountRepository.findAll();
		
		int retIndex = 1;
		
		for (Account account : accounts) {
			if(account.getCreateAt().toLocalDate().equals(now)) {
				String accNum = Integer.toString(account.getAccountNumber());
				int index = Integer.parseInt(accNum.substring(0, accNum.length() - 8));
				if(index >= retIndex) retIndex = index + 1;
			}
		}
		
		String formatDate = now.format(DateTimeFormatter.ofPattern("ddMMYYYY"));
		String retString = Integer.toString(retIndex).concat(formatDate);
		
		return Integer.parseInt(retString);
	}
}
