package com.hust.datn.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hust.datn.entity.Account;
import com.hust.datn.entity.Order;

@Service
public class StatisticalService {

	public StatisticalService() {
		super();
	}

	public List<String> getListCreateMonth() {
		List<String> months = new ArrayList<String>();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(int i=0; i<12; i++) {
			LocalDateTime time = now.minus(Period.ofMonths(i));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

	        String month = time.format(formatter);
			if (!months.contains(month)) {
				months.add(0, month);
			}
		}

		return months;
	}

	public List<Integer> getAccountAmountByMonths(List<Account> accounts) {
		List<String> months = new ArrayList<String>();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(int i=0; i<12; i++) {
			LocalDateTime time = now.minus(Period.ofMonths(i));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

	        String month = time.format(formatter);
			if (!months.contains(month)) {
				months.add(0, month);
			}
		}

		List<Integer> amounts = new ArrayList<Integer>();
		List<String> createList = accounts.stream().map(acc -> acc.getCreateAt().format(DateTimeFormatter.ofPattern("MM/yy"))).collect(Collectors.toList());

		for (String month : months) {
			int amount = Collections.frequency(createList, month);
			amounts.add(amount);
		}

		return amounts;
	}
	
	public List<Integer> getOrderAmountByMonths(List<Order> orders) {
		List<String> months = new ArrayList<String>();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(int i=0; i<12; i++) {
			LocalDateTime time = now.minus(Period.ofMonths(i));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

	        String month = time.format(formatter);
			if (!months.contains(month)) {
				months.add(0, month);
			}
		}

		List<Integer> amounts = new ArrayList<Integer>();
		List<String> createList = orders.stream().map(acc -> acc.getCreateAt().format(DateTimeFormatter.ofPattern("MM/yy"))).collect(Collectors.toList());

		for (String month : months) {
			int amount = Collections.frequency(createList, month);
			amounts.add(amount);
		}

		return amounts;
	}
	
	public List<Integer> getOrderValueByMonths(List<Order> orders) {
		List<String> months = new ArrayList<String>();
		
		LocalDateTime now = LocalDateTime.now();
		
		for(int i=0; i<12; i++) {
			LocalDateTime time = now.minus(Period.ofMonths(i));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

	        String month = time.format(formatter);
			if (!months.contains(month)) {
				months.add(0, month);
			}
		}

		List<Integer> values = new ArrayList<Integer>();

		for (String month : months) {
			int m = Integer.parseInt(month.substring(0, 2));
			int y = Integer.parseInt(month.substring(3, 7));
			int value = orders.stream().filter(obj -> obj.getCreateAt().getMonthValue() == m && obj.getCreateAt().getYear() == y)
						.map(obj -> obj.getCost())
						.mapToInt(Integer::intValue)
						.sum();
			values.add(value);
		}

		return values;
	}
}
