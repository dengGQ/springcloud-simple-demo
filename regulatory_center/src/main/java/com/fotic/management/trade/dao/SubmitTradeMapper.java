package com.fotic.management.trade.dao;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.fotic.management.trade.entity.SubmitTrade;
import com.fotic.management.trade.providers.SubmitTradeProvider;

import tk.mybatis.mapper.common.Mapper;
public interface SubmitTradeMapper extends Mapper<SubmitTrade>{
	
	@InsertProvider(type = SubmitTradeProvider.class, method = "execCsvFileSql")  
	public void insertAll(@Param("list") List<SubmitTrade> list);  
	
	@SelectProvider(type = SubmitTradeProvider.class, method = "queryList")
	public List<SubmitTrade> findList(String certno,String dataStatus, String dataSrc);
}
