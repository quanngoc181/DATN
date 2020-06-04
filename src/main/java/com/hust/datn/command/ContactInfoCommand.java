package com.hust.datn.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hust.datn.validator.ValidDate;
import com.hust.datn.validator.ValidPhone;

public class ContactInfoCommand {
	@ValidPhone(message = "Số điện thoại không hợp lệ")
	public String phone;
	
	@NotBlank(message = "Vui lòng điền email")
	@Email(message = "Email không hợp lệ")
	public String email;
	
	@NotBlank(message = "Địa chỉ không hợp lệ")
	public String address;
	
	public ContactInfoCommand() {
		super();
	}

	public ContactInfoCommand(String phone, String email, String address) {
		super();
		this.phone = phone;
		this.email = email;
		this.address = address;
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
}
