package com.hust.datn.dto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;

public class ProductPreviewDTO {
	public UUID id;
	public String name;
	public String productCode;
	public int cost;
	public String image;
	public String categoryName;
	public String options;
	public List<ProductOption> optionArray;

	public ProductPreviewDTO(UUID id, String name, String code, int cost, String image, String categoryName, String options, List<ProductOption> optionArray) {
		super();
		this.id = id;
		this.name = name;
		this.productCode = code;
		this.cost = cost;
		this.image = image;
		this.categoryName = categoryName;
		this.options = options;
		this.optionArray = optionArray;
	}
	
	public static ProductPreviewDTO fromProduct(Product product) {
		String avatar = product.getImage() == null ? "/images/default-product.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(product.getImage()));
		
		return new ProductPreviewDTO(product.getId(), product.getName(), product.getProductCode(), product.getCost(), avatar,
				product.getCategory().getName(), product.getOptions() == null ? "" : product.getOptions(), new ArrayList<>());
	}
}
