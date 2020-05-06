package com.hust.datn.dto;

import java.util.UUID;

public class ProductPreviewDTO {
	public UUID id;
	public String name;
	public String code;
	public int cost;
	public String image;
	public String categoryName;
	
	public ProductPreviewDTO() {
		super();
	}

	public ProductPreviewDTO(UUID id, String name, String code, int cost, String image, String categoryName) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.cost = cost;
		this.image = image;
		this.categoryName = categoryName;
	}
}
