package com.hust.datn.command;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.hust.datn.exception.InternalException;
import com.hust.datn.validator.ValidPhone;

public class AddOrderCommand {
	@NotBlank(message = "Người mua không hợp lệ")
	public String orderName;
	
	@ValidPhone(message = "Số điện thoại không hợp lệ")
	public String orderPhone;
	
	public String note;
	
	public List<OrderProductCommand> products;
	
	public AddOrderCommand() {
		super();
	}

	public AddOrderCommand(String orderName, String orderPhone, String note, List<OrderProductCommand> products) {
		super();
		this.orderName = orderName;
		this.orderPhone = orderPhone;
		this.note = note;
		this.products = products;
	}
	
	public void validate() throws InternalException {
		if(products.size() == 0)
			throw new InternalException("Đơn hàng chưa có sản phẩm");
		
		for (OrderProductCommand product : products) {
			// check trùng sản phẩm
		}
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public List<OrderProductCommand> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProductCommand> products) {
		this.products = products;
	}
}
