package com.hust.datn.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchCommand {
	public String keyword;
	public String categories;
	public String discount;
	
	public SearchCommand() {
	}

	public SearchCommand(String keyword, String categories, String discount) {
		super();
		this.keyword = keyword;
		this.categories = categories;
		this.discount = discount;
	}
	
	public List<UUID> getUUIDs() {
		List<UUID> uuids = new ArrayList<>();
		if(this.categories.isEmpty())
			return uuids;
		String[] ids = this.categories.split(";");
		for (String id : ids) {
			uuids.add(UUID.fromString(id));
		}
		return uuids;
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
