package com.fotic.webproject.business;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fotic.webproject.business.entity.Order;
import com.fotic.webproject.business.repository.OrderRepository;
import com.fotic.webproject.business.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

	@Autowired
	private OrderService os;
	
	@Autowired
	private OrderRepository or;
	
//	@Before
	public void init() {
		Order order = new Order();
		order.setOrderNum("dd000001");
//		order.setAccountId(1L);
		order.setOrderAmount(60);
		
		Order order1 = new Order();
		order1.setOrderNum("dd000001");
//		order1.setAccount(account);setAccountId(1L);
		order1.setOrderAmount(70);
		
		Order order2 = new Order();
		order2.setOrderNum("dd000001");
//		order2.setAccountId(1L);
		order2.setOrderAmount(160);
		
		os.insertBatch(Arrays.asList(order, order1, order2));
	}
	
	@Test()
	public void testOrder() {
	}
}
