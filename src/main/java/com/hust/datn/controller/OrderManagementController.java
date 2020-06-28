package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.command.AddOrderCommand;
import com.hust.datn.command.OrderProductCommand;
import com.hust.datn.dto.CartDTO;
import com.hust.datn.dto.CategoryDTO;
import com.hust.datn.entity.Cart;
import com.hust.datn.entity.Category;
import com.hust.datn.entity.Notification;
import com.hust.datn.entity.Order;
import com.hust.datn.entity.OrderProduct;
import com.hust.datn.entity.Product;
import com.hust.datn.enums.OrderStatus;
import com.hust.datn.enums.OrderType;
import com.hust.datn.exception.InternalException;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.repository.NotificationRepository;
import com.hust.datn.repository.OrderRepository;
import com.hust.datn.repository.ProductRepository;
import com.hust.datn.service.CartService;
import com.hust.datn.service.CategoryService;

@Controller
public class OrderManagementController {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CartService cartService;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@GetMapping("/admin/order-management")
	public String index() {
		return "admin/order-management";
	}
	
	@GetMapping("/admin/order-management/fetchOrder")
	@ResponseBody
	public ModelAndView fetchOrder(int type, int status, String keyword) {
		String key = keyword == null ? "" : keyword;
		List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));

		List<Order> retOrders = orders.stream()
				.filter(order -> order.getType() == OrderType.values()[type] && order.getStatus() == OrderStatus.values()[status] &&
								(order.getId().toString().contains(key) || (order.getOrderAccount() == null ? "" : order.getOrderAccount()).contains(key)))
				.collect(Collectors.toList());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orders", retOrders);
		model.put("status", status);
		model.put("keyword", key);
		
		if(type == 1)
			return new ModelAndView("partial/order-list-offline", model);
		
		return new ModelAndView("partial/order-list", model);
	}

	@PostMapping("/admin/order-management/change-status")
	@ResponseBody
	public void changeStatus(String id, int status) throws InternalException {
		Optional<Order> optional = orderRepository.findById(UUID.fromString(id));
		if (!optional.isPresent())
			throw new InternalException("Không tìm thấy đơn hàng");

		Order order = optional.get();
		order.setStatus(OrderStatus.values()[status]);

		orderRepository.save(order);
		
		notificationRepository.save(new Notification(order.getOrderAccount(), "Đơn hàng của bạn " + order.getId().toString() + " đã được đánh dấu là " + OrderStatus.values()[status], "/user/my-order", false));
		
		messagingTemplate.convertAndSendToUser(order.getOrderAccount(), "/queue/notification-updates", "");
	}

	@GetMapping("/admin/order-management/add")
	@ResponseBody
	public ModelAndView addOrder() {
		List<Category> categories = categoryRepository.findAll();

		List<CategoryDTO> dtos = new ArrayList<>();

		for (Category category : categories) {
			CategoryDTO dto = categoryService.getCategoryDTO(category, true);
			dtos.add(dto);
		}

		return new ModelAndView("partial/add-order", "categories", dtos);
	}

	@PostMapping("/admin/order-management/add")
	@ResponseBody
	public String addOrder1(@RequestBody @Valid AddOrderCommand orderData, BindingResult result) throws InternalException {
		if (result.hasErrors()) {
			throw new InternalException(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		orderData.validate();
		
		Order order = new Order();

		order.setOrderName(orderData.orderName);
		order.setOrderPhone(orderData.orderPhone);

		Set<OrderProduct> products = new HashSet<>();
		int productCost = 0;
		for (OrderProductCommand cart : orderData.products) {
			Optional<Product> product = productRepository.findById(UUID.fromString(cart.productId));
			if (product.isPresent()) {
				OrderProduct orderProduct = new OrderProduct();

				orderProduct.setAmount(cart.amount);

				orderProduct.setCode(product.get().getProductCode());
				orderProduct.setName(product.get().getName());
				orderProduct.setImage(product.get().getImage());
				orderProduct.setCost(product.get().getDiscountCost());

				CartDTO cartDTO = cartService.getCartDTO(new Cart(null, null, UUID.fromString(cart.productId), cart.amount, cart.items));
				orderProduct.setItems(cartDTO.getItemStringLite());

				orderProduct.setOrder(order);

				productCost += cartService.getDetailCost(new Cart(null, null, UUID.fromString(cart.productId), cart.amount, cart.items));

				products.add(orderProduct);
			}
		}
		order.setOrderProducts(products);
		order.setProductCost(productCost);

		order.setShippingFee(0);

		order.setType(OrderType.PICKUP);
		order.setStatus(OrderStatus.COMPLETED);
		order.setNote(orderData.note);
		order.setCost(productCost);
		
		orderRepository.save(order);
		
		return "{}";
	}

	@PostMapping("/admin/order-management/add-to-order")
	@ResponseBody
	public ModelAndView addToOrder(Authentication auth, String productId, int amount, String items) {
		CartDTO dto = cartService.getCartDTO(new Cart(null, null, UUID.fromString(productId), amount, items));

		return new ModelAndView("partial/add-to-order-product", "cart", dto);
	}

	@PostMapping("/admin/order-management/update-amount")
	@ResponseBody
	public int updateAmount(String productId, int amount, String items) {
		return cartService.getDetailCost(new Cart(null, null, UUID.fromString(productId), amount, items));
	}
}
