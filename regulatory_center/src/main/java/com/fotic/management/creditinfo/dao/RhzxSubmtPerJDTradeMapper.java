package com.fotic.management.creditinfo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.fotic.management.creditinfo.entity.RhzxSubmtPerJDTrade;
import com.fotic.management.creditinfo.providers.RhzxSubmtPerTradeJDProvider;

import tk.mybatis.mapper.common.Mapper;

public interface RhzxSubmtPerJDTradeMapper extends Mapper<RhzxSubmtPerJDTrade> {

	@SelectProvider(type = RhzxSubmtPerTradeJDProvider.class, method = "findFactoryList")
	@Results(value = {
			@Result(id = true, property = "repayData", column = "REPAY_DATA", javaType = Date.class, jdbcType = JdbcType.DATE),
			@Result(property = "IOUNo", column = "IOU_NO", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "credNo", column = "cred_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "conNo", column = "con_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "projName", column = "proj_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "projId", column = "proj_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "coOrgCode", column = "co_org_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "coOrgName", column = "co_org_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "dataSrc", column = "data_src", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "checkResult", column = "check_result", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
	public List<RhzxSubmtPerJDTrade> findFactoryList(@Param(value = "projectId") String projectId,
			@Param(value = "coOrgCode") String coOrgCode, @Param(value = "IOUNo") String IOUNo,
			@Param(value = "credNo") String credNo, @Param(value = "checkResult") String checkRest,
			@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
			@Param(value = "dataSrc") String dataSrc);
}