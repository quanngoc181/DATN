package com.hust.datn.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage extends ParentEntity {
	private String sender;
	
	private String receiver;
	
	@Nationalized
	private String content;
	
	private boolean seen;
	
	public ChatMessage() {
	}

	public ChatMessage(String sender, String receiver, String content, boolean seen) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.seen = seen;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
}
