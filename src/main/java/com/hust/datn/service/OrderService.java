package com.hust.datn.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.CartDTO;
import com.hust.datn.dto.OrderDTO;
import com.hust.datn.dto.OrderProductDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.repository.ProductRepository;

@Service
public class OrderService {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ConstantService constantService;
	
	public OrderService() { }
	
	public OrderDTO createOrderDTO(UUID userId) {
		OrderDTO dto = new OrderDTO();
		
		Account account = accountRepository.findById(userId).get();
		
		for (ReceiveAddress receiveAddress : account.getReceiveAddresses()) {
			if(receiveAddress.isDefault()) {
				dto.setAddressId(receiveAddress.getId());
				dto.setAddressName(receiveAddress.getAddressName());
				dto.setName(receiveAddress.getName());
				dto.setAddress(receiveAddress.getAddress());
				dto.setPhone(receiveAddress.getPhone());
				
				break;
			}
		}
		
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		List<OrderProductDTO> productDTOs = new ArrayList<>();
		int productCost = 0;
		for (Cart cart : carts) {
			OrderProductDTO productDto = new OrderProductDTO();
			
			productDto.setAmount(cart.getAmount());
			Optional<Product> product = productRepository.findById(cart.getProductId());
			if(product.isPresent()) {
				productDto.setCode(product.get().getProductCode());
				productDto.setName(product.get().getName());
				String image = product.get().getImage() == null ? "/images/default-product.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(product.get().getImage()));
				productDto.setImage(image);
				productDto.setCost(product.get().getDiscountCost());
				
				CartDTO cartDTO = cartService.getCartDTO(cart);
				productDto.setItems(cartDTO.getItemStringLite());
				
				productCost += cartService.getDetailCost(cart);
			}
			productDTOs.add(productDto);
		}
		dto.products = productDTOs;
		dto.productCost = productCost;
		
		int shippingFee = constantService.getAll().shippingFee;
		dto.shippingFee = shippingFee;
		
		return dto;
	}
}
