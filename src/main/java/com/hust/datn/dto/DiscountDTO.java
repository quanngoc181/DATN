package com.hust.datn.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.hust.datn.entity.DiscountProduct;
import com.hust.datn.entity.Product;

public class DiscountDTO {
	public UUID id;
	public int amount;
	public int unit;
	public String description;
	public LocalDateTime startDate;
	public LocalDateTime endDate;
	public String start;
	public String startTime;
	public String end;
	public String endTime;
	public String productIds;
	
	public DiscountDTO() { }

	public DiscountDTO(UUID id, int amount, int unit, String description, LocalDateTime startDate,
			LocalDateTime endDate, String start, String startTime, String end, String endTime, String ids) {
		super();
		this.id = id;
		this.amount = amount;
		this.unit = unit;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.start = start;
		this.startTime = startTime;
		this.end = end;
		this.endTime = endTime;
		this.productIds = ids;
	}
	
	public DiscountDTO(DiscountProduct discount) {
		this.id = discount.getId();
		this.description = discount.getDescription();
		this.amount = discount.getAmount();
		this.unit = discount.getUnit().ordinal();
		this.startDate = discount.getStartDate();
		this.endDate = discount.getEndDate();
		this.start = this.startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.end = this.endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.startTime = this.startDate.format(DateTimeFormatter.ofPattern("HH:mm"));
		this.endTime = this.endDate.format(DateTimeFormatter.ofPattern("HH:mm"));
		this.productIds = productIds(discount.getProducts());
	}
	
	private String productIds(List<Product> products) {
		if(products == null || products.isEmpty())
			return "";
		
		String ids = products.stream().map(element -> element.getId().toString()).collect(Collectors.joining(";"));
		return ids;
	}
}
