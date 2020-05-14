package com.hust.datn.entity;

import java.util.Set;
import java.util.UUID;

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

	public Category(UUID id, String name, String code, Set<Product> products) {
		super.setId(id);
		this.name = name;
		this.categoryCode = code;
		this.products = products;
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
