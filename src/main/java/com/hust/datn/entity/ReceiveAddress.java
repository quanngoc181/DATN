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
	private String name;
	
	private String phone;
	
	@Nationalized
	private String address;
	
	@ManyToOne
	private Account account;
	
	public ReceiveAddress() {
		super();
	}

	public ReceiveAddress(UUID id, String name, String phone, String address) {
		super.setId(id);
		this.name = name;
		this.phone = phone;
		this.address = address;
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
