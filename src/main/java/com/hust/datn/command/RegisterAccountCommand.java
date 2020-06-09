package com.hust.datn.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hust.datn.exception.InternalException;
import com.hust.datn.validator.ValidDate;
import com.hust.datn.validator.ValidPhone;
import com.hust.datn.validator.ValidUsername;

public class RegisterAccountCommand {
	@NotBlank(message = "Họ đệm không hợp lệ")
	public String firstName;
	
	@NotBlank(message = "Tên không hợp lệ")
	public String lastName;
	
	@ValidDate(message = "Ngày sinh không hợp lệ")
	public String birthday;
	
	public int gender;
	
	@ValidPhone(message = "Số điện thoại không hợp lệ")
	public String phone;
	
	@NotBlank(message = "Vui lòng điền email")
	@Email(message = "Email không hợp lệ")
	public String email;
	
	@NotBlank(message = "Địa chỉ không hợp lệ")
	public String address;
	
	@ValidUsername(message = "Tài khoản ít nhất 8 kí tự (chữ, số, gạch dưới)")
	public String username;
	
	@ValidUsername(message = "Mật khẩu ít nhất 8 kí tự (chữ, số, gạch dưới)")
	public String password;
	
	public String confirmPassword;
	
	public void validate() throws InternalException {
		if (!password.equals(confirmPassword))
			throw new InternalException("Mật khẩu nhập lại không trùng khớp");
	}
	
	public RegisterAccountCommand() {
		super();
	}

	public RegisterAccountCommand(String firstName, String lastName, String birthday, int gender, String phone,
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
