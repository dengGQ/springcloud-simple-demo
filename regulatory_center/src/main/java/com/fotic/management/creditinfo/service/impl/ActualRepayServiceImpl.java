package com.fotic.management.creditinfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.creditinfo.dao.ActualRepayMapper;
import com.fotic.management.creditinfo.entity.ActualRepay;
import com.fotic.management.creditinfo.service.IActualRepayService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ActualRepayServiceImpl implements IActualRepayService {
	@Autowired
	private ActualRepayMapper actualRepayMapper;

	@Override
	public List<ActualRepay> findAll() {
		return actualRepayMapper.selectAll();
	}

	@Override
	public int update(ActualRepay entity) {
		Example example = new Example(ActualRepay.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("id", entity.getId());//根据id更新数据
		return actualRepayMapper.updateByExampleSelective(entity, example);
	}

	@Override
	public List<ActualRepay> queryListByParams(Map<String, Object> jsonToMap) {
		Example example = new Example(ActualRepay.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andEqualTo("id", jsonToMap.get("actualId"));
		return actualRepayMapper.selectByExample(example);
	}

	
}
