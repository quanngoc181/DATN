package com.hust.datn.dto;

import java.util.List;
import java.util.UUID;

public class CategoryDTO {
	public UUID categoryId;
	public String categoryName;
	public String categoryCode;
	public List<ProductPreviewDTO> products;
	
	public CategoryDTO() {
	}

	public CategoryDTO(UUID categoryId, String categoryName, String categoryCode, List<ProductPreviewDTO> products) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryCode = categoryCode;
		this.products = products;
	}
}
