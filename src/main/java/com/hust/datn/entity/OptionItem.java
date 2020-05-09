package com.hust.datn.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "OPTION_ITEM")
public class OptionItem extends ParentEntity {
	@Nationalized
	private String name;
	
	private int cost;
	
	@ManyToOne
	@JsonIgnore
	private ProductOption option;
	
	public OptionItem() {
		super();
	}

	public OptionItem(UUID id, String name, int cost, ProductOption option) {
		super.setId(id);
		this.name = name;
		this.cost = cost;
		this.option = option;
	}

	public ProductOption getOption() {
		return option;
	}

	public void setOption(ProductOption option) {
		this.option = option;
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
