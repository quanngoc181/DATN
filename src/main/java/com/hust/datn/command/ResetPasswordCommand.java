package com.hust.datn.command;

import com.hust.datn.exception.InternalException;
import com.hust.datn.validator.ValidUsername;

public class ResetPasswordCommand {
	@ValidUsername(message = "Mật khẩu ít nhất 8 kí tự (chữ, số, gạch dưới)")
	public String newpass;
	
	public String confirmpass;
	
	public String token;
	
	public ResetPasswordCommand() {
	}

	public ResetPasswordCommand(String newpass, String confirmpass, String token) {
		super();
		this.newpass = newpass;
		this.confirmpass = confirmpass;
		this.token = token;
	}
	
	public void validate() throws InternalException {
		if(!newpass.equals(confirmpass))
			throw new InternalException("Mật khẩu không trùng khớp");
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String getConfirmpass() {
		return confirmpass;
	}

	public void setConfirmpass(String confirmpass) {
		this.confirmpass = confirmpass;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
