package com.fotic.management.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.customer.dao.SubmitAddressHisMapper;
import com.fotic.management.customer.entity.SubmitAddressHis;
import com.fotic.management.customer.service.ISubmitAddressHisService;

@Service
public class SubmitAddressHisServiceImpl implements ISubmitAddressHisService {

	@Autowired
	private SubmitAddressHisMapper submitAddressHisMapper;
	
	@Override
	public List<SubmitAddressHis> findList(SubmitAddressHis submitAddressHis) {
		return submitAddressHisMapper.select(submitAddressHis);
	}

}
