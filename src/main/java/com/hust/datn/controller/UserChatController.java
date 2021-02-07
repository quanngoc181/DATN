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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.UserMessageDTO;
import com.hust.datn.entity.Authorities;
import com.hust.datn.entity.ChatMessage;
import com.hust.datn.repository.AuthoritiesRepository;
import com.hust.datn.repository.UserChatRepository;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.service.ChatService;

@Controller
public class UserChatController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	UserChatRepository userChatRepository;

	@Autowired
	ChatService chatService;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@PostMapping("/user/chat/add")
	@ResponseBody
	public ModelAndView addMessage(Authentication auth, String message) {
		String username = auth.getName();
		userChatRepository.save(new ChatMessage(username, "SYSTEM", message, false));

		List<ChatMessage> send = userChatRepository.findBySender(username);
		List<ChatMessage> receive = userChatRepository.findByReceiver(username);

		List<ChatMessage> total = Stream.concat(send.stream(), receive.stream()).collect(Collectors.toList());

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt().compareTo(o2.getCreateAt());
		Collections.sort(total, compareByDate);

		List<UserMessageDTO> dtos = chatService.createUserMessageDto(total, username);
		int unSeen = (int) total.stream().filter(obj -> !obj.isSeen() && obj.getReceiver().equals(username)).count();

		Map<String, Object> model = new HashMap<>();
		model.put("messages", dtos);
		model.put("unSeen", unSeen);
		
		List<Authorities> authors = authoritiesRepository.findAll();
		for (Authorities author : authors) {
			if(author.getAuthority().equals("ROLE_ADMIN"))
			messagingTemplate.convertAndSendToUser(author.getUsername(), "/queue/chat-updates", "");
		}

		return new ModelAndView("/partial/user-conversation", model);
	}

	@PostMapping("/user/chat/update")
	@ResponseBody
	public ModelAndView updateChat(Authentication auth) {
		String username = auth.getName();

		List<ChatMessage> send = userChatRepository.findBySender(username);
		List<ChatMessage> receive = userChatRepository.findByReceiver(username);

		List<ChatMessage> total = Stream.concat(send.stream(), receive.stream()).collect(Collectors.toList());

		Comparator<ChatMessage> compareByDate = (ChatMessage o1, ChatMessage o2) -> o1.getCreateAt()
				.compareTo(o2.getCreateAt());

		Collections.sort(total, compareByDate);

		List<UserMessageDTO> dtos = chatService.createUserMessageDto(total, username);
		int unSeen = (int) total.stream().filter(obj -> !obj.isSeen() && obj.getReceiver().equals(username)).count();

		Map<String, Object> model = new HashMap<>();
		model.put("messages", dtos);
		model.put("unSeen", unSeen);

		return new ModelAndView("/partial/user-conversation", model);
	}
	
	@PostMapping("/user/chat/seen")
	@ResponseBody
	public void seenChat(Authentication auth) {
		String username = auth.getName();

		List<ChatMessage> receive = userChatRepository.findByReceiver(username);
		
		for (ChatMessage chatMessage : receive) {
			chatMessage.setSeen(true);
			userChatRepository.save(chatMessage);
		}
	}
}
