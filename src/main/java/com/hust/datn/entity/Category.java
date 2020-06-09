package com.hust.datn.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "CATEGORY")
public class Category extends ParentEntity {
	@Nationalized
	private String name;

	private String categoryCode;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Product> products;

	public Category() {
		super();
	}

	public Category(UUID id, String name, String code) {
		super.setId(id);
		this.name = name;
		this.categoryCode = code;
	}
	
	public Category filter(String keyword, String discount) {
		List<Product> products = this.products.stream()
				.filter(product ->
					product.getName().toLowerCase().contains(keyword.toLowerCase()) &&
					(discount.equals("all") ? true : product.isDiscount())
				)
				.collect(Collectors.toList());
		this.products = new HashSet<>(products);
		return this;
	}
	
	public void addProduct(Product product) {
		product.setCategory(this);
		this.products.add(product);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
