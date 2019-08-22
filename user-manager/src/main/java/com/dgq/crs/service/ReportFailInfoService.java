package com.dgq.crs.service;
/*
* @Description: 报送失败信息service
* @author dgq 
* @date 2019年6月21日
*/

import java.util.Date;
import java.util.List;

import com.dgq.crs.entity.ReportFailInfo;
import com.dgq.crs.xml.bean.CncrsRes;

public interface ReportFailInfoService {
	
	/**
	 * xml转实体
	 * @param cncrs
	 * @param user
	 * @param dataYear
	 * @return
	 * @throws Exception
	 */
	List<ReportFailInfo> xmlConvertEntity(CncrsRes cncrs, Date dataYear) throws Exception;
}
