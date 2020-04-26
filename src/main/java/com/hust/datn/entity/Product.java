package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "PRODUCT")
public class Product extends ParentEntity {
	@Nationalized
	private String name;
	
	private String productCode;
	
	private int cost;
	
	@Column(columnDefinition = "varbinary(MAX)")
	private byte[] image;
	
	@ManyToOne
	private Category category;
	
	public Product() {
		super();
	}

	public Product(UUID id, String name, String code, int cost, byte[] image) {
		super.setId(id);
		this.name = name;
		this.productCode = code;
		this.cost = cost;
		this.image = image;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
