package com.hust.datn.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hust.datn.enums.DiscountUnit;

@Entity
@Table(name = "PRODUCT")
public class Product extends ParentEntity {
	@Nationalized
	private String name;
	
	private String productCode;
	
	private int cost;
	
	@Column(columnDefinition = "varbinary(MAX)")
	private byte[] image;
	
	@Column(columnDefinition = "varchar(MAX)")
	private String options;
	
	@ManyToOne
	@JsonIgnore
	private Category category;
	
	@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
	private Set<DiscountProduct> discounts;
	
	public Product() {
		super();
	}

	public Product(UUID id, String name, String code, int cost, byte[] image, String options) {
		super.setId(id);
		this.name = name;
		this.productCode = code;
		this.cost = cost;
		this.image = image;
		this.options = options;
	}
	
	public int getDiscountCost() {
		int cost = this.cost;
		
		for (DiscountProduct discountProduct : discounts) {
			LocalDateTime now = LocalDateTime.now();
			if(now.compareTo(discountProduct.getStartDate()) >= 0 && now.compareTo(discountProduct.getEndDate()) <= 0) {
				if(discountProduct.getUnit() == DiscountUnit.PERCENT) {
					cost -= ((float)discountProduct.getAmount())/100*cost;
				} else {
					cost -= discountProduct.getAmount();
				}
			}
		}
		
		if(cost < 0) return 0;
		
		return cost;
	}
	
	public boolean isDiscount() {
		return this.getDiscountCost() != this.getCost();
	}

	public Set<DiscountProduct> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<DiscountProduct> discounts) {
		this.discounts = discounts;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
