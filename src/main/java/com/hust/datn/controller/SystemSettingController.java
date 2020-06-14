package com.hust.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.command.CompanySettingCommand;
import com.hust.datn.dto.SettingDTO;
import com.hust.datn.repository.ConstantRepository;
import com.hust.datn.service.ConstantService;

@Controller
public class SystemSettingController {
	@Autowired
	ConstantRepository constantRepository;
	
	@Autowired
	ConstantService constantService;
	
	@GetMapping("/admin/system-setting")
	public String index(Model model) {
		SettingDTO dto = constantService.getAll();
		model.addAttribute("setting", dto);
		return "admin/system-setting";
	}
	
	@PostMapping("/admin/system-setting/save-cost")
	@ResponseBody
	public void saveCost(String shippingFee) {
		constantService.saveOrUpdate("SHIPPING_FEE", shippingFee);
	}
	
	@PostMapping("/admin/system-setting/save-other")
	@ResponseBody
	public void saveOther(String receiveAddressLimit) {
		constantService.saveOrUpdate("RECEIVE_ADDRESS_LIMIT", receiveAddressLimit);
	}
	
	@PostMapping("/admin/system-setting/save-company")
	@ResponseBody
	public void saveCompany(CompanySettingCommand command) {
		constantService.saveOrUpdate("COMPANY_NAME", command.companyName);
		constantService.saveOrUpdate("COMPANY_ADDRESS", command.companyAddress);
		constantService.saveOrUpdate("COMPANY_PHONE", command.companyPhone);
		constantService.saveOrUpdate("COMPANY_EMAIL", command.companyEmail);
		constantService.saveOrUpdate("COMPANY_BUSINESS_NUMBER", command.companyBusinessNumber);
		constantService.saveOrUpdate("COMPANY_BUSINESS_DATE", command.companyBusinessDate);
		constantService.saveOrUpdate("COMPANY_BUSINESS_ORGANIZATION", command.companyBusinessOrganization);
	}
}
