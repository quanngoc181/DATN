package com.hust.datn.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatisticalDTO {
	public int allAccount;
	public int enabledAccount;
	public int disabledAccount;
	public int adminAccount;
	public int memberAccount;
	public List<String> monthsAccount;
	public List<Integer> amountsAccount;
	public List<Integer> amountsOrder;
	public List<Integer> valuesOrder;
	
	public int allOrder;
	public int unpaidOrder;
	public int paidOrder;
	public int processingOrder;
	public int deliveringOrder;
	public int completedOrder;
	public int pickupOrder;

	public StatisticalDTO() {
		super();
	}

	public StatisticalDTO(int allAccount, int enabledAccount, int disabledAccount, int adminAccount, int memberAccount,
			List<String> monthsAccount, List<Integer> amountsAccount, List<Integer> amountsOrder, List<Integer> valuesOrder, int allOrder, int unpaidOrder, int paidOrder,
			int processingOrder, int deliveringOrder, int completedOrder, int pickupOrder) {
		super();
		this.allAccount = allAccount;
		this.enabledAccount = enabledAccount;
		this.disabledAccount = disabledAccount;
		this.adminAccount = adminAccount;
		this.memberAccount = memberAccount;
		this.monthsAccount = monthsAccount;
		this.amountsAccount = amountsAccount;
		this.amountsOrder = amountsOrder;
		this.valuesOrder = valuesOrder;
		this.allOrder = allOrder;
		this.unpaidOrder = unpaidOrder;
		this.paidOrder = paidOrder;
		this.processingOrder = processingOrder;
		this.deliveringOrder = deliveringOrder;
		this.completedOrder = completedOrder;
		this.pickupOrder = pickupOrder;
	}

	public String getMonthsAccount() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(monthsAccount);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}
}
