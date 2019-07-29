package com.fotic.management.customer.service;

import java.text.ParseException;
import java.util.List;

/**
 * 客户家庭住址信息_历史
 * @author gyx
 */
public interface ISubmitAddressService {

	/**
	 * 从csv文件获取数据，导入表中
	 * @param list
	 * @throws ParseException
	 */
	public void insertAddressFromCsv(List<String[]> list)  throws ParseException;
	
	public void deleteByCertTypeAndCertNo(String certType,String certNo,String dataSrc);
}
