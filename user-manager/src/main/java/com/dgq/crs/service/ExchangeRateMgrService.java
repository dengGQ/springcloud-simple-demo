package com.dgq.crs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dgq.crs.entity.ExchangeRateMgr;

/*
* @Description: public class ExchangeRateMgr{ }
* @author dgq 
* @date 2018年12月7日
*/
public interface ExchangeRateMgrService{
	
	void insertEntity(ExchangeRateMgr o);
	
	Map<String, Object> queryExchangeRateForPage(int pageNum, int pageSize);

	/**
	 * 查询当前日期范围内的汇率列表
	 * @param pageNum 页面
	 * @param pageSize 每页显示数量
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	Map<String, Object> queryExchangeRateForPage(int pageNum, int pageSize, Date startDate, Date endDate);
	
	/**
	 * 根据汇率日期查询
	 * @param o
	 * @return
	 */
	public List<ExchangeRateMgr> queryExchangeRateByDatee(Date datee);
}
