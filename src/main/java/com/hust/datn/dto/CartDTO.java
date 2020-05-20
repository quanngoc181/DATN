package com.hust.datn.dto;

import java.util.List;
import java.util.UUID;

import com.hust.datn.entity.OptionItem;
import com.hust.datn.entity.Product;

public class CartDTO {
	public UUID id;
	public UUID userId;
	public ProductPreviewDTO product;
	public List<OptionItem> items;
	public int amount;
	public int totalAmount;
	
	public CartDTO() { }

	public CartDTO(UUID id, UUID userId, ProductPreviewDTO product, List<OptionItem> items, int amount,
			int totalAmount) {
		super();
		this.id = id;
		this.userId = userId;
		this.product = product;
		this.items = items;
		this.amount = amount;
		this.totalAmount = totalAmount;
	}
	
	public String getItemString() {
		String itemString = "";
		
		for (OptionItem item : items) {
			itemString += item.getOption().getName() + " " + item.getName() + ", ";
		}
		if(!itemString.isEmpty())
			itemString = itemString.substring(0, itemString.length() - 2);
		
		return itemString;
	}
}
