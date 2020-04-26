package com.hust.datn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.entity.Category;
import com.hust.datn.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
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
}
