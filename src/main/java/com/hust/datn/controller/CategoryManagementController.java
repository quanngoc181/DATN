package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Product;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.service.CategoryService;
import com.hust.datn.service.OptionService;
import com.hust.datn.specification.CategorySpecification;

@Controller
public class CategoryManagementController {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OptionService optionService;

	@GetMapping("/admin/category-management")
	public String categoryManagement() {
		return "admin/category-management";
	}

	@GetMapping("/admin/category-management/datatable")
	@ResponseBody
	public DatatableDTO<Category> categoryDatatable(HttpServletRequest request) {
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

		int countAll = (int) categoryRepository.count();
		int countFiltered = categoryRepository.count(CategorySpecification.containsTextInNameOrCode(value));
		List<Category> categories = categoryRepository.findAll(CategorySpecification.containsTextInNameOrCode(value),
				PageRequest.of(start / length, length, sort));

		return new DatatableDTO<Category>(draw, countAll, countFiltered, categories);
	}

	@PostMapping("/admin/category-management/add")
	public String addCategory(String name) {
		String code = categoryService.generateCategoryCode();

		categoryRepository.save(new Category(null, name, code, new HashSet<>()));

		return "redirect:/admin/category-management";
	}

	@PostMapping("/admin/category-management/edit")
	public String editCategory(String id, String name) {
		UUID ctgId = UUID.fromString(id);

		Category category = categoryRepository.findById(ctgId).get();
		
		category.setName(name);

		categoryRepository.save(category);

		return "redirect:/admin/category-management";
	}

	@PostMapping("/admin/category-management/delete")
	@ResponseBody
	public void deleteCategory(String id) {
		UUID ctgId = UUID.fromString(id);

		Category category = categoryRepository.findById(ctgId).get();

		categoryRepository.delete(category);
	}
	
	@GetMapping("/admin/category-management/view-detail")
	@ResponseBody
	public ModelAndView viewDetail(String id) {
		UUID ctgId = UUID.fromString(id);

		Category category = categoryRepository.findById(ctgId).get();
		
		List<ProductPreviewDTO> dtos = new ArrayList<>();
		
		for (Product product : category.getProducts()) {
			ProductPreviewDTO productPreviewDTO = ProductPreviewDTO.fromProduct(product);
			productPreviewDTO.optionArray = optionService.optionsFromString(product.getOptions());
			dtos.add(productPreviewDTO);
		}
		
		return new ModelAndView("partial/view-category-detail", "products", dtos);
	}
}
