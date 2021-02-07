package com.hust.datn.command;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.hust.datn.validator.ValidCost;

public class AddProductCommand {
	public String id;
	public String categoryId;
	
	@NotBlank(message = "Tên sản phẩm không hợp lệ")
	public String name;
	
	@ValidCost(message = "Giá tiền không hợp lệ")
	public String cost;
	
	public MultipartFile file;
	public String[] options;
	
	public AddProductCommand() {
		super();
	}

	public AddProductCommand(String id, String categoryId, String name, String cost, MultipartFile file, String[] options) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.cost = cost;
		this.file = file;
		this.options = options;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
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
		return Integer.parseInt(cost);
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
