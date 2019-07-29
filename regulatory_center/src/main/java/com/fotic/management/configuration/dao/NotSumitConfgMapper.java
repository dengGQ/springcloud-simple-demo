package com.fotic.management.configuration.dao;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.fotic.management.configuration.entity.NotSumitConfg;
import com.fotic.management.configuration.providers.NotSumitProvider;

import tk.mybatis.mapper.common.Mapper;
public interface NotSumitConfgMapper extends Mapper<NotSumitConfg>{
   
	 @InsertProvider(type = NotSumitProvider.class, method = "insertBatchDatas")  
	 public void insertAll(@Param("list") List<NotSumitConfg> list); 
}