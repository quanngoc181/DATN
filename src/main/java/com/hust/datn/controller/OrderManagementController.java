package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/admin/order-management")
	public String index(Model model) {
		List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));

		List<Order> unpaidOrder = orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.UNPAID)
				.collect(Collectors.toList());
		List<Order> paidOrder = orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PAID)
				.collect(Collectors.toList());
		List<Order> processingOrder = orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.PROCESSING)
				.collect(Collectors.toList());
		List<Order> deliveringOrder = orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.DELIVERING)
				.collect(Collectors.toList());
		List<Order> completedOrder = orders.stream()
				.filter(order -> order.getType() == OrderType.ONLINE && order.getStatus() == OrderStatus.COMPLETED)
				.collect(Collectors.toList());
		List<Order> pickupOrder = orders.stream().filter(order -> order.getType() == OrderType.PICKUP)
				.collect(Collectors.toList());

		model.addAttribute("unpaidOrder", unpaidOrder);
		model.addAttribute("paidOrder", paidOrder);
		model.addAttribute("processingOrder", processingOrder);
		model.addAttribute("deliveringOrder", deliveringOrder);
		model.addAttribute("completedOrder", completedOrder);
		model.addAttribute("pickupOrder", pickupOrder);

		return "admin/order-management";
	}

	@PostMapping("/admin/order-management/change-status")
	@ResponseBody
	public void changeStatus(String id, int status) throws InternalException {
		Optional<Order> o = orderRepository.findById(UUID.fromString(id));
		if (!o.isPresent())
			throw new InternalException("Không tìm thấy đơn hàng");

		Order order = o.get();
		order.setStatus(OrderStatus.values()[status]);

		orderRepository.save(order);
		
		notificationRepository.save(new Notification(order.getOrderAccount(), "Đơn hàng của bạn " + order.getId().toString() + " đã được đánh dấu là " + OrderStatus.values()[status], "/user/my-order", false));
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
	public String addOrder1(@RequestBody AddOrderCommand orderData) {
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
