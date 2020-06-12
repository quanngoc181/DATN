package com.hust.datn.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.CartDTO;
import com.hust.datn.dto.OrderDTO;
import com.hust.datn.dto.OrderProductDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Order;
import com.hust.datn.entity.OrderProduct;
import com.hust.datn.entity.Product;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.enums.OrderType;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.repository.ReceiveAddressRepository;

@Service
public class OrderService {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReceiveAddressRepository receiveAddressRepository;
	
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
	
	public Order createOrder(UUID userId, UUID addressId, String note) throws InternalException {
		Order order = new Order();
		
		if(userId == null || addressId == null)
			throw new InternalException("Tham số lỗi");
		
		Optional<Account> account = accountRepository.findById(userId);
		if(!account.isPresent())
			throw new InternalException("Không tìm thấy tài khoản");
		
		order.setOrderAccountId(account.get().getId());
		order.setOrderAccount(account.get().getUsername());
		order.setOrderName(account.get().getFirstName() + " " + account.get().getLastName());
		order.setOrderPhone(account.get().getPhone());
		
		Optional<ReceiveAddress> receiveAddress = receiveAddressRepository.findById(addressId);
		if(!receiveAddress.isPresent())
			throw new InternalException("Không tìm thấy địa chỉ");
		
		order.setAddressName(receiveAddress.get().getAddressName());
		order.setName(receiveAddress.get().getName());
		order.setAddress(receiveAddress.get().getAddress());
		order.setPhone(receiveAddress.get().getPhone());
		
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		Set<OrderProduct> products = new HashSet<>();
		int productCost = 0;
		for (Cart cart : carts) {
			Optional<Product> product = productRepository.findById(cart.getProductId());
			if(product.isPresent()) {
				OrderProduct orderProduct = new OrderProduct();
				
				orderProduct.setAmount(cart.getAmount());
				
				orderProduct.setCode(product.get().getProductCode());
				orderProduct.setName(product.get().getName());
				orderProduct.setImage(product.get().getImage());
				orderProduct.setCost(product.get().getDiscountCost());
				
				CartDTO cartDTO = cartService.getCartDTO(cart);
				orderProduct.setItems(cartDTO.getItemStringLite());
				
				orderProduct.setOrder(order);
				
				productCost += cartService.getDetailCost(cart);
				
				products.add(orderProduct);
			}
		}
		order.setOrderProducts(products);
		order.setProductCost(productCost);
		
		int shippingFee = constantService.getAll().shippingFee;
		order.setShippingFee(shippingFee);
		
		order.setType(OrderType.ONLINE);
		order.setStatus(OrderStatus.UNPAID);
		order.setNote(note);
		order.setCost(productCost + shippingFee);
		
		return order;
	}
}
