package com.hust.datn.command;

import javax.validation.constraints.NotBlank;

import com.hust.datn.validator.ValidDate;

public class BasicInfoCommand {
	@NotBlank(message = "Họ đệm không hợp lệ")
	public String firstName;
	
	@NotBlank(message = "Tên không hợp lệ")
	public String lastName;
	
	@ValidDate(message = "Ngày sinh không hợp lệ")
	public String birthday;
	
	public int gender;
	
	public BasicInfoCommand() {
		super();
	}

	public BasicInfoCommand(String firstName, String lastName, String birthday, int gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
}
