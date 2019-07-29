package com.fotic.management.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.PubMethod;
import com.fotic.management.customer.dao.CustomerMapper;
import com.fotic.management.customer.entity.Customer;
import com.fotic.management.customer.service.ICustomerService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月17日 下午2:15:36 
 * @version 1.0 * @parameter  
 */
@Service
public class CustomerServiceImpl implements ICustomerService{

	@Autowired
	private CustomerMapper customermapper;
	
	@Override
	public List<Customer> findList(String custName,String credCode) {
		Example example = new Example(Customer.class);
		Criteria criteria = example.createCriteria();
		if(!PubMethod.isEmpty(custName)){
			criteria.andCondition("cust_name like '%"+custName.trim()+"%'");
		}
		if(!PubMethod.isEmpty(credCode)){
			criteria.andCondition("cred_code like '%"+credCode+"%'");
		}
		
		return customermapper.selectByExample(example);
	}

	@Override
	public List<Customer> findDetailsList(String credCode) {
		Example example = new Example(Customer.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("cred_code = '"+credCode+"'");
		return customermapper.selectByExample(example);
	}
}
