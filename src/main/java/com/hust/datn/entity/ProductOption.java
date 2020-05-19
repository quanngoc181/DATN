package com.hust.datn.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.hust.datn.enums.OptionType;

@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption extends ParentEntity {
	@Nationalized
	private String name;
	
	private OptionType type;
	
	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<OptionItem> items;
	
	public ProductOption() {
		super();
	}

	public ProductOption(UUID id, String name, OptionType type) {
		super.setId(id);
		this.name = name;
		this.type = type;
	}
	
	public int getIntType() {
		return this.type.ordinal();
	}
	
	public OptionType getType() {
		return type;
	}

	public void setType(OptionType type) {
		this.type = type;
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

	public Set<OptionItem> getItems() {
		return items;
	}

	public void setItems(Set<OptionItem> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
