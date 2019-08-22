package com.dgq.crs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dgq.crs.entity.DataEditLog;

import tk.mybatis.mapper.common.Mapper;

/*
* @Description: 数据修改日志Mapper
* @author dgq 
* @date 2019年3月25日
*/
public interface DataEditLogMapper extends Mapper<DataEditLog>{
	
	@Select("select d.* from reg.data_edit_log d ${condition}")
	List<DataEditLog> listByCondition(@Param("condition") String condition);
}
