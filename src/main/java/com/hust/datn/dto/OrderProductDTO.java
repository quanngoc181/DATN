package com.hust.datn.dto;

public class OrderProductDTO {
	public String code;
	
	public String name;
	
	public String items;
	
	public int amount;
	
	public int cost;
	
	public String image;
	
	public OrderProductDTO() {
	}

	public OrderProductDTO(String code, String name, String items, int amount, int cost, String image) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
