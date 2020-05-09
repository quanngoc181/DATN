package com.hust.datn.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption extends ParentEntity {
	@Nationalized
	private String name;
	
	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<OptionItem> items;
	
	public ProductOption() {
		super();
	}

	public ProductOption(UUID id, String name, List<OptionItem> items) {
		super.setId(id);
		this.name = name;
		this.items = items;
	}
	
	public void addItem(OptionItem item) {
		item.setOption(this);
		this.items.add(item);
	}
	
	public void deleteItem(UUID id) {
		OptionItem item = this.items.stream().filter(it -> id.equals(it.getId())).findAny().orElse(null);

		if (item != null) {
			this.items.remove(item);
		}
	}

	public List<OptionItem> getItems() {
		return items;
	}

	public void setItems(List<OptionItem> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
