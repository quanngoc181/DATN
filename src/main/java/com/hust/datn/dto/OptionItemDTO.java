package com.hust.datn.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hust.datn.entity.OptionItem;

public class OptionItemDTO {
	public UUID id;
	public String name;
	public int cost;
	
	public UUID optionId;
	public String optionName;
	
	public OptionItemDTO() { }

	public OptionItemDTO(UUID id, String name, int cost, UUID optionId, String optionName) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.optionId = optionId;
		this.optionName = optionName;
	}
	
	public static OptionItemDTO fromItem(OptionItem item) {
		return new OptionItemDTO(item.getId(), item.getName(), item.getCost(), item.getOption().getId(), item.getOption().getName());
	}
	
	public static List<OptionItemDTO> fromItems(List<OptionItem> items) {
		List<OptionItemDTO> dtos = new ArrayList<>();
		for (OptionItem item : items) {
			dtos.add(new OptionItemDTO(item.getId(), item.getName(), item.getCost(), item.getOption().getId(), item.getOption().getName()));
		}
		return dtos;
	}
}
