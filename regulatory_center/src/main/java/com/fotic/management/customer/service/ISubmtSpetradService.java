package com.fotic.management.customer.service;

import java.text.ParseException;
import java.util.List;

public interface ISubmtSpetradService {
	public void insertSpetradFromCsv(List<String[]> list)  throws ParseException;
	
	/**
	 * 根据业务编号、来源删除信息
	 * @param account
	 * @param dataSrc
	 */
	public void deleteByAccountAndDataSrc(String account,String dataSrc);
}
