package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "RECEIVE_ADDRESS")
public class ReceiveAddress extends ParentEntity {
	@Nationalized
	private String addressName;
	
	@Nationalized
	private String name;
	
	private String phone;
	
	@Nationalized
	private String address;
	
	private boolean isDefault;
	
	@ManyToOne
	private Account account;
	
	public ReceiveAddress() {
		super();
	}

	public ReceiveAddress(UUID id, String addressName, String name, String phone, String address, boolean def) {
		this.setId(id);
		this.addressName = addressName;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isDefault = def;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
