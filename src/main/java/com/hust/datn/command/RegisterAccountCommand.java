package com.hust.datn.command;

public class RegisterAccountCommand {
	public String firstName;
	public String lastName;
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
	
	public RegisterAccountCommand(String firstName, String lastName, String username, String password, String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
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
