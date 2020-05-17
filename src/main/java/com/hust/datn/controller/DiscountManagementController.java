package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.command.AddDiscountProductCommand;
import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.DiscountDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.DiscountProduct;
import com.hust.datn.entity.Product;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.DiscountProductRepository;
import com.hust.datn.service.CategoryService;
import com.hust.datn.service.OptionService;
import com.hust.datn.service.ProductService;

@Controller
public class DiscountManagementController {
	@Autowired
	DiscountProductRepository discountProductRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/admin/discount-management")
	public String index() {
		return "admin/discount-management";
	}
	
	@GetMapping("/admin/discount-management/datatable")
	@ResponseBody
	public DatatableDTO<DiscountProduct> discountDatatable(HttpServletRequest request) {
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

		int countAll = (int) discountProductRepository.count();
		int countFiltered = discountProductRepository.countByDescriptionContains(value);
		List<DiscountProduct> discounts = discountProductRepository.findByDescriptionContains(value, PageRequest.of(start / length, length, sort));

		return new DatatableDTO<DiscountProduct>(draw, countAll, countFiltered, discounts);
	}
	
	@GetMapping("/admin/discount-management/add")
	@ResponseBody
	public ModelAndView addDiscount() {
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (Category category : categories) {
			CategoryDTO dto = categoryService.getCategoryDTO(category, false);
			dtos.add(dto);
		}
		
		return new ModelAndView("partial/add-discount", "categories", dtos);
	}
	
	@PostMapping("/admin/discount-management/add")
	@ResponseBody
	public String addDiscount1(@ModelAttribute AddDiscountProductCommand command) {
		DiscountProduct discount = new DiscountProduct(null, command.description, command.amount, command.getUnit(), command.getStartDateTime(), command.getEndDateTime());
		
		discountProductRepository.save(discount);
		
		DiscountProduct savedDiscount = discountProductRepository.findById(discount.getId()).get();
		
		Set<Product> products = productService.productsFromString(command.products);
		savedDiscount.setProducts(products);
		
		discountProductRepository.save(savedDiscount);
		
		return "";
	}
	
	@PostMapping("/admin/discount-management/delete")
	@ResponseBody
	public void deleteDiscount(String id) {
		Optional<DiscountProduct> optional = discountProductRepository.findById(UUID.fromString(id));
		if(optional.isPresent()) {
			discountProductRepository.delete(optional.get());
		}
	}
	
	@GetMapping("/admin/discount-management/edit")
	@ResponseBody
	public ModelAndView editDiscount(String id) {
		Map<String, Object> model = new HashMap<>();
		
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (Category category : categories) {
			CategoryDTO dto = categoryService.getCategoryDTO(category, false);
			
			dtos.add(dto);
		}
		
		Optional<DiscountProduct> optional = discountProductRepository.findById(UUID.fromString(id));
		if(optional.isPresent()) {
			DiscountProduct discount = optional.get();
			model.put("discount", new DiscountDTO(discount));
		}
		
		model.put("categories", dtos);
		
		return new ModelAndView("partial/edit-discount", model);
	}
	
	@PostMapping("/admin/discount-management/edit")
	@ResponseBody
	public String editDiscount1(@ModelAttribute AddDiscountProductCommand command) {
		DiscountProduct discount = discountProductRepository.findById(UUID.fromString(command.id)).get();
		
		discount.setDescription(command.description);
		discount.setAmount(command.amount);
		discount.setUnit(command.getUnit());
		discount.setStartDate(command.getStartDateTime());
		discount.setEndDate(command.getEndDateTime());
		
		Set<Product> products = productService.productsFromString(command.products);
		discount.setProducts(products);
		
		discountProductRepository.save(discount);
		
		return "";
	}
}
