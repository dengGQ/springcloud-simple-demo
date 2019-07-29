package com.fotic.management.customer.dao;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.fotic.management.customer.entity.SubmtPerson;
import com.fotic.management.customer.providers.SubmtPersonProvider;

import tk.mybatis.mapper.common.Mapper;
public interface SubmtPersonMapper extends Mapper<SubmtPerson>{
	
	@InsertProvider(type = SubmtPersonProvider.class, method = "execCsvFileSql")  
	 public void insertAll(@Param("list") List<SubmtPerson> list);  
}