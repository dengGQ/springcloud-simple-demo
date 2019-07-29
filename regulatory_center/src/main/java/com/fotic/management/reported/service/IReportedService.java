package com.fotic.management.reported.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fotic.management.reported.entity.SubmitPerTrade;

/**
 * 上报(数据推送)service
 * @author zhaoqh
 */
public interface IReportedService {

	/**
	 * 查询需上报数据
	 * @param dataSrc 数据来源
	 * @return
	 */
	public List<SubmitPerTrade> findList(Integer dataSrc);
	
	public List<SubmitPerTrade> findListOrg(Integer dataSrc);
	
	/**
	 * findListAll
	 */
	public List<SubmitPerTrade> findListAll(String startDate,String endDate ,Integer dataSrc,String coOrgName);
	/**
	 * 上报某天数据
	 * @param insertDates	日期集合
	 * @return 是否上报成功的map
	 */
	public Map<String, Object> reporting(String insertDates,HttpServletRequest request);
}
