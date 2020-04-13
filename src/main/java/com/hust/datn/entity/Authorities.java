package com.hust.datn.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITIES")
@IdClass(AuthorityId.class)
public class Authorities {
	@Id
	private String username;
	@Id
	private String authority;
	
	public Authorities() {
		super();
	}

	public Authorities(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
