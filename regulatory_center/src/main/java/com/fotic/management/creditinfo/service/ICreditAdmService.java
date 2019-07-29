package com.fotic.management.creditinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fotic.management.creditinfo.entity.RhzxSubmtPerJDTrade;
import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;

public interface ICreditAdmService {
	List<RhzxSubmtPerTrade> findAll();

	List<RhzxSubmtPerTrade> findList(String projectId, String coOrgNo, String IOUNo, String credNum, String checkResult,
			String startDate, String endDate,String dataSrc,String curtermspastdue);
	//信贷信息管理（厂检）查询 - 暂时保留
	List<RhzxSubmtPerJDTrade> findFactoryList(String projectId, String coOrgNo, String IOUNo, String credNum, String checkResult,
			String startDate, String endDate,String dataSrc);

	List<Map<String, Object>> getActualAndRepayInfo(String conNo);

	void setEditInfo(HttpServletRequest request, List<Map<String, Object>> editInfo);
	
	List<RhzxSubmtPerTrade> findCsvDatas(String dataSouce);
	
}
