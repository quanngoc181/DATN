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
	
	public int productCost;
	
	public List<OrderProductDTO> products;
	
	public int shippingFee;
	
	public OrderDTO() {
	}

	public OrderDTO(String name, String addressName, String address, String phone, int cost, int shippingFee) {
		super();
		this.name = name;
		this.addressName = addressName;
		this.address = address;
		this.phone = phone;
		this.productCost = cost;
		this.shippingFee = shippingFee;
	}

	public int getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(int shippingFee) {
		this.shippingFee = shippingFee;
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
		return productCost;
	}

	public void setCost(int cost) {
		this.productCost = cost;
	}
}
