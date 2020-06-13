package com.hust.datn.dto;

public class UserMessageDTO {
	public String sender;
	
	public String date;
	
	public String message;
	
	public String avatar;
	
	public boolean original;
	
	public UserMessageDTO() {
	}

	public UserMessageDTO(String sender, String date, String message, String avatar, boolean original) {
		super();
		this.sender = sender;
		this.date = date;
		this.message = message;
		this.avatar = avatar;
		this.original = original;
	}
}
