package com.fotic.management.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.customer.dao.SubmtPersonHisMapper;
import com.fotic.management.customer.entity.SubmtPersonHis;
import com.fotic.management.customer.service.ISubmtPersonHisService;

@Service
public class SubmtPersonHisServiceImpl implements ISubmtPersonHisService {
	@Autowired
	private SubmtPersonHisMapper submtPersonHisMapper;

	@Override
	public List<SubmtPersonHis> findList(SubmtPersonHis submtPersonHis) {
		return submtPersonHisMapper.select(submtPersonHis);
	}
}
