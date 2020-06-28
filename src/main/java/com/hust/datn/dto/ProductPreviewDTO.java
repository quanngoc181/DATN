package com.hust.datn.dto;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.hust.datn.entity.DiscountProduct;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;

public class ProductPreviewDTO {
	public UUID id;
	public String name;
	public String productCode;
	public int cost;
	public int discountCost;
	public String image;
	public String categoryName;
	public String options;
	public List<ProductOption> optionArray;
	public Set<DiscountProduct> discounts;

	public ProductPreviewDTO(UUID id, String name, String code, int cost, int discountCost, String image, String categoryName, String options, List<ProductOption> optionArray, Set<DiscountProduct> discounts) {
		super();
		this.id = id;
		this.name = name;
		this.productCode = code;
		this.cost = cost;
		this.discountCost = discountCost;
		this.image = image;
		this.categoryName = categoryName;
		this.options = options;
		this.optionArray = optionArray;
		this.discounts = discounts;
	}
	
	public static ProductPreviewDTO fromProduct(Product product) {
		return new ProductPreviewDTO(product.getId(), product.getName(), product.getProductCode(), product.getCost(), product.getDiscountCost(), product.getImageString(),
				product.getCategory().getName(), product.getOptions() == null ? "" : product.getOptions(), null, product.getDiscounts());
	}
	
	public static ProductPreviewDTO fromProduct(Product product, List<ProductOption> optionArray) {
		return new ProductPreviewDTO(product.getId(), product.getName(), product.getProductCode(), product.getCost(), product.getDiscountCost(), product.getImageString(),
				product.getCategory().getName(), product.getOptions() == null ? "" : product.getOptions(), optionArray, product.getDiscounts());
	}
}
