package com.fotic.management.configuration.dao;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.fotic.management.configuration.entity.NotSumitConfgInfo;
import com.fotic.management.configuration.providers.NotSumitProvider;

import tk.mybatis.mapper.common.Mapper;
public interface NotSumitConfgInfoMapper extends Mapper<NotSumitConfgInfo>{
	
	@SelectProvider(type = NotSumitProvider.class, method = "queryList")
	public List<NotSumitConfgInfo> queryList(String project, String prod, String org, String conno, String dataSrc); 

}