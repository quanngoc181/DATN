package com.hust.datn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.entity.Notification;
import com.hust.datn.repository.NotificationRepository;

@Controller
public class NotificationController {
	@Autowired
	NotificationRepository notificationRepository;
	
	@GetMapping("/notification/update")
	@ResponseBody
	public ModelAndView update(Authentication auth) {
		String username = auth.getName();
		if(auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
			username = "SYSTEM";
		}
		List<Notification> notifications = notificationRepository.findByReceiverOrderByCreateAtDesc(username);
		int unSeen = (int) notifications.stream().filter(obj -> !obj.isSeen()).count();
		
		Map<String, Object> model = new HashMap<>();
		model.put("notifications", notifications);
		model.put("unSeen", unSeen);
		
		return new ModelAndView("/partial/notification", model);
	}
	
	@PostMapping("/notification/seen")
	@ResponseBody
	public void seen(String id) {
		Optional<Notification> notification = notificationRepository.findById(UUID.fromString(id));
		if(notification.isPresent()) {
			Notification noti = notification.get();
			noti.setSeen(true);
			notificationRepository.save(noti);
		}
	}
}
