package com.hust.datn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	
	public List<Product> productsFromString(String uuids) {
		List<Product> products = new ArrayList<>();
		
		if(uuids.isEmpty())
			return null;
		
		for (String uuid : uuids.split(";")) {
			Optional<Product> optional = productRepository.findById(UUID.fromString(uuid));
			if(optional.isPresent()) {
				products.add(optional.get());
			}
		}
		
		return products;
	}
}
