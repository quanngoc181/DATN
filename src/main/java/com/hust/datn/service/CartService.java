package com.hust.datn.service;

import java.util.List;
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
		Product product = productRepository.findById(cart.getProductId()).get();
		int cost = product.getDiscountCost();
		
		if(!cart.getItems().isEmpty())
			for (String item : cart.getItems().split(";")) {
				UUID itemId = UUID.fromString(item);
				OptionItem optionItem = itemRepository.findById(itemId).get();
				cost += optionItem.getCost();
			}
		
		return cart.getAmount() * cost;
	}
	
	public CartDTO getCartDTO(Cart cart) {
		CartDTO dto = new CartDTO();
		
		dto.id = cart.getId();
		dto.userId = cart.getUserId();
		dto.product = ProductPreviewDTO.fromProduct(productRepository.findById(cart.getProductId()).get());
		dto.amount = cart.getAmount();
		dto.totalAmount = cartService.getDetailCost(cart);
		dto.items = itemService.itemsFromString(cart.getItems());
		
		return dto;
	}
}
