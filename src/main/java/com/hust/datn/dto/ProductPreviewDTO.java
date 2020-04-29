package com.hust.datn.dto;

public class ProductPreviewDTO {
	public String name;
	public String code;
	public int cost;
	public String image;
	
	public ProductPreviewDTO() {
		super();
	}

	public ProductPreviewDTO(String name, String code, int cost, String image) {
		super();
		this.name = name;
		this.code = code;
		this.cost = cost;
		this.image = image;
	}
}
