package com.fotic.webproject.business.vo;

import java.util.List;

import com.fotic.webproject.business.entity.Order;

import lombok.Data;

@Data
public class AccountVo {
	
	private Long id;
	
	private String acountName;
	
	private Integer balance;
	
	private List<Order> orders;
}
