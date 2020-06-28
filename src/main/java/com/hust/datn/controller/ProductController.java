package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.command.SearchCommand;
import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ProductOption;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.ItemRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.service.CartService;
import com.hust.datn.service.CategoryService;
import com.hust.datn.service.OptionService;
import com.hust.datn.utilities.StringUtilities;

@Controller
public class ProductController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	StringUtilities stringUtilities;
	
	@GetMapping("/product")
	public String product(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "product";
	}
	
	@GetMapping("/user/product")
	public String userProduct(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "user/product";
	}
	
	@GetMapping("/product/fetch-product")
	@ResponseBody
	public ModelAndView fetchProduct(@ModelAttribute SearchCommand command) {
		List<UUID> uuids = stringUtilities.uuidsFromString(command.categories);
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (UUID uuid : uuids) {
			Optional<Category> optional = categoryRepository.findById(uuid);
			if(optional.isPresent()) {
				Category category = optional.get();
				Category filtered = category.filter(command.keyword, command.discount);
				dtos.add(categoryService.getCategoryDTO(filtered, false));
			}
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("categories", dtos);
		
		return new ModelAndView("/partial/product-list", model);
	}
	
	@GetMapping("/user/product/add-to-cart")
	@ResponseBody
	public ModelAndView getDetail(String id) throws InternalException {
		UUID prdId = UUID.fromString(id);
		
		Optional<Product> optional = productRepository.findById(prdId);
		if(!optional.isPresent())
			throw new InternalException("Không tìm thấy sản phẩm");
		Product product = optional.get();
		
		List<ProductOption> options = optionService.optionsFromString(product.getOptions());
		ProductPreviewDTO productPreviewDTO = ProductPreviewDTO.fromProduct(product, options);
		
		Map<String, Object> model = new HashMap<>();
		model.put("product", productPreviewDTO);
		
		return new ModelAndView("/partial/add-to-cart", model);
	}
	
	@PostMapping("/user/product/add-to-cart")
	@ResponseBody
	public void addToCart(Authentication auth, String productId, int amount, String items) {
		UUID userId = accountRepository.findByUsername(auth.getName()).getId();
		
		Cart cart = cartRepository.findByUserIdAndProductIdAndItems(userId, UUID.fromString(productId), items);
		if(cart == null) {
			cartRepository.save(new Cart(null, userId, UUID.fromString(productId), amount, items));
		} else {
			cart.setAmount(cart.getAmount() + amount);
			cartRepository.save(cart);
		}
	}
	
	@GetMapping("/user/product/calculate-total-cost")
	@ResponseBody
	public int calculateTotalCost(String id, int amount, String items) {
		return cartService.getDetailCost(new Cart(null, null, UUID.fromString(id), amount, items));
	}
}
