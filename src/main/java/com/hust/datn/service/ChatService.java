package com.hust.datn.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.ContactItem;
import com.hust.datn.dto.UserMessageDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.ChatMessage;
import com.hust.datn.entity.Users;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.UserRepository;

@Service
public class ChatService {
	@Autowired
	AccountRepository accountRepository;
	
	public ChatService() {
		super();
	}
	
	public List<UserMessageDTO> createUserMessageDto(List<ChatMessage> messages) {
		List<UserMessageDTO> dtos = new ArrayList<>();
		
		for (ChatMessage message : messages) {
			UserMessageDTO dto = new UserMessageDTO();
			
			if(!message.getSender().equals("SYSTEM")) {
				Account account = accountRepository.findByUsername(message.getSender());
				dto.sender = account.getLastName();
				dto.original = false;
				dto.message = message.getContent();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
				dto.date = message.getCreateAt().format(formatter);
				dto.avatar = account.getAvatarString();
			} else {
				dto.sender = "Admin";
				dto.original = true;
				dto.message = message.getContent();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
				dto.date = message.getCreateAt().format(formatter);
				dto.avatar = "/images/logo.svg";
			}
			
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public List<UserMessageDTO> createUserMessageDto(List<ChatMessage> messages, String sender) {
		List<UserMessageDTO> dtos = new ArrayList<>();
		
		for (ChatMessage message : messages) {
			UserMessageDTO dto = new UserMessageDTO();
			
			if(message.getSender().equals(sender)) {
				Account account = accountRepository.findByUsername(sender);
				dto.sender = account.getLastName();
				dto.original = true;
				dto.message = message.getContent();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
				dto.date = message.getCreateAt().format(formatter);
				dto.avatar = account.getAvatarString();
			} else {
				dto.sender = "Admin";
				dto.original = false;
				dto.message = message.getContent();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
				dto.date = message.getCreateAt().format(formatter);
				dto.avatar = "/images/logo.svg";
			}
			
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public List<ContactItem> getContactList(List<ChatMessage> total) {
		List<ContactItem> contacts = new ArrayList<>();
		
//		List<String> users = total.stream()
//				.map(obj -> (obj.getSender().equals("SYSTEM") ? obj.getReceiver() : obj.getSender()))
//				.filter(distinctByKey(p -> p))
//				.collect(Collectors.toList());
		
		List<Account> users = accountRepository.findAll();

		for (Account user : users) {
			boolean seen = true;
			List<ChatMessage> userMessages = total.stream().filter(obj -> obj.getSender().equals(user.getUsername())).collect(Collectors.toList());
			for (ChatMessage message : userMessages) {
				if(!message.isSeen()) {
					seen = false;
					break;
				}
			}
			
			ContactItem contact = new ContactItem(user.getUsername(), user.getAvatarString(), user.getFirstName() + " " + user.getLastName(), seen);
			contacts.add(contact);
		}
		
		return contacts;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
