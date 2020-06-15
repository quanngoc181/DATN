package com.hust.datn.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends ParentEntity {
	private String receiver;
	
	@Nationalized
	private String content;
	
	private String url;
	
	private boolean seen;
	
	public Notification() {
	}

	public Notification(String receiver, String content, String url, boolean seen) {
		super();
		this.receiver = receiver;
		this.content = content;
		this.url = url;
		this.seen = seen;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
