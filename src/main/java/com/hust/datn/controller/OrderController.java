package com.hust.datn.controller;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.OrderDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Order;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.repository.OrderRepository;
import com.hust.datn.repository.ReceiveAddressRepository;
import com.hust.datn.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ReceiveAddressRepository receiveAddressRepository;

	@Autowired
	OrderService orderService;

	@GetMapping("/user/order-preview")
	public String orderPreview(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);

		String avatar = account.getAvatar() == null ? "/images/default-avatar.png"
				: new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", avatar);

		OrderDTO orderDTO = orderService.createOrderDTO(account.getId());
		model.addAttribute("order", orderDTO);

		if (orderDTO.getProducts().size() == 0)
			return "redirect:/user/product";

		return "user/order-preview";
	}

	@GetMapping("/user/order-preview/get-address")
	@ResponseBody
	public ModelAndView getAddress(Authentication auth) {
		Account account = accountRepository.findByUsername(auth.getName());

		return new ModelAndView("partial/address-list", "addresses", account.getReceiveAddresses());
	}

	@PostMapping("/user/order-preview/change-address")
	@ResponseBody
	public ModelAndView changeAddress(String id) {
		ReceiveAddress address = receiveAddressRepository.findById(UUID.fromString(id)).get();

		return new ModelAndView("partial/receive-address", "address", address);
	}

	@PostMapping("/user/create-order")
	@ResponseBody
	public String createOrder(Authentication auth, String addressId, String note) throws InternalException {
		Account account = accountRepository.findByUsername(auth.getName());

		Order order = orderService.createOrder(account.getId(), UUID.fromString(addressId), note);
		orderRepository.save(order);

		List<Cart> carts = cartRepository.findByUserId(account.getId());
		for (Cart cart : carts) {
			cartRepository.delete(cart);
		}
		
		return order.getId().toString();
	}

	@GetMapping("/user/my-order")
	public String myOrder(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);

		String avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
		model.addAttribute("avatar", avatar);

		List<Order> orders = orderRepository.findByOrderAccountIdOrderByCreateAtDesc(account.getId());

		List<Order> unpaidOrder = orders.stream().filter(order -> order.getStatus() == OrderStatus.UNPAID).collect(Collectors.toList());
		List<Order> paidOrder = orders.stream().filter(order -> order.getStatus() == OrderStatus.PAID).collect(Collectors.toList());
		List<Order> processingOrder = orders.stream().filter(order -> order.getStatus() == OrderStatus.PROCESSING).collect(Collectors.toList());
		List<Order> deliveringOrder = orders.stream().filter(order -> order.getStatus() == OrderStatus.DELIVERING).collect(Collectors.toList());
		List<Order> completedOrder = orders.stream().filter(order -> order.getStatus() == OrderStatus.COMPLETED).collect(Collectors.toList());
		
		model.addAttribute("unpaidOrder", unpaidOrder);
		model.addAttribute("paidOrder", paidOrder);
		model.addAttribute("processingOrder", processingOrder);
		model.addAttribute("deliveringOrder", deliveringOrder);
		model.addAttribute("completedOrder", completedOrder);

		return "user/my-order";
	}
}
