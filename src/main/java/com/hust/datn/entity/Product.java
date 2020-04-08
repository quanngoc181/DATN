package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "PRODUCT")
public class Product extends ParentEntity {
	@Nationalized
	private String name;
	
	private int cost;
	
	private byte[] image;
	
	public Product() {
		super();
	}

	public Product(UUID id, String name, int cost, byte[] image) {
		super.setId(id);
		this.name = name;
		this.cost = cost;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
