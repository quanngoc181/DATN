package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ParentEntity {
	@Id
	@GeneratedValue
	private UUID id;

	private LocalDateTime createAt;

	private LocalDateTime updateAt;

	public ParentEntity() {
		super();
	}

	public ParentEntity(UUID id, LocalDateTime createAt, LocalDateTime updateAt) {
		super();
		this.id = id;
		this.createAt = createAt;
		this.updateAt = updateAt;
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
