package com.hust.datn.command;

public class OrderProductCommand {
	public int amount;
	
	public String productId;
	
	public String items;
	
	public OrderProductCommand() {
		super();
	}

	public OrderProductCommand(int amount, String productId, String items) {
		super();
		this.amount = amount;
		this.productId = productId;
		this.items = items;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}
}
