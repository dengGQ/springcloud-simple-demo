package com.fotic.webproject.business;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fotic.webproject.business.entity.Account;
import com.fotic.webproject.business.entity.Order;
import com.fotic.webproject.business.repository.AccountRepository;
import com.fotic.webproject.business.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTest {
	
	@Autowired
	private AccountService as;
	
	@Autowired
	private AccountRepository ar;
	
//	@Before
	public void init() {
		Account account = new Account();
		account.setAccountName("dgq-wer");
		account.setBalance(3000);
		
		Account account1 = new Account();
		account1.setAccountName("dgq-wer2");
		account1.setBalance(4000);
		
		Account account2 = new Account();
		account2.setAccountName("dgq-wer3");
		account2.setBalance(5000);
		
		Iterable<Account> accounts = as.insertBatch(Arrays.asList(account, account1, account2));
		System.out.println(accounts);
	}
	
	@Test
	public void test() {
//		List<Account> list = as.findAll();
		List<Account> list = ar.getByAccountName("dgq-wer");
		list.parallelStream().forEach(o->{
			List<Order> orders = o.getOrders();
			System.out.println(orders);
		});
		System.out.println(list);
		
		
	}
	
	/*@After
	public void after() {
		as.deleteAll();
	}*/
}
