package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption extends ParentEntity {
	@Nationalized
	private String name;
	
	public ProductOption() {
		super();
	}

	public ProductOption(UUID id, String name) {
		super.setId(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
