package com.hust.datn.dto;

import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.Nationalized;

import com.hust.datn.enums.OrderStatus;

public class OrderDTO {
	public String name;
	
	public String addressName;
	
	public String address;
	
	public String phone;
	
	public int cost;
	
	public List<OrderProductDTO> products;
	
	public OrderDTO() {
	}

	public OrderDTO(String name, String addressName, String address, String phone, int cost) {
		super();
		this.name = name;
		this.addressName = addressName;
		this.address = address;
		this.phone = phone;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
