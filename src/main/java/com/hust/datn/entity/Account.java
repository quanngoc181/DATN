package com.hust.datn.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ACCOUNT")
public class Account extends ParentEntity {
	private String username;

	private int accountNumber;

	@Nationalized
	private String firstName;

	@Nationalized
	private String lastName;

	private LocalDate birthday;

	private int gender;

	private String phone;

	private String email;

	@Nationalized
	private String address;

	private int money;

	private byte[] avatar;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceiveAddress> receiveAddresses = new ArrayList<>();

	public Account() {
		super();
	}

	public Account(UUID id, String username, String firstName, String lastName, int accountNumber, LocalDate birthday,
			int gender, String phone, String email, String address, int money, byte[] avatar) {
		super.setId(id);
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountNumber;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.money = money;
		this.avatar = avatar;
	}

	public void addReceiveAddress(ReceiveAddress address) {
		address.setAccount(this);
		receiveAddresses.add(address);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public List<ReceiveAddress> getReceiveAddresses() {
		return receiveAddresses;
	}

	public void setReceiveAddresses(List<ReceiveAddress> receiveAddresses) {
		this.receiveAddresses = receiveAddresses;
	}
}
