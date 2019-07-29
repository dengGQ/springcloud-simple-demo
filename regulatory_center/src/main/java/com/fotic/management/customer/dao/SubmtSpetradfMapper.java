package com.fotic.management.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.fotic.management.customer.entity.SubmtSpetrad;
import com.fotic.management.customer.providers.SubmtSpetradProvider;

import tk.mybatis.mapper.common.Mapper;

public interface SubmtSpetradfMapper extends Mapper<SubmtSpetrad>{
	
	 @InsertProvider(type = SubmtSpetradProvider.class, method = "execCsvFileSql")  
	 public void insertAll(@Param("list") List<SubmtSpetrad> list);  
}
