package com.hust.datn.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.hust.datn.validator.ValidDate;
import com.hust.datn.validator.ValidPhone;

public class CompanySettingCommand {
	@NotBlank(message = "Tên công ty không hợp lệ")
	public String companyName;
	
	@NotBlank(message = "Địa chỉ không hợp lệ")
	public String companyAddress;
	
	@ValidPhone(message = "Số điện thoại không hợp lệ")
	public String companyPhone;
	
	@NotBlank(message = "Vui lòng điền email")
	@Email(message = "Email không hợp lệ")
	public String companyEmail;
	
	@NotBlank(message = "Số đăng ký kinh doanh không hợp lệ")
	public String companyBusinessNumber;
	
	@ValidDate(message = "Ngày đăng ký không hợp lệ")
	public String companyBusinessDate;
	
	@NotBlank(message = "Nơi cấp không hợp lệ")
	public String companyBusinessOrganization;
	
	public CompanySettingCommand() {
		super();
	}

	public CompanySettingCommand(String companyName, String companyAddress, String companyPhone, String companyEmail,
			String companyBusinessNumber, String companyBusinessDate, String companyBusinessOrganization) {
		super();
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyPhone = companyPhone;
		this.companyEmail = companyEmail;
		this.companyBusinessNumber = companyBusinessNumber;
		this.companyBusinessDate = companyBusinessDate;
		this.companyBusinessOrganization = companyBusinessOrganization;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyBusinessNumber() {
		return companyBusinessNumber;
	}

	public void setCompanyBusinessNumber(String companyBusinessNumber) {
		this.companyBusinessNumber = companyBusinessNumber;
	}

	public String getCompanyBusinessDate() {
		return companyBusinessDate;
	}

	public void setCompanyBusinessDate(String companyBusinessDate) {
		this.companyBusinessDate = companyBusinessDate;
	}

	public String getCompanyBusinessOrganization() {
		return companyBusinessOrganization;
	}

	public void setCompanyBusinessOrganization(String companyBusinessOrganization) {
		this.companyBusinessOrganization = companyBusinessOrganization;
	}
}
