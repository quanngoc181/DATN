package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESET_PASSWORD_TOKEN")
public class ResetPasswordToken extends ParentEntity {
	@Column(columnDefinition = "uniqueidentifier")
	private UUID userId;
	
	@Column(columnDefinition = "uniqueidentifier")
	private UUID token;
	
	private LocalDateTime expiredTime;
	
	public ResetPasswordToken() {
	}

	public ResetPasswordToken(UUID userId, UUID token, LocalDateTime expiredTime) {
		super();
		this.userId = userId;
		this.token = token;
		this.expiredTime = expiredTime;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(LocalDateTime expiredTime) {
		this.expiredTime = expiredTime;
	}
}
