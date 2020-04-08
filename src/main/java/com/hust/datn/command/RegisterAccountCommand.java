package com.hust.datn.command;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class RegisterAccountCommand {
	public String firstName;
	public String lastName;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public LocalDate birthday;
	public int gender;
	public String phone;
	public String email;
	public String address;
	public String username;
	public String password;
	public String confirmPassword;
	
	public void validate() throws Exception {
		if (!password.equals(confirmPassword))
			throw new Exception("Mật khẩu nhập lại không trùng khớp");
	}
	
	public RegisterAccountCommand() {
		super();
	}

	public RegisterAccountCommand(String firstName, String lastName, LocalDate birthday, int gender, String phone,
			String email, String address, String username, String password, String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
