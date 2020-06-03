package com.hust.datn.command;

import com.hust.datn.exception.InternalException;
import com.hust.datn.validator.ValidUsername;

public class ChangePasswordCommand {
	public String oldpass;
	
	@ValidUsername(message = "Mật khẩu ít nhất 8 kí tự (chữ, số, gạch dưới)")
	public String newpass;
	
	public String confirmpass;
	
	public void validate() throws InternalException {
		if(!newpass.equals(confirmpass))
			throw new InternalException("Mật khẩu nhập lại không khớp");
		
		if (oldpass.equals(newpass))
			throw new InternalException("Mật khẩu mới không được trùng mật khẩu cũ");
	}
	
	public ChangePasswordCommand() {
	}

	public ChangePasswordCommand(String oldpass, String newpass, String confirmpass) {
		super();
		this.oldpass = oldpass;
		this.newpass = newpass;
		this.confirmpass = confirmpass;
	}

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
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
}
