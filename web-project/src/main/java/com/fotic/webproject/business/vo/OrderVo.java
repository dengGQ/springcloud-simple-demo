package com.fotic.webproject.business.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderVo {
	
	private Long id;
	
	private String orderNum;
	
	private Integer orderAmount;
	
	private String acountName;
	
	private Integer balance;

	public OrderVo(Long id, String orderNum, Integer orderAmount, String acountName, Integer balance) {
		super();
		this.id = id;
		this.orderNum = orderNum;
		this.orderAmount = orderAmount;
		this.acountName = acountName;
		this.balance = balance;
	}
	
	
}
