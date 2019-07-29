package com.fotic.management.customer.service;

import java.util.List;

import com.fotic.management.customer.entity.Customer;

/**
 * @author 顾亚玺
 * @date 创建时间：2017年7月17日
 * @version 1.0 * @parameter
 */
public interface ICustomerService {

	/**
	 * 客户信息查询
	 * @param custName
	 * @param credCode
	 * @return
	 */
	List<Customer> findList(String custName,String credCode);
	
	/**
	 * 详细信息查询
	 * @param credCode
	 */
	List<Customer> findDetailsList(String credCode);
}
