package com.hust.datn.dto;

public class ContactItem {
	public String username;
	
	public String avatar;
	
	public String name;
	
	public boolean seen;
	
	public ContactItem() {
	}

	public ContactItem(String username, String avatar, String name, boolean seen) {
		super();
		this.username = username;
		this.avatar = avatar;
		this.name = name;
		this.seen = seen;
	}
}
