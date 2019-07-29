package com.fotic.management.trade.service;
import java.util.List;
import java.util.Map;

import com.fotic.management.trade.entity.Contract;
import com.fotic.management.trade.entity.RhzxRebuy;
public interface ContractService{
	
	/**
	 * 查询合同基本信息
	 * @param conNo
	 * @return
	 */
	public List<Contract> findContractList(String conNo,String iouNo,String dataSrc);
	
	/**
	 * 根据合同号获取合同详细信息
	 * @param conNo
	 * @return
	 */
	public List<Contract> findDetailsContract(String conNo);
	
	/**
	 * 查询特殊交易信息
	 * @return
	 */
	public List<RhzxRebuy> findRebuyList(String conNo);
	
	/**
	 * 根据合同号批量删除数据
	 * @param conNo
	 * @return
	 */
	public int deleteByConNos(String conNo);
}
