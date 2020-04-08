package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "NEWS")
public class News extends ParentEntity {
	@Nationalized
	private String title;
	
	@Nationalized
	private String description;
	
	@Nationalized
	private String content;
	
	public News() {
		super();
	}

	public News(UUID id, String title, String description, String content) {
		super.setId(id);
		this.title = title;
		this.description = description;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
