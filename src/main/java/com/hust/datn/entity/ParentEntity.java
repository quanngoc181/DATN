package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class ParentEntity {
	@Id
	@GeneratedValue
	private UUID id;

	private LocalDateTime createAt;

	private LocalDateTime updateAt;
	
	@PrePersist
	protected void onCreate() {
		createAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updateAt = LocalDateTime.now();
	}

	public ParentEntity() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
