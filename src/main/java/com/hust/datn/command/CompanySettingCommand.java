package com.hust.datn.command;

public class CompanySettingCommand {
	public String companyName;
	
	public String companyAddress;
	
	public String companyPhone;
	
	public String companyEmail;
	
	public String companyBusinessNumber;
	
	public String companyBusinessDate;
	
	public String companyBusinessOrganization;
	
	public CompanySettingCommand() {
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
