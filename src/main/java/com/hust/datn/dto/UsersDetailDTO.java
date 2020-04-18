package com.hust.datn.dto;

public class UsersDetailDTO {
	public String username;
	public boolean enabled;
	public boolean isAdmin;
	public boolean isUser;
	
	public UsersDetailDTO() {
		
	}

	public UsersDetailDTO(String username, String password, boolean enabled, boolean isAdmin, boolean isUser) {
		super();
		this.username = username;
		this.enabled = enabled;
		this.isAdmin = isAdmin;
		this.isUser = isUser;
	}
}
