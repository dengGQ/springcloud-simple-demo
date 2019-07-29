package com.fotic.test.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.test.dao.TestMapper;
import com.fotic.test.model.Test;
import com.fotic.test.service.ITestService;

@Service
public class TestServiceImpl implements ITestService{
	
	@Autowired
	private TestMapper  testMapper;
	

	
	@Override
	public int save(Test entity) {
		return testMapper.insert(entity);
	}

	@Override
	public Test get(long id) {
		// TODO Auto-generated method stub
		return testMapper.getById(id);
	}



	@Override
	public List<Test> findAll() {
		return testMapper.selectAll();
	}

	@Override
	public String getNameById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, String> getName(long id) {
		// TODO Auto-generated method stub
		return testMapper.getName(id);
	}

}
