package com.fotic.management.datacheck.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.fotic.management.datacheck.entity.SelfCheckExprt;
import com.fotic.management.datacheck.providers.DataCheckProvider;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author WangTao
 *
 */
public interface DataCheckMapper extends Mapper<SelfCheckExprt>{

	
	/**
	 * 按季度查询自检信息
	 * @param quarter 季度 like 201701 201604
	 * @return 自检信息列表 SelfCheckExprt
	 */
	@SelectProvider(type = DataCheckProvider.class, method = "queryDataCheck" )
	@Results(value = { 
			@Result( property = "seqId", column = "SEQ_ID", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "quatr", column = "QUATR", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "fileName", column = "FILE_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "bussDate", column = "BUSS_DATE", javaType = Date.class, jdbcType = JdbcType.DATE)
			}
	) 
	public  List<SelfCheckExprt> queryDataCheck(@Param(value="year")String year);
	
	/**
	 * 按照序号在自检信息中查询对应文件路径
	 * @param seqs 序号，多个以“,”分隔
	 * @return 文件名称（含相对路径）列表
	 */
	@SelectProvider(type = DataCheckProvider.class, method = "queryFileNamesByQuatr" )
	@Results(value = { 
			@Result(property = "fileName", column = "FILE_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR)
			}
	) 
	public List<String> queryFileNamesByQuatr(@Param(value="quatr")String quatr);
	
	
}
