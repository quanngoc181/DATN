package com.hust.datn.command;

import java.time.LocalDateTime;

import com.hust.datn.enums.DiscountUnit;

public class AddDiscountProductCommand {
	public String id;
	public String description;
	public int amount;
	public int unit;
	public String startDate;
	public String startTime;
	public String endDate;
	public String endTime;
	public String products;

	public AddDiscountProductCommand() {
	}

	public AddDiscountProductCommand(String id, String description, int amount, int unit, String startDate,
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
		return amount;
	}

	public void setAmount(int amount) {
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
