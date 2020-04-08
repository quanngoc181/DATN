package com.hust.datn.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONSTANT")
public class Constant {
	@Id
	private String keyword;
	
	private int value;
	
	public Constant() {
		super();
	}

	public Constant(String keyword, int value) {
		super();
		this.keyword = keyword;
		this.value = value;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
