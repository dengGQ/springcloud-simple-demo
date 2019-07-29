package com.fotic.management.customer.service;

import java.util.List;

import com.fotic.management.customer.entity.SubmitAddressHis;

/**
 * 客户家庭住址信息_历史
 * @author zhaoqh
 */
public interface ISubmitAddressHisService {

	/**
	 * 客户家庭住址信息列表查询
	 * @param submitAddressHis	家庭住址对象(查询条件)
	 * @return
	 */
	List<SubmitAddressHis> findList(SubmitAddressHis submitAddressHis);
}
