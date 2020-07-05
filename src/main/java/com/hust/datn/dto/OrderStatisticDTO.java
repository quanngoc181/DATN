package com.hust.datn.dto;

import java.util.List;

public class OrderStatisticDTO {
	public List<Integer> amountsOrder;
	public List<Integer> valuesOrder;
	
	public OrderStatisticDTO() {
		super();
	}

	public OrderStatisticDTO(List<Integer> amountsOrder, List<Integer> valuesOrder) {
		super();
		this.amountsOrder = amountsOrder;
		this.valuesOrder = valuesOrder;
	}
}
