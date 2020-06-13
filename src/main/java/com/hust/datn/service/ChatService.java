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
import com.hust.datn.repository.AccountRepository;

@Service
public class ChatService {
	@Autowired
	AccountRepository accountRepository;
	
	public ChatService() {
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
				dto.avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
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
				dto.avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
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
		
		List<String> users = total.stream()
				.map(obj -> (obj.getSender().equals("SYSTEM") ? obj.getReceiver() : obj.getSender()))
				.filter(distinctByKey(p -> p))
				.collect(Collectors.toList());

		for (String user : users) {
			Account account = accountRepository.findByUsername(user);
			
			String avatar = account.getAvatar() == null ? "/images/default-avatar.png" : new String("data:image/;base64,").concat(Base64.getEncoder().encodeToString(account.getAvatar()));
			
			boolean seen = true;
			List<ChatMessage> userMessages = total.stream().filter(obj -> obj.getSender().equals(user)).collect(Collectors.toList());
			for (ChatMessage message : userMessages) {
				if(!message.isSeen()) {
					seen = false;
					break;
				}
			}
			
			ContactItem contact = new ContactItem(user, avatar, account.getFirstName() + " " + account.getLastName(), seen);
			contacts.add(contact);
		}
		
		return contacts;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
