package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "CATEGORY")
public class Category extends ParentEntity {
	@Nationalized
	private String name;
	
	public Category() {
		super();
	}

	public Category(UUID id, String name) {
		super.setId(id);
		this.name = name;
	}
}
