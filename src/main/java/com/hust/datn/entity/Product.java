package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PRODUCT")
public class Product extends ParentEntity {
	@Nationalized
	private String name;
	
	private String productCode;
	
	private int cost;
	
	@Column(columnDefinition = "varbinary(MAX)")
	private byte[] image;
	
	private String options;
	
	@ManyToOne
	@JsonIgnore
	private Category category;
	
	public Product() {
		super();
	}

	public Product(UUID id, String name, String code, int cost, byte[] image, String options, Category category) {
		super.setId(id);
		this.name = name;
		this.productCode = code;
		this.cost = cost;
		this.image = image;
		this.options = options;
		this.category = category;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
