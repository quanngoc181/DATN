package com.hust.datn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.entity.ProductOption;
import com.hust.datn.repository.OptionRepository;

@Service
public class OptionService {
	@Autowired
	OptionRepository optionRepository;
	
	public OptionService() { }
	
	public List<ProductOption> optionsFromString(String string) {
		List<ProductOption> optionArray = new ArrayList<>();
		String[] options = (string == null || string.isEmpty()) ? new String[0] : string.split(";");
		
		for (String id : options) {
			Optional<ProductOption> optional = optionRepository.findById(UUID.fromString(id));
			if(optional.isPresent()) {
				optionArray.add(optional.get());
			}
		}
		
		return optionArray;
	}
}
