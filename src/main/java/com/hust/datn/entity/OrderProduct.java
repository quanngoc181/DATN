package com.hust.datn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProduct extends ParentEntity {
	private String code;
	
	@Nationalized
	private String name;
	
	@Nationalized
	private String items;
	
	private int amount;
	
	private int cost;
	
	@Column(columnDefinition = "varbinary(MAX)")
	private byte[] image;
	
	@ManyToOne
	private Order order;
	
	public OrderProduct() {
	}

	public OrderProduct(String code, String name, String items, int amount, int cost, byte[] image) {
		super();
		this.code = code;
		this.name = name;
		this.items = items;
		this.amount = amount;
		this.cost = cost;
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
