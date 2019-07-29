package com.fotic.management.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.fotic.management.customer.entity.SubmitAddress;
import com.fotic.management.customer.providers.SubmitAddressProvider;

import tk.mybatis.mapper.common.Mapper;

public interface SubmitAddressMapper extends Mapper<SubmitAddress>{
	
	@InsertProvider(type = SubmitAddressProvider.class, method = "execCsvFileSql")  
	 public void insertAll(@Param("list") List<SubmitAddress> list);  
}
