package com.fotic.management.customer.dao;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.fotic.management.customer.entity.SubmitEmp;
import com.fotic.management.customer.providers.SubmitEmpProvider;

import tk.mybatis.mapper.common.Mapper;
public interface SubmitEmpMapper extends Mapper<SubmitEmp>{
	
	@InsertProvider(type = SubmitEmpProvider.class, method = "execCsvFileSql")  
	 public void insertAll(@Param("list") List<SubmitEmp> list);  
	
}
