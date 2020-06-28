package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.hust.datn.entity.DiscountProduct;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.DiscountProductRepository;
import com.hust.datn.repository.OptionRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.service.CategoryService;
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
	
	@Autowired
	DiscountProductRepository discountProductRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/admin/product-management")
	public String productManagement(Model model) {
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (Category category : categories) {
			CategoryDTO dto = categoryService.getCategoryDTO(category, true);
			
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
	public void changeCategory(String productId, String categoryId) {
		UUID ctgId = UUID.fromString(categoryId);
		UUID prdId = UUID.fromString(productId);
		
		Optional<Product> optionalP = productRepository.findById(prdId);
		Optional<Category> optionalC = categoryRepository.findById(ctgId);
		
		if(optionalP.isPresent() && optionalC.isPresent()) {
			Product product = optionalP.get();
			Category category = optionalC.get();
			product.setCategory(category);
			productRepository.save(product);
		}
	}
	
	@GetMapping("/admin/product-management/add")
	@ResponseBody
	public ModelAndView addProduct(String id) throws InternalException {
		UUID ctgId = UUID.fromString(id);
		
		Optional<Category> optional = categoryRepository.findById(ctgId);
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy danh mục");
		
		Category category = optional.get();
		List<ProductOption> options = optionRepository.findAll();
		
		Map<String, Object> model = new HashMap<>();
		model.put("category", category);
		model.put("options", options);
		
		return new ModelAndView("partial/add-product", model);
	}
	
	@PostMapping("/admin/product-management/add")
	@ResponseBody
	public void addProduct1(@Valid AddProductCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		Optional<String> optionalEx = stringUtilities.getExtension(command.file.getOriginalFilename());
		if(optionalEx.isPresent()) {
			String extension = optionalEx.get();
			if(!extension.equals("jpg") && !extension.equals("png")) {
				throw new InternalException("Sai định dạng ảnh (png hoặc jpg)");
			}
		}
		
		try {
			byte[] bytes = command.file.getBytes().length == 0 ? null : command.file.getBytes();
			
			String code = productService.generateProductCode();
			
			UUID ctgId = UUID.fromString(command.categoryId);
			
			Optional<Category> optional = categoryRepository.findById(ctgId);
			if(optional.isPresent()) {
				Category category = optional.get();
				category.addProduct(new Product(null, command.name, code, command.getCost(), bytes, command.options == null ? null : String.join(";", command.options)));
				categoryRepository.save(category);
			}
		} catch (Exception e) {
			throw new InternalException("Đã có lỗi, không thể thêm sản phẩm");
		}
	}
	
	@GetMapping("/admin/product-management/edit")
	@ResponseBody
	public ModelAndView editProduct(String id) throws InternalException {
		UUID prdId = UUID.fromString(id);
		
		Optional<Product> optional = productRepository.findById(prdId);
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy sản phẩm");
		
		Product product = optional.get();
		List<ProductOption> options = optionRepository.findAll();
		
		Map<String, Object> model = new HashMap<>();
		model.put("dto", ProductPreviewDTO.fromProduct(product));
		model.put("options", options);
		
		return new ModelAndView("partial/edit-product", model);
	}
	
	@PostMapping("/admin/product-management/edit")
	@ResponseBody
	public void editProduct1(@Valid AddProductCommand command, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		Optional<String> optionalEx = stringUtilities.getExtension(command.file.getOriginalFilename());
		if(optionalEx.isPresent()) {
			String extension = optionalEx.get();
			if(!extension.equals("jpg") && !extension.equals("png")) {
				throw new InternalException("Sai định dạng ảnh (png hoặc jpg)");
			}
		}
		
		try {
			UUID id = UUID.fromString(command.id);
			byte[] bytes = command.file.getBytes().length == 0 ? null : command.file.getBytes();
			
			Optional<Product> optional = productRepository.findById(id);
			if(!optional.isPresent())
				throw new InternalException("Không tìm thấy sản phẩm");
			
			Product product = optional.get();
			
			product.setName(command.name);
			product.setCost(command.getCost());
			product.setImage(bytes);
			product.setOptions(command.options == null ? null : String.join(";", command.options));
			
			productRepository.save(product);
		} catch (Exception e) {
			throw new InternalException("Đã có lỗi, không thể thêm sản phẩm");
		}
	}
	
	@PostMapping("/admin/product-management/delete")
	@ResponseBody
	public void deleteProduct(String id) throws InternalException {
		UUID prdId = UUID.fromString(id);
		
		Optional<Product> optional = productRepository.findById(prdId);
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy sản phẩm");
		
		Product product = optional.get();
		
		for (DiscountProduct discount : product.getDiscounts()) {
			discount.deleteProduct(prdId);
			discountProductRepository.save(discount);
		}
		
		Category category = product.getCategory();
		category.deleteProduct(prdId);
		categoryRepository.save(category);
	}
}
