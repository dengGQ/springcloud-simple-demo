package com.fotic.management.unreported.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.unreported.dao.UnreportedMapper;
import com.fotic.management.unreported.entity.ViewUnreported;
import com.fotic.management.unreported.service.IUnreportedService;

@Service
public class UnreportedServiceImpl implements IUnreportedService {

	/**
	 * 应报未报mapper
	 */
	@Autowired
	private UnreportedMapper unreportedMapper;
	
	@Override
	public  List<ViewUnreported> findList(String startMonth, String endMonth,String dataSrc) {
		return unreportedMapper.findList(startMonth, endMonth,dataSrc);
	}
	
	@Override
	public List<Object> findObjectList(String startMonth, String endMonth, String dataSrc) {
		Map<String,List<?>> map = new LinkedHashMap<String,List<?>>();
		List<ViewUnreported> findList = unreportedMapper.findList(startMonth, endMonth, dataSrc);
		map.put("应报未报数据信息", findList);
		List<Object> list = new ArrayList<>();
		list.add(map);
		return list;
	}
	
}
