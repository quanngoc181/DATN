package com.hust.datn.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.CartDTO;
import com.hust.datn.dto.ProductPreviewDTO;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.OptionItem;
import com.hust.datn.entity.Product;
import com.hust.datn.repository.ItemRepository;
import com.hust.datn.repository.ProductRepository;

@Service
public class CartService {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ItemService itemService;
	
	public CartService() { }
	
	public int getDetailCost(Cart cart) {
		Optional<Product> optional = productRepository.findById(cart.getProductId());
		if(!optional.isPresent())
			return 0;
		Product product = optional.get();
		int cost = product.getDiscountCost();
		
		if(cart.getItems() != null && !cart.getItems().isEmpty())
			for (String item : cart.getItems().split(";")) {
				UUID itemId = UUID.fromString(item);
				Optional<OptionItem> option = itemRepository.findById(itemId);
				if(option.isPresent()) {
					OptionItem optionItem = option.get();
					cost += optionItem.getCost();
				}
			}
		
		return cart.getAmount() * cost;
	}
	
	public CartDTO getCartDTO(Cart cart) {
		CartDTO dto = new CartDTO();
		
		dto.id = cart.getId();
		dto.userId = cart.getUserId();
		dto.itemsString = cart.getItems();
		dto.product = ProductPreviewDTO.fromProduct(productRepository.findById(cart.getProductId()).get());
		dto.amount = cart.getAmount();
		dto.totalAmount = cartService.getDetailCost(cart);
		dto.items = itemService.itemsFromString(cart.getItems());
		
		return dto;
	}
}
