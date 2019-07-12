package com.fotic.webproject.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fotic.webproject.business.entity.Order;
import com.fotic.webproject.business.vo.OrderVo;
import com.fotic.webproject.jpadata.service.BaseService;

public interface OrderService extends BaseService<Order>{
	
	OrderVo queryOrderById(Long id);
	
	Page<OrderVo> queryOrderByOrderNum(String orderNum, Pageable pageable);
}
