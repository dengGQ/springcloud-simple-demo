package com.fotic.management.reported.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.reported.dao.SubmitPerTradeMapper;
import com.fotic.management.reported.entity.SubmitPerTrade;
import com.fotic.management.reported.service.ISubmitPerTradeService;

@Service
public class SubmitPerTradeServiceImpl implements ISubmitPerTradeService {

	@Autowired
	private SubmitPerTradeMapper submitPerTradeMapper;
	@Override
	public List<SubmitPerTrade> findList(Integer dataSrc) {
		return submitPerTradeMapper.findList(dataSrc);
	}
	
	@Override
	public List<SubmitPerTrade> findAll(Integer dataSrc) {
		return submitPerTradeMapper.findAll(dataSrc);
	}
	@Override
	public List<SubmitPerTrade> findByBussDate(Integer dataSrc, String bussDate) {
		return submitPerTradeMapper.findByBussDate(dataSrc, bussDate);
	}
	@Override
	public List<SubmitPerTrade> findListAll(String startDate, String endDate,
			Integer dataSrc,String coOrgName) {
		// TODO Auto-generated method stub
		return submitPerTradeMapper.findListAll(startDate,endDate, dataSrc,coOrgName);
	}
}
