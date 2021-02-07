package com.hust.datn.command;

public class SearchCommand {
	public String keyword;
	public String categories;
	public String discount;
	
	public SearchCommand() {
		super();
	}

	public SearchCommand(String keyword, String categories, String discount) {
		super();
		this.keyword = keyword;
		this.categories = categories;
		this.discount = discount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
