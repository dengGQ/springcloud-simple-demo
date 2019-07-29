package com.fotic.management.trade.dao;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.fotic.management.trade.entity.Contract;
import com.fotic.management.trade.entity.RhzxRebuy;
import com.fotic.management.trade.providers.RhzxContractProvider;
import com.fotic.management.trade.providers.RhzxRebuyProvider;

import tk.mybatis.mapper.common.Mapper;
public interface ContractMapper extends Mapper<Contract>{
	
	@SelectProvider(type = RhzxContractProvider.class, method = "findContractList")
	public List<Contract> findContractList(String conNo,String iouNo,String dataSrc);
	
	@SelectProvider(type = RhzxRebuyProvider.class, method = "findList")
	public List<RhzxRebuy> findRhzxRebuyList(String conNo);
	
}
