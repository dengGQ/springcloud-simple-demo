package com.fotic.management.unreported.service;

import java.util.List;

import com.fotic.management.unreported.entity.ViewUnreported;

/**
 * 应报未报service
 * @author zhaoqh
 */
public interface IUnreportedService {
	
	/**
	 * 应报 未报数据查询
	 * @param startMonth	起始年月
	 * @param endMonth		结束年月
	 * @param dataSrc		数据来源
	 * @return
	 */
	 List<ViewUnreported> findList(String startMonth, String endMonth ,String dataSrc);
	 
	 List<Object> findObjectList(String startMonth, String endMonth ,String dataSrc);
}
