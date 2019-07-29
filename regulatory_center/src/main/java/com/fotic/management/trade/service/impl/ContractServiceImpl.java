package com.fotic.management.trade.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.trade.dao.ContractMapper;
import com.fotic.management.trade.entity.Contract;
import com.fotic.management.trade.entity.RhzxRebuy;
import com.fotic.management.trade.service.ContractService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class ContractServiceImpl implements ContractService {
	@Autowired
	private ContractMapper contractMapper;

	@Override
	public List<Contract> findContractList(String conNo,String iouNo,String dataSrc) {
		return contractMapper.findContractList(conNo,iouNo,dataSrc);
	}

	@Override
	public List<Contract> findDetailsContract(String conNo) {
		Example example = new Example(Contract.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("con_no = '"+conNo+"'");
		return contractMapper.selectByExample(example);
	}

	@Override
	public List<RhzxRebuy> findRebuyList(String conNo) {
		return contractMapper.findRhzxRebuyList(conNo);
	}

	@Override
	public int deleteByConNos(String conNo) {
		Example example = new Example(Contract.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("con_no in ("+conNo+")");
		return contractMapper.deleteByExample(example);
	}
}
