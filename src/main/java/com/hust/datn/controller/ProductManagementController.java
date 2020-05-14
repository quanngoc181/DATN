package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.command.AddProductCommand;
import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.OptionRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.service.OptionService;
import com.hust.datn.service.ProductService;
import com.hust.datn.specification.ProductSpecification;
import com.hust.datn.utilities.StringUtilities;

@Controller
public class ProductManagementController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	StringUtilities stringUtilities;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OptionRepository optionRepository;
	
	@Autowired
	OptionService optionService;
	
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
				ProductPreviewDTO productPreviewDTO = ProductPreviewDTO.fromProduct(product);
				productPreviewDTO.optionArray = optionService.optionsFromString(product.getOptions());
				products.add(productPreviewDTO);
			}
			dto.products = products;
			
			dtos.add(dto);
		}
		
		model.addAttribute("categories", dtos);
		
		return "admin/product-management";
	}

	@GetMapping("/admin/product-management/datatable")
	@ResponseBody
	public DatatableDTO<ProductPreviewDTO> productDatatable(HttpServletRequest request) {
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
		
		List<ProductPreviewDTO> dtos = new ArrayList<>();
		for (Product product : products) {
			ProductPreviewDTO productPreviewDTO = ProductPreviewDTO.fromProduct(product);
			productPreviewDTO.optionArray = optionService.optionsFromString(product.getOptions());
			dtos.add(productPreviewDTO);
		}

		return new DatatableDTO<ProductPreviewDTO>(draw, countAll, countFiltered, dtos);
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
	
	@GetMapping("/admin/product-management/add")
	@ResponseBody
	public ModelAndView addProduct(String id) {
		UUID ctgId = UUID.fromString(id);
		
		Category category = categoryRepository.findById(ctgId).get();
		List<ProductOption> options = optionRepository.findAll();
		
		Map<String, Object> model = new HashMap<>();
		model.put("category", category);
		model.put("options", options);
		
		return new ModelAndView("partial/add-product", model);
	}
	
	@PostMapping("/admin/product-management/add")
	@ResponseBody
	public String addProduct1(@ModelAttribute AddProductCommand command) throws InternalException {
		Optional<String> optional = stringUtilities.getExtension(command.file.getOriginalFilename());
		if(optional.isPresent()) {
			String extension = optional.get();
			if(!extension.equals("jpg") && !extension.equals("png")) {
				throw new InternalException("Sai định dạng ảnh (png hoặc jpg)");
			}
		}
		
		try {
			byte[] bytes = command.file.getBytes().length == 0 ? null : command.file.getBytes();
			
			String code = productService.generateProductCode();
			
			UUID ctgId = UUID.fromString(command.categoryId);
			
			Category category = categoryRepository.findById(ctgId).get();
			
			category.addProduct(new Product(null, command.name, code, command.cost, bytes, command.options == null ? null : String.join(";", command.options)));
			
			categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/admin/product-management/edit")
	@ResponseBody
	public ModelAndView editProduct(String id) {
		UUID prdId = UUID.fromString(id);
		
		Product product = productRepository.findById(prdId).get();
		List<ProductOption> options = optionRepository.findAll();
		
		Map<String, Object> model = new HashMap<>();
		model.put("dto", ProductPreviewDTO.fromProduct(product));
		model.put("options", options);
		
		return new ModelAndView("partial/edit-product", model);
	}
	
	@PostMapping("/admin/product-management/edit")
	@ResponseBody
	public String editProduct1(@ModelAttribute AddProductCommand command) throws InternalException {
		Optional<String> optional = stringUtilities.getExtension(command.file.getOriginalFilename());
		if(optional.isPresent()) {
			String extension = optional.get();
			if(!extension.equals("jpg") && !extension.equals("png")) {
				throw new InternalException("Sai định dạng ảnh (png hoặc jpg)");
			}
		}
		
		try {
			UUID id = UUID.fromString(command.id);
			byte[] bytes = command.file.getBytes();
			
			Product product = productRepository.findById(id).get();
			
			product.setName(command.name);
			product.setCost(command.cost);
			if(bytes.length != 0)
				product.setImage(bytes);
			product.setOptions(command.options == null ? null : String.join(";", command.options));
			
			productRepository.save(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@PostMapping("/admin/product-management/delete")
	@ResponseBody
	public String deleteProduct(String id) {
		UUID prdId = UUID.fromString(id);
		
		Category category = productRepository.findById(prdId).get().getCategory();
		
		category.deleteProduct(prdId);

		categoryRepository.save(category);
		
		return "";
	}
}
