package com.hust.datn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;
import com.hust.datn.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OptionService optionService;
	
	public CategoryService() {
	}
	
	public String generateCategoryCode() {
		List<Category> categories = categoryRepository.findAll();
		int maxCode = 1;
		
		for (Category category : categories) {
			int code = Integer.parseInt(category.getCategoryCode().substring(3));
			if(code >= maxCode) maxCode = code + 1;
		}
		
		String prefix = maxCode < 10 ? "0" : "";
		
		return "CTG" + prefix + Integer.toString(maxCode);
	}
	
	public CategoryDTO getCategoryDTO(Category category, boolean includeOption) {
		CategoryDTO dto = new CategoryDTO();
		dto.categoryId = category.getId();
		dto.categoryName = category.getName();
		dto.categoryCode = category.getCategoryCode();
		
		List<ProductPreviewDTO> products = new ArrayList<>();
		for (Product product : category.getProducts()) {
			List<ProductOption> options = includeOption ? optionService.optionsFromString(product.getOptions()) : null;
			ProductPreviewDTO productPreviewDTO = ProductPreviewDTO.fromProduct(product, options);
			products.add(productPreviewDTO);
		}
		dto.products = products;
		
		return dto;
	}
}
