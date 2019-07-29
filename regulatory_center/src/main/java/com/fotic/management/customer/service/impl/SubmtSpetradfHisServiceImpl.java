package com.fotic.management.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.customer.dao.SubmtSpetradfHisMapper;
import com.fotic.management.customer.entity.SubmtSpetradfHis;
import com.fotic.management.customer.service.ISubmtSpetradfHisService;

@Service
public class SubmtSpetradfHisServiceImpl implements ISubmtSpetradfHisService {

	@Autowired
	private SubmtSpetradfHisMapper submtSpetradfHisMapper;
	
	@Override
	public List<SubmtSpetradfHis> findList(SubmtSpetradfHis submtSpetradfHis) {
		return submtSpetradfHisMapper.select(submtSpetradfHis);
	}

}
