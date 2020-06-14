package com.hust.datn.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.ContactItem;
import com.hust.datn.dto.UserMessageDTO;
import com.hust.datn.entity.ChatMessage;
import com.hust.datn.repository.UserChatRepository;
import com.hust.datn.service.ChatService;

@Controller
public class ChatManagementController {
	@Autowired
	UserChatRepository userChatRepository;

	@Autowired
	ChatService chatService;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@GetMapping("/admin/chat-management")
	public String index() {
		return "admin/chat-management";
	}

	@GetMapping("/admin/chat-management/update")
	@ResponseBody
	public ModelAndView update() {
		List<ChatMessage> total = userChatRepository.findAll();

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt()
				.compareTo(o2.getCreateAt());
		Collections.sort(total, compareByDate.reversed());

		List<ContactItem> contacts = chatService.getContactList(total);
		int unSeen = (int) contacts.stream().filter(obj -> !obj.seen).count();

		Map<String, Object> model = new HashMap<>();
		model.put("contacts", contacts);
		model.put("unSeen", unSeen);

		return new ModelAndView("/partial/contact-list", model);
	}

	@GetMapping("/admin/chat-management/get-conversation")
	@ResponseBody
	public ModelAndView getConversation(String user) {
		List<ChatMessage> send = userChatRepository.findBySender(user);
		List<ChatMessage> receive = userChatRepository.findByReceiver(user);

		List<ChatMessage> total = Stream.concat(send.stream(), receive.stream()).collect(Collectors.toList());

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt()
				.compareTo(o2.getCreateAt());
		Collections.sort(total, compareByDate);

		List<UserMessageDTO> dtos = chatService.createUserMessageDto(total);

		Map<String, Object> model = new HashMap<>();
		model.put("messages", dtos);
		model.put("username", user);

		return new ModelAndView("partial/admin-conversation", model);
	}

	@PostMapping("/admin/chat-management/seen")
	@ResponseBody
	public ModelAndView seen(String user) {
		List<ChatMessage> receive = userChatRepository.findBySenderAndReceiver(user, "SYSTEM");

		for (ChatMessage chatMessage : receive) {
			chatMessage.setSeen(true);
			userChatRepository.save(chatMessage);
		}

		List<ChatMessage> total = userChatRepository.findAll();

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt()
				.compareTo(o2.getCreateAt());
		Collections.sort(total, compareByDate.reversed());

		List<ContactItem> contacts = chatService.getContactList(total);
		int unSeen = (int) contacts.stream().filter(obj -> !obj.seen).count();

		Map<String, Object> model = new HashMap<>();
		model.put("contacts", contacts);
		model.put("unSeen", unSeen);
		model.put("selecting", user);

		return new ModelAndView("/partial/contact-list", model);
	}
	
	@PostMapping("/admin/chat-management/add")
	@ResponseBody
	public ModelAndView add(String username, String message) {
		userChatRepository.save(new ChatMessage("SYSTEM", username, message, false));
		
		List<ChatMessage> send = userChatRepository.findBySender(username);
		List<ChatMessage> receive = userChatRepository.findByReceiver(username);

		List<ChatMessage> total = Stream.concat(send.stream(), receive.stream()).collect(Collectors.toList());

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt()
				.compareTo(o2.getCreateAt());
		Collections.sort(total, compareByDate);

		List<UserMessageDTO> dtos = chatService.createUserMessageDto(total);

		Map<String, Object> model = new HashMap<>();
		model.put("messages", dtos);
		model.put("username", username);
		
		messagingTemplate.convertAndSendToUser(username, "/queue/chat-updates", "");

		return new ModelAndView("partial/admin-conversation", model);
	}
}
