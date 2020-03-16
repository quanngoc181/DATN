package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account extends ParentEntity {
	private String username;
	private String password;

	public Account() {
		super();
	}

	public Account(UUID id, LocalDateTime createAt, LocalDateTime updateAt, String username, String password) {
		super(id, createAt, updateAt);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
