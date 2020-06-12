package com.hust.datn.entity;

import java.util.Base64;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.hust.datn.enums.OrderStatus;
import com.hust.datn.enums.OrderType;

@Entity
@Table(name = "ORDERS")
public class Order extends ParentEntity {
	@Column(columnDefinition = "uniqueidentifier")
	private UUID orderAccountId;
	
	private String orderAccount;
	
	@Nationalized
	private String orderName;
	
	private String orderPhone;
	
	@Nationalized
	private String name;
	
	@Nationalized
	private String addressName;
	
	@Nationalized
	private String address;
	
	private String phone;
	
	private int productCost;
	
	private int shippingFee;
	
	private int cost;
	
	private OrderType type;
	
	private OrderStatus status;

	@Column(columnDefinition = "nvarchar(MAX)")
	private String note;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<OrderProduct> orderProducts;
	
	public Order() {
	}

	public Order(UUID orderAccountId, String orderAccount, String orderName, String orderPhone, String name,
			String addressName, String address, String phone, int productCost, int shippingFee, int cost,
			OrderType type, OrderStatus status, String note, Set<OrderProduct> orderProducts) {
		super();
		this.orderAccountId = orderAccountId;
		this.orderAccount = orderAccount;
		this.orderName = orderName;
		this.orderPhone = orderPhone;
		this.name = name;
		this.addressName = addressName;
		this.address = address;
		this.phone = phone;
		this.productCost = productCost;
		this.shippingFee = shippingFee;
		this.cost = cost;
		this.type = type;
		this.status = status;
		this.note = note;
		this.orderProducts = orderProducts;
	}

	public UUID getOrderAccountId() {
		return orderAccountId;
	}

	public void setOrderAccountId(UUID orderAccountId) {
		this.orderAccountId = orderAccountId;
	}

	public String getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(String orderAccount) {
		this.orderAccount = orderAccount;
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

	public Set<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public int getProductCost() {
		return productCost;
	}

	public void setProductCost(int productCost) {
		this.productCost = productCost;
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
