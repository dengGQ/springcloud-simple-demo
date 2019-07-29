package com.fotic.management.creditinfo.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.creditinfo.providers.RhzxSubmtPerTradeProvider;

import tk.mybatis.mapper.common.Mapper;

public interface RhzxSubmtPerTradeMapper extends Mapper<RhzxSubmtPerTrade> {

	@SelectProvider(type = RhzxSubmtPerTradeProvider.class, method = "findList")
	@Results(value = {
			@Result(id = true, property = "repayData", column = "REPAY_DATA", javaType = Date.class, jdbcType = JdbcType.DATE),
			
			@Result(property = "credNo", column = "cred_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "conNo", column = "con_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "projName", column = "proj_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "projId", column = "proj_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "coOrgCode", column = "co_org_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "coOrgName", column = "co_org_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "dataSrc", column = "data_src", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "credType", column = "cred_type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "checkResult", column = "check_result", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "curtermspastdue", column = "curtermspastdue", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
			
	public List<RhzxSubmtPerTrade> findList(@Param(value = "projectId") String projectId,
			@Param(value = "coOrgCode") String coOrgCode, @Param(value = "IOUNo") String IOUNo,
			@Param(value = "credNo") String credNo, @Param(value = "checkResult") String checkRest,
			@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
			@Param(value = "dataSrc") String dataSrc,
			@Param(value = "curtermspastdue") String curtermspastdue)
			;
	
	
	@SelectProvider(type = RhzxSubmtPerTradeProvider.class, method = "queryEditInfoByConNo")
	@Results(value = {
			@Result(id = true,property = "conNo", column = "CON_NO", javaType = String.class, jdbcType = JdbcType.VARCHAR),
					@Result(property = "actualId", column = "ID", javaType = String.class, jdbcType = JdbcType.VARCHAR),
					@Result(property = "actualAmt", column = "ACTUAL_AMT", javaType = BigDecimal.class, jdbcType = JdbcType.NUMERIC),
					@Result(property = "actualRepayDate", column = "ACTUAL_REPAY_DATE", javaType = Date.class, jdbcType = JdbcType.DATE),
					@Result(property = "repayAmt", column = "REPAY_AMT", javaType = BigDecimal.class, jdbcType = JdbcType.NUMERIC),
					@Result(property = "repayDate", column = "REPAY_DATE", javaType = Date.class, jdbcType = JdbcType.DATE)
	} ) 
	public List<Map<String,Object>> queryEditInfoByConNo(String conNo);
	
}