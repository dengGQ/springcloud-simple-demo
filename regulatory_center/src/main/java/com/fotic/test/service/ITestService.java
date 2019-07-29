package com.fotic.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import com.fotic.test.model.Test;

public interface ITestService{
	
	int save(Test entity);
	Test get(long id);
	//PageResult searchListByPage(Page page);
	List<Test> findAll();
	
	String getNameById(long id);
	Map<String, String> getName(long id);
}
