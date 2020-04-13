package com.hust.datn.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSISTENT_LOGINS")
public class PersistentLogins {
	@Id
	private String series;
	private String username;
	private String token;
	private LocalDateTime lastUsed;
	
	public PersistentLogins() {
		super();
	}

	public PersistentLogins(String series, String username, String token, LocalDateTime lastUsed) {
		super();
		this.series = series;
		this.username = username;
		this.token = token;
		this.lastUsed = lastUsed;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}
}
