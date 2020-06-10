package com.hust.datn.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "CONSTANT")
public class Constant {
	@Id
	private String keyword;
	
	@Nationalized
	private String value;
	
	public Constant() {
		super();
	}

	public Constant(String keyword, String value) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
