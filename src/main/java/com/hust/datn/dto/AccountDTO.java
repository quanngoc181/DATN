package com.hust.datn.dto;

import java.util.Base64;

import com.hust.datn.entity.Account;

public class AccountDTO {
	public String avatar;
	public String lastname;
	
	public AccountDTO() {
		super();
	}

	public AccountDTO(String avatar, String lastname) {
		super();
		this.avatar = avatar;
		this.lastname = lastname;
	}
	
	public AccountDTO(Account account) {
		super();
		this.avatar = account.getAvatarString();
		this.lastname = account.getLastName();
	}
}
