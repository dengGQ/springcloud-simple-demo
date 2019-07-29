package com.fotic.management.unreported.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.fotic.management.unreported.entity.ViewUnreported;
import com.fotic.management.unreported.providers.UnreportedProvider;

import tk.mybatis.mapper.common.Mapper;

/**
 * 应报未报mapper
 * @author zhaoqh
 */
public interface UnreportedMapper extends Mapper<ViewUnreported>{

	
	/**
	 * 应报未报列表查询
	 * @param startMonth	开始月
	 * @param endMonth		结束月
	 * @return
	 */
	@SelectProvider(type = UnreportedProvider.class, method = "findList" )
	@Results(value = { 
			@Result(id = true, property = "repayDate", column = "REPAY_DATE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "iouNo", column = "IOU_NO", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "credCode", column = "CRED_CODE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "conNo", column = "CON_NO", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "coOrgName", column = "CO_ORG_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "dataSrc", column = "DATA_SRC", javaType = String.class, jdbcType = JdbcType.VARCHAR) 
			}
	) 
	public  List<ViewUnreported> findList(@Param(value="startMonth")String startMonth, @Param(value="endMonth")String endMonth, @Param(value="dataSrc")String dataSrc);
	
	
}
