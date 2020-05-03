package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Product;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.specification.ProductSpecification;

@Controller
public class ProductManagementController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/admin/product-management")
	public String productManagement(Model model) {
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (Category category : categories) {
			CategoryDTO dto = new CategoryDTO();
			dto.categoryId = category.getId();
			dto.categoryName = category.getName();
			dto.categoryCode = category.getCategoryCode();
			
			List<ProductPreviewDTO> products = new ArrayList<>();
			for (Product product : category.getProducts()) {
				String avatar = product.getImage() == null ? "/images/default-product.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(product.getImage()));
				products.add(new ProductPreviewDTO(product.getId(), product.getName(), product.getProductCode(), product.getCost(), avatar));
			}
			dto.products = products;
			
			dtos.add(dto);
		}
		
		model.addAttribute("categories", dtos);
		
		return "admin/product-management";
	}

	@GetMapping("/admin/product-management/datatable")
	@ResponseBody
	public DatatableDTO<Product> productDatatable(HttpServletRequest request) {
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		String value = request.getParameter("search[value]");
		String dir = request.getParameter("order[0][dir]");
		String order = request.getParameter("order[0][column]");
		String col = request.getParameter("columns[" + order + "][name]");

		Sort sort;
		if (dir.equalsIgnoreCase("asc"))
			sort = Sort.by(Sort.Direction.ASC, col);
		else
			sort = Sort.by(Sort.Direction.DESC, col);

		int countAll = (int) productRepository.count();
		int countFiltered = productRepository.count(ProductSpecification.containsTextInNameOrCode(value));
		List<Product> products = productRepository.findAll(ProductSpecification.containsTextInNameOrCode(value), PageRequest.of(start / length, length, sort));

		return new DatatableDTO<Product>(draw, countAll, countFiltered, products);
	}
	
	@PostMapping("/admin/product-management/change-category")
	@ResponseBody
	public String changeCategory(String productId, String categoryId) {
		UUID ctgId = UUID.fromString(categoryId);
		UUID prdId = UUID.fromString(productId);
		
		Product product = productRepository.findById(prdId).get();
		Category category = categoryRepository.findById(ctgId).get();
		
		product.setCategory(category);
		
		productRepository.save(product);
		
		return "";
	}
}
