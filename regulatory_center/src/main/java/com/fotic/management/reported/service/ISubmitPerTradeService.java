package com.fotic.management.reported.service;

import java.util.List;

import com.fotic.management.reported.entity.SubmitPerTrade;

public interface ISubmitPerTradeService {

	/**
	 *  查询需上报数据
	 *  @param dataSrc 数据来源
	 * @return
	 */
	public List<SubmitPerTrade> findList(Integer dataSrc);
	
	public List<SubmitPerTrade> findListAll(String startDate,String endDate,Integer dataSrc,String coOrgName);
	
	/**
	 * 查询全部需要上报数据
	 * @param dataSrc 数据来源
	 * @return
	 */
	public List<SubmitPerTrade> findAll(Integer dataSrc);
	
	/**
	 * 根据业务日期获取需要上报数据
	 * @return
	 */
	public List<SubmitPerTrade> findByBussDate(Integer dataSrc,String bussDate);
}
