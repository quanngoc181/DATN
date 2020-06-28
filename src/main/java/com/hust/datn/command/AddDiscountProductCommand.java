package com.hust.datn.command;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.hust.datn.enums.DiscountUnit;
import com.hust.datn.exception.InternalException;
import com.hust.datn.validator.ValidCost;
import com.hust.datn.validator.ValidDate;
import com.hust.datn.validator.ValidTime;

public class AddDiscountProductCommand {
	public String id;
	
	@NotBlank(message = "Mô tả không hợp lệ")
	public String description;
	
	@ValidCost(message = "Số tiền không hợp lệ")
	public String amount;
	
	public int unit;
	
	@ValidDate(message = "Ngày bắt đầu không hợp lệ")
	public String startDate;
	
	@ValidTime(message = "Thời gian bắt đầu không hợp lệ")
	public String startTime;
	
	@ValidDate(message = "Ngày kết thúc không hợp lệ")
	public String endDate;
	
	@ValidTime(message = "Thời gian kết thúc không hợp lệ")
	public String endTime;
	
	public String products;

	public AddDiscountProductCommand() {
		super();
	}

	public AddDiscountProductCommand(String id, String description, String amount, int unit, String startDate,
			String startTime, String endDate, String endTime, String products) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.unit = unit;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.products = products;
	}
	
	public void validate() throws InternalException {
		try {
			int a = Integer.parseInt(amount);
			if(unit == 0) {
				if(a < 0 || a > 100)
					throw new Exception();
			} else {
				if(a < 0)
					throw new Exception();
			}
		} catch (Exception e) {
			throw new InternalException("Số tiền không hợp lệ");
		}
		
		if(getStartDateTime().compareTo(getEndDateTime()) >= 0)
			throw new InternalException("Thời gian bắt đầu phải nhỏ hơn thời gian hết thúc");
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getStartDateTime() {
		return LocalDateTime.of(
				Integer.parseInt(startDate.substring(6)), 
				Integer.parseInt(startDate.substring(3, 5)),
				Integer.parseInt(startDate.substring(0, 2)),
				Integer.parseInt(startTime.substring(0, 2)),
				Integer.parseInt(startTime.substring(3)));
	}
	
	public LocalDateTime getEndDateTime() {
		return LocalDateTime.of(
				Integer.parseInt(endDate.substring(6)), 
				Integer.parseInt(endDate.substring(3, 5)),
				Integer.parseInt(endDate.substring(0, 2)),
				Integer.parseInt(endTime.substring(0, 2)),
				Integer.parseInt(endTime.substring(3)));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return Integer.parseInt(amount);
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public DiscountUnit getUnit() {
		return DiscountUnit.values()[unit];
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
