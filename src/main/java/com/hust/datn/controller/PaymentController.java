package com.hust.datn.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.entity.Authorities;
import com.hust.datn.entity.Notification;
import com.hust.datn.entity.Order;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.AuthoritiesRepository;
import com.hust.datn.repository.NotificationRepository;
import com.hust.datn.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Controller
public class PaymentController {
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;
	
	@GetMapping("/user/payment")
	public String index(String orderId, Model model) throws StripeException, Exception {
		Optional<Order> order = orderRepository.findById(UUID.fromString(orderId));
		if(!order.isPresent())
			throw new Exception("Không tìm thấy đơn hàng");
		
		LocalDateTime createAt = order.get().getCreateAt();
		LocalDateTime now = LocalDateTime.now();
		if(createAt.getYear() != now.getYear() || createAt.getMonthValue() != now.getMonthValue() || createAt.getDayOfMonth() != now.getDayOfMonth())
			model.addAttribute("error", "Đơn hàng chỉ được phép thanh toán trong ngày!!!");
		
		Stripe.apiKey = "sk_test_51GtnsjJG8N5EtzpV8upDLc6PZ9xhr8bQkQh6s8KcABXqyo5nq8wKxYgzjpgAwIrJc68aWdnrumMd7nR6bY4jO8u8000vBgM4Ok";

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setCurrency("vnd").setAmount((long) order.get().getCost()).build();

		PaymentIntent intent = PaymentIntent.create(params);

		model.addAttribute("client_secret", intent.getClientSecret());
		model.addAttribute("orderId", order.get().getId());

		return "user/payment";
	}
	
	@PostMapping("/user/payment-success")
	@ResponseBody
	public void paymentSuccess(String orderId) throws InternalException {
		Optional<Order> order = orderRepository.findById(UUID.fromString(orderId));
		if(!order.isPresent())
			throw new InternalException("Không tìm thấy đơn hàng");
		
		Order od = order.get();
		od.setStatus(OrderStatus.PAID);
		
		orderRepository.save(od);
		
		notificationRepository.save(new Notification(od.getOrderAccount(), "Thanh toán thành công đơn hàng " + od.getId().toString(), "/user/my-order", false));
		notificationRepository.save(new Notification("SYSTEM", "Có đơn hàng vừa mới được thanh toán " + od.getId().toString(), "/admin/order-management", false));
		
		List<Authorities> authors = authoritiesRepository.findAll();
		for (Authorities author : authors) {
			if(author.getAuthority().equals("ROLE_ADMIN"))
			messagingTemplate.convertAndSendToUser(author.getUsername(), "/queue/notification-updates", "");
		}
		messagingTemplate.convertAndSendToUser(od.getOrderAccount(), "/queue/notification-updates", "");
	}
}
