package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hust.datn.enums.DiscountUnit;

@Entity
@Table(name = "DISCOUNT_PRODUCT")
public class DiscountProduct extends ParentEntity {
	@Nationalized
	private String description;
	
	private int amount;
	
	private DiscountUnit unit;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Product> products;
	
	public DiscountProduct() {
		super();
	}

	public DiscountProduct(UUID id, String description, int amount, DiscountUnit unit, LocalDateTime startDate, LocalDateTime endDate) {
		this.setId(id);
		this.description = description;
		this.amount = amount;
		this.unit = unit;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public void deleteProduct(UUID id) {
		Product product = this.products.stream().filter(prd -> id.equals(prd.getId())).findAny().orElse(null);

		if (product != null) {
			this.products.remove(product);
		}
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public DiscountUnit getUnit() {
		return unit;
	}

	public void setUnit(DiscountUnit unit) {
		this.unit = unit;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}
