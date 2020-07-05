package com.hust.datn.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hust.datn.entity.Account;

@Service
public class StatisticalService {

	public StatisticalService() {
		super();
	}

	public List<String> getListCreateMonth(List<Account> accounts) {
		List<String> months = new ArrayList<String>();

		for (Account account : accounts) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

	        String month = account.getCreateAt().format(formatter);
			if (!months.contains(month)) {
				months.add(month);
			}
		}

		return months;
	}

	public List<Integer> getAccountAmountByMonths(List<Account> accounts) {
		List<Integer> months = new ArrayList<Integer>();

		for (Account account : accounts) {
			int month = account.getCreateAt().getMonthValue();
			if (!months.contains(month)) {
				months.add(month);
			}
		}

		List<Integer> amounts = new ArrayList<Integer>();
		List<Integer> creates = accounts.stream().map(acc -> acc.getCreateAt().getMonthValue()).collect(Collectors.toList());

		for (Integer month : months) {
			int amount = Collections.frequency(creates, month);
			amounts.add(amount);
		}

		return amounts;
	}
}
