package com.fotic.management.reported.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.fotic.management.reported.entity.SubmitPerTrade;
import com.fotic.management.reported.providers.SubmitPerTradeProvider;

import tk.mybatis.mapper.common.Mapper;
/**
 * 交易结果表mapper
 * @author zhaoqh
 */
public interface SubmitPerTradeMapper extends Mapper<SubmitPerTrade> {

	
	/**
	 * 查询需上报数据
	 * @param dataSrc	数据来源
	 * @return
	 */
	@SelectProvider(type = SubmitPerTradeProvider.class, method = "findList" )
	public List<SubmitPerTrade> findList(@Param("dataSrc") Integer dataSrc);
	
	@SelectProvider(type = SubmitPerTradeProvider.class, method = "findListOrg" )
	public List<SubmitPerTrade> findListOrg(@Param("dataSrc") Integer dataSrc);
	/**
	 * 查询需上报数据
	 * @param dataSrc	数据来源
	 * @return
	 */
	@SelectProvider(type = SubmitPerTradeProvider.class, method = "findListAll" )
	public List<SubmitPerTrade> findListAll(@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("dataSrc") Integer dataSrc,@Param("coOrgName") String coOrgName);

	
	/**
	 * 查询全部需上报数据
	 * @param dataSrc	数据来源
	 * @return
	 */
	@SelectProvider(type = SubmitPerTradeProvider.class, method = "findAll" )
	public List<SubmitPerTrade> findAll(@Param("dataSrc") Integer dataSrc);
	
	
	/**
	 * 查询全部需上报数据
	 * @param dataSrc	数据来源
	 * @param bussDate  业务日期
	 * @return
	 */
	@SelectProvider(type = SubmitPerTradeProvider.class, method = "findByBussDate" )
	public List<SubmitPerTrade> findByBussDate(@Param("dataSrc") Integer dataSrc,@Param("bussDate") String bussDate);
	
}
