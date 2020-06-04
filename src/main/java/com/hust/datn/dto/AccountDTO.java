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
		this.avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		this.lastname = account.getLastName();
	}
}
