package com.fotic.management.customer.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fotic.management.customer.dao.SubmitEmpHisMapper;
import com.fotic.management.customer.entity.SubmitEmpHis;
import com.fotic.management.customer.service.SubmitEmpHisService;
@Service
public class SubmitEmpHisServiceImpl implements SubmitEmpHisService{
	@Autowired
	private SubmitEmpHisMapper submitEmpHisMapper;

	@Override
	public List<SubmitEmpHis> findList(SubmitEmpHis submitEmpHis) {
		return submitEmpHisMapper.select(submitEmpHis);
	}
    
}
