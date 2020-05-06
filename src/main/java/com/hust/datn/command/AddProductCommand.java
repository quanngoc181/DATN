package com.hust.datn.command;

import org.springframework.web.multipart.MultipartFile;

public class AddProductCommand {
	public String id;
	public String categoryId;
	public String name;
	public int cost;
	public MultipartFile file;
	
	public AddProductCommand() {
	}

	public AddProductCommand(String id, String categoryId, String name, int cost, MultipartFile file) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.cost = cost;
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
