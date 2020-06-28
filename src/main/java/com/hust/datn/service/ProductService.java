package com.hust.datn.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	
	public Set<Product> productsFromString(String uuids) {
		Set<Product> products = new HashSet<Product>();
		
		if(uuids == null || uuids.isEmpty())
			return products;
		
		for (String uuid : uuids.split(";")) {
			Optional<Product> optional = productRepository.findById(UUID.fromString(uuid));
			if(optional.isPresent()) {
				products.add(optional.get());
			}
		}
		
		return products;
	}
}
