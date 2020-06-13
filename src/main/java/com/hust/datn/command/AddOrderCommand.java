package com.hust.datn.command;

import java.util.List;

public class AddOrderCommand {
	public String orderName;
	
	public String orderPhone;
	
	public String note;
	
	public List<OrderProductCommand> products;
	
	public AddOrderCommand() {
	}

	public AddOrderCommand(String orderName, String orderPhone, String note, List<OrderProductCommand> products) {
		super();
		this.orderName = orderName;
		this.orderPhone = orderPhone;
		this.note = note;
		this.products = products;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public List<OrderProductCommand> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProductCommand> products) {
		this.products = products;
	}
}
