package com.hust.datn.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.hust.datn.enums.OrderStatus;

@Entity
@Table(name = "ORDERS")
public class Order extends ParentEntity {
	@Nationalized
	private String name;
	
	@Nationalized
	private String addressName;
	
	@Nationalized
	private String address;
	
	private String phone;
	
	private int cost;
	
	private OrderStatus status;

	@Column(columnDefinition = "nvarchar(MAX)")
	private String note;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<OrderProduct> orderProducts;
	
	public Order() {
	}

	public Order(String name, String addressName, String address, String phone, int cost, OrderStatus status,
			String note) {
		super();
		this.name = name;
		this.addressName = addressName;
		this.address = address;
		this.phone = phone;
		this.cost = cost;
		this.status = status;
		this.note = note;
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
