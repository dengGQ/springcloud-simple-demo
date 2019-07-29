package com.fotic.management.creditinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.creditinfo.entity.RhzxCsvLoadLog;

import tk.mybatis.mapper.common.Mapper;

public interface RhzxCsvLoadLogMapper extends Mapper<RhzxCsvLoadLog>{
	
	@Select("SELECT r.*,t.name FROM RHZX_LOG_CSV_LOAD r left join T_LOGINUSER t on r.user_code=t.usercode ${condition}")
	public List<RhzxCsvLoadLog> queryRhzxCsvLoadLog(@Param("condition")String condition);
}
