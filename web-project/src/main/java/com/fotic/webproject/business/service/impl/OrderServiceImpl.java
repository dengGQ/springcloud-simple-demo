package com.fotic.webproject.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fotic.webproject.business.entity.Order;
import com.fotic.webproject.business.repository.OrderRepository;
import com.fotic.webproject.business.service.OrderService;
import com.fotic.webproject.business.vo.OrderVo;
import com.fotic.webproject.jpadata.service.AbstractBaseService;

@Service
public class OrderServiceImpl extends AbstractBaseService<Order, OrderRepository> implements OrderService{
	
	@Autowired
	private OrderRepository or;
	
	@Override
	public OrderVo queryOrderById(Long id) {
		OrderVo orderVo = or.queryOrderById(id);
		return orderVo;
	}

	@Override
	public Page<OrderVo> queryOrderByOrderNum(String orderNum, Pageable pageable) {
		return or.queryOrderByOrderNum(orderNum, pageable);
	}
}
