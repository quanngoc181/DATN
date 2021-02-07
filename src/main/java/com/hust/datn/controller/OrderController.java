package com.hust.datn.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.OrderDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.Authorities;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Notification;
import com.hust.datn.entity.Order;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.AuthoritiesRepository;
import com.hust.datn.repository.CartRepository;
import com.hust.datn.repository.NotificationRepository;
import com.hust.datn.repository.OrderRepository;
import com.hust.datn.repository.ReceiveAddressRepository;
import com.hust.datn.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ReceiveAddressRepository receiveAddressRepository;
	
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	OrderService orderService;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@GetMapping("/user/order-preview")
	public String orderPreview(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);

		OrderDTO orderDTO = orderService.createOrderDTO(account.getId());
		model.addAttribute("order", orderDTO);

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
		
		notificationRepository.save(new Notification(account.getUsername(), "Tạo thành công đơn hàng " + order.getId().toString(), "/user/my-order", false));
		notificationRepository.save(new Notification("SYSTEM", "Có đơn hàng vừa mới được tạo " + order.getId().toString(), "/admin/order-management", false));

		List<Authorities> authors = authoritiesRepository.findAll();
		for (Authorities author : authors) {
			if(author.getAuthority().equals("ROLE_ADMIN"))
			messagingTemplate.convertAndSendToUser(author.getUsername(), "/queue/notification-updates", "");
		}
		messagingTemplate.convertAndSendToUser(account.getUsername(), "/queue/notification-updates", "");
		
		return order.getId().toString();
	}

	@GetMapping("/user/my-order")
	public String myOrder(Authentication auth, Model model) {
		Account account = accountRepository.findByUsername(auth.getName());
		model.addAttribute("user", account);

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
