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

	public StatisticalDTO() {
		super();
	}

	public StatisticalDTO(int allAccount, int enabledAccount, int disabledAccount, int adminAccount, int memberAccount,
			List<String> monthsAccount, List<Integer> amountsAccount) {
		super();
		this.allAccount = allAccount;
		this.enabledAccount = enabledAccount;
		this.disabledAccount = disabledAccount;
		this.adminAccount = adminAccount;
		this.memberAccount = memberAccount;
		this.monthsAccount = monthsAccount;
		this.amountsAccount = amountsAccount;
	}

	public String getMonthsAccount() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(monthsAccount);
			System.out.println(json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
