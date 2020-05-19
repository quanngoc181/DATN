package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CART")
public class Cart extends ParentEntity {
	@Column(columnDefinition = "uniqueidentifier")
	private UUID userId;
	@Column(columnDefinition = "uniqueidentifier")
	private UUID productId;
	private int amount;
	@Column(columnDefinition = "varchar(MAX)")
	private String items;
	
	public Cart() { }

	public Cart(UUID id, UUID userId, UUID productId, int amount, String items) {
		super();
		this.setId(id);
		this.userId = userId;
		this.productId = productId;
		this.amount = amount;
		this.items = items;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}
}
