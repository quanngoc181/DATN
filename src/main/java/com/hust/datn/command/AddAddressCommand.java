package com.hust.datn.command;

import javax.validation.constraints.NotBlank;

import com.hust.datn.validator.ValidPhone;

public class AddAddressCommand {
	public String id;
	
	@NotBlank(message = "Tên địa chỉ không hợp lệ")
	public String addressName;
	
	@NotBlank(message = "Người nhận không hợp lệ")
	public String name;
	
	@ValidPhone(message = "Số điện thoại không hợp lệ")
	public String phone;
	
	@NotBlank(message = "Địa chỉ không hợp lệ")
	public String address;
	
	public AddAddressCommand() {
		super();
	}

	public AddAddressCommand(String id, String addressName, String name, String phone, String address) {
		super();
		this.id = id;
		this.addressName = addressName;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
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
}
