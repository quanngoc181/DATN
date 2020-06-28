package com.hust.datn.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.hust.datn.enums.OptionType;
import com.hust.datn.exception.InternalException;

@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption extends ParentEntity {
	@Nationalized
	private String name;
	
	private OptionType type;
	
	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy(value = "createAt ASC")
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
		if(this.type == OptionType.MULTIPLE) {
			this.items.add(item);
		} else {
			if(this.items.size() == 0) {
				item.setDefault(true);
				this.items.add(item);
			} else {
				if(item.isDefault()) {
					for (OptionItem optionItem : this.items) {
						optionItem.setDefault(false);
					}
					this.items.add(item);
				} else {
					this.items.add(item);
				}
			}
		}
	}
	
	public void editItem(OptionItem item) throws InternalException {
		for (OptionItem optionItem : this.items) {
			if(optionItem.getId().equals(item.getId())) {
				optionItem.setName(item.getName());
				optionItem.setCost(item.getCost());
				if(this.type == OptionType.MULTIPLE) {
					optionItem.setDefault(item.isDefault());
				} else {
					if(item.isDefault()) {
						this.setDefault(optionItem.getId());
					} else if (!item.isDefault() && optionItem.isDefault()) {
						throw new InternalException("SINGLE type cần 1 lựa chọn mặc định");
					}
				}
				break;
			}
		}
	}
	
	public void setDefault(UUID id) {
		for (OptionItem optionItem : this.items) {
			if(optionItem.getId().equals(id))
				optionItem.setDefault(true);
			else
				optionItem.setDefault(false);
		}
	}
	
	public void deleteItem(UUID id) throws InternalException {
		OptionItem item = this.items.stream().filter(it -> id.equals(it.getId())).findAny().orElse(null);

		if (item != null) {
			if(item.isDefault() && this.type.equals(OptionType.SINGLE))
				throw new InternalException("Không thể xóa lựa chọn mặc định");
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
