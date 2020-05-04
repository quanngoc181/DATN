package com.hust.datn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.entity.Product;
import com.hust.datn.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	public ProductService() {
	}
	
	public String generateProductCode() {
		List<Product> products = productRepository.findAll();
		int maxCode = 1;
		
		for (Product product : products) {
			int code = Integer.parseInt(product.getProductCode().substring(3));
			if(code >= maxCode) maxCode = code + 1;
		}
		
		String prefix = maxCode < 10 ? "0" : "";
		
		return "PRD" + prefix + Integer.toString(maxCode);
	}
}
