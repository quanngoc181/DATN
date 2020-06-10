package com.hust.datn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.SettingDTO;
import com.hust.datn.entity.Constant;
import com.hust.datn.repository.ConstantRepository;

@Service
public class ConstantService {
	@Autowired
	ConstantRepository constantRepository;
	
	public ConstantService() {
	}
	
	public void saveOrUpdate(String key, String value) {
		Optional<Constant> constant = constantRepository.findById(key);
		if(constant.isPresent()) {
			Constant cs = constant.get();
			cs.setValue(value);
			constantRepository.save(cs);
		} else {
			constantRepository.save(new Constant(key, value));
		}
	}
	
	public SettingDTO getAll() {
		SettingDTO dto = new SettingDTO();
		
		Optional<Constant> shippingFee = constantRepository.findById("SHIPPING_FEE");
		Optional<Constant> receiveAddressLimit = constantRepository.findById("RECEIVE_ADDRESS_LIMIT");
		Optional<Constant> companyName = constantRepository.findById("COMPANY_NAME");
		Optional<Constant> companyAddress = constantRepository.findById("COMPANY_ADDRESS");
		Optional<Constant> companyPhone = constantRepository.findById("COMPANY_PHONE");
		Optional<Constant> companyEmail = constantRepository.findById("COMPANY_EMAIL");
		Optional<Constant> companyBusinessNumber = constantRepository.findById("COMPANY_BUSINESS_NUMBER");
		Optional<Constant> companyBusinessDate = constantRepository.findById("COMPANY_BUSINESS_DATE");
		Optional<Constant> companyBusinessOrganization = constantRepository.findById("COMPANY_BUSINESS_ORGANIZATION");
		
		dto.shippingFee = shippingFee.isPresent() ? Integer.parseInt(shippingFee.get().getValue()) : 0;
		dto.receiveAddressLimit = receiveAddressLimit.isPresent() ? Integer.parseInt(receiveAddressLimit.get().getValue()) : 4;
		dto.companyName = companyName.isPresent() ? companyName.get().getValue() : "Company Name";
		dto.companyAddress = companyAddress.isPresent() ? companyAddress.get().getValue() : "Company Address";
		dto.companyPhone = companyPhone.isPresent() ? companyPhone.get().getValue() : "Company Phone";
		dto.companyEmail = companyEmail.isPresent() ? companyEmail.get().getValue() : "Company Email";
		dto.companyBusinessNumber = companyBusinessNumber.isPresent() ? companyBusinessNumber.get().getValue() : "Company Business Number";
		dto.companyBusinessDate = companyBusinessDate.isPresent() ? companyBusinessDate.get().getValue() : "Company Business Date";
		dto.companyBusinessOrganization = companyBusinessOrganization.isPresent() ? companyBusinessOrganization.get().getValue() : "Company Business Organization";
		
		return dto;
	}
}
