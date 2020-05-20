package com.hust.datn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.entity.OptionItem;
import com.hust.datn.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	ItemRepository itemRepository;
	
	public ItemService() { }
	
	public List<OptionItem> itemsFromString(String string) {
		List<OptionItem> itemArray = new ArrayList<>();
		String[] options = string.isEmpty() ? new String[0] : string.split(";");
		
		for (String id : options) {
			Optional<OptionItem> optional = itemRepository.findById(UUID.fromString(id));
			if(optional.isPresent()) {
				itemArray.add(optional.get());
			}
		}
		
		return itemArray;
	}
}
