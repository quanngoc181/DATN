package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "OPTION_ITEM")
public class OptionItem extends ParentEntity {
	@Nationalized
	private String name;
	
	private int cost;
	
	public OptionItem() {
		super();
	}

	public OptionItem(UUID id, String name, int cost) {
		super.setId(id);
		this.name = name;
		this.cost = cost;
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
}
