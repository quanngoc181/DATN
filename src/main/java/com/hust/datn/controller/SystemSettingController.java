package com.hust.datn.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.command.CompanySettingCommand;
import com.hust.datn.dto.SettingDTO;
import com.hust.datn.exception.InternalException;
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
	public void saveCost(String shippingFee) throws InternalException {
		int c;
		try {
			c = Integer.parseInt(shippingFee);
			if(c < 0) throw new Exception();
		} catch (Exception e) {
			throw new InternalException("Phí vận chuyển không hợp lệ");
		}
		constantService.saveOrUpdate("SHIPPING_FEE", "" + c);
	}
	
	@PostMapping("/admin/system-setting/save-other")
	@ResponseBody
	public void saveOther(String receiveAddressLimit) throws InternalException {
		int c;
		try {
			c = Integer.parseInt(receiveAddressLimit);
			if(c < 0) throw new Exception();
		} catch (Exception e) {
			throw new InternalException("Số lượng địa chỉ không hợp lệ");
		}
		constantService.saveOrUpdate("RECEIVE_ADDRESS_LIMIT", "" + c);
	}
	
	@PostMapping("/admin/system-setting/save-company")
	@ResponseBody
	public void saveCompany(@Valid CompanySettingCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		constantService.saveOrUpdate("COMPANY_NAME", command.companyName);
		constantService.saveOrUpdate("COMPANY_ADDRESS", command.companyAddress);
		constantService.saveOrUpdate("COMPANY_PHONE", command.companyPhone);
		constantService.saveOrUpdate("COMPANY_EMAIL", command.companyEmail);
		constantService.saveOrUpdate("COMPANY_BUSINESS_NUMBER", command.companyBusinessNumber);
		constantService.saveOrUpdate("COMPANY_BUSINESS_DATE", command.companyBusinessDate);
		constantService.saveOrUpdate("COMPANY_BUSINESS_ORGANIZATION", command.companyBusinessOrganization);
	}
}
