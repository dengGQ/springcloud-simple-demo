package com.fotic.management.creditinfo.service;

import java.util.List;
import java.util.Map;

import com.fotic.management.creditinfo.entity.ActualRepay;

public interface IActualRepayService {
	List<ActualRepay> findAll();
	int update(ActualRepay entity);
	List<ActualRepay> queryListByParams(Map<String, Object> jsonToMap);
}
