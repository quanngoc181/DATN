package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.AccountDTO;
import com.hust.datn.dto.OrderStatisticDTO;
import com.hust.datn.dto.StatisticalDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Order;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.enums.OrderType;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.AuthoritiesRepository;
import com.hust.datn.repository.OrderRepository;
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
	OrderRepository orderRepository;

	@Autowired
	StatisticalService statisticalService;

	@GetMapping("/admin")
	public String adminIndex(Model model) {
		List<Account> accounts = accountRepository.findAll();
		List<Order> orders = orderRepository.findAll();

		int allAccount = accounts.size();

		int enabledAccount = userRepository.countByEnabled(true);
		int disabledAccount = allAccount - enabledAccount;

		int adminAccount = authoritiesRepository.countByAuthority("ROLE_ADMIN");
		int memberAccount = allAccount - adminAccount;

		List<String> monthsAccount = statisticalService.getListCreateMonth();
		List<Integer> amountsAccount = statisticalService.getAccountAmountByMonths(accounts);
		List<Integer> amountsOrder = statisticalService.getOrderAmountByMonths(orders);
		List<Integer> valuesOrder = statisticalService.getOrderValueByMonths(orders);

		int allOrder = orders.size();
		int unpaidOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.UNPAID)
				.count();
		int paidOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PAID)
				.count();
		int processingOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PROCESSING)
				.count();
		int deliveringOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.DELIVERING)
				.count();
		int completedOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.COMPLETED)
				.count();
		int pickupOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.PICKUP && order.getStatus() == OrderStatus.COMPLETED)
				.count();

		model.addAttribute("statistic",
				new StatisticalDTO(allAccount, enabledAccount, disabledAccount, adminAccount, memberAccount,
						monthsAccount, amountsAccount, amountsOrder, valuesOrder, allOrder, unpaidOrder, paidOrder, processingOrder,
						deliveringOrder, completedOrder, pickupOrder));

		return "admin/index";
	}
	
	@GetMapping("/admin/statistic")
	@ResponseBody
	public OrderStatisticDTO getStatistic(int month, int year) {
		List<Order> orders = orderRepository.findAll();
		
		orders = orders.stream().filter(obj -> obj.getCreateAt().getMonthValue() == month && obj.getCreateAt().getYear() == year).collect(Collectors.toList());

		int unpaidOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.UNPAID)
				.count();
		int paidOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PAID)
				.count();
		int processingOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PROCESSING)
				.count();
		int deliveringOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.DELIVERING)
				.count();
		int completedOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.COMPLETED)
				.count();
		int pickupOrder = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.PICKUP && order.getStatus() == OrderStatus.COMPLETED)
				.count();
		
		List<Integer> amounts = new ArrayList<Integer>();
		amounts.add(unpaidOrder);
		amounts.add(paidOrder);
		amounts.add(processingOrder);
		amounts.add(deliveringOrder);
		amounts.add(completedOrder);
		amounts.add(pickupOrder);
		
		int unpaidValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.UNPAID)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		int paidValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PAID)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		int processingValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PROCESSING)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		int deliveringValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.DELIVERING)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		int completedValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.COMPLETED)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		int pickupValue = (int) orders.stream()
				.filter(order -> order.getType() == OrderType.PICKUP && order.getStatus() == OrderStatus.COMPLETED)
				.map(obj -> obj.getCost())
				.mapToInt(Integer::intValue).sum();
		
		List<Integer> values = new ArrayList<Integer>();
		values.add(unpaidValue);
		values.add(paidValue);
		values.add(processingValue);
		values.add(deliveringValue);
		values.add(completedValue);
		values.add(pickupValue);
		
		return new OrderStatisticDTO(amounts, values);
	}

	@GetMapping("/admin/getInfo")
	@ResponseBody
	public AccountDTO getInfo(Authentication auth) {
		Account account = accountRepository.findByUsername(auth.getName());
		return new AccountDTO(account);
	}
}
