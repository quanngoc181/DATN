package com.hust.datn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.ChatMessage;

public interface UserChatRepository extends JpaRepository<ChatMessage, String> {
	List<ChatMessage> findBySender(String username);
	List<ChatMessage> findByReceiver(String username);
	List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);	
}
