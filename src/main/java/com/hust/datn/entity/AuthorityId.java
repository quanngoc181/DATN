package com.hust.datn.entity;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String authority;
	
	public AuthorityId() {
		super();
	}

	public AuthorityId(String username, String authority) {
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
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		AuthorityId authorityId = (AuthorityId) o;
		return Objects.equals(username, authorityId.username) && Objects.equals(authority, authorityId.authority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, authority);
	}
}
