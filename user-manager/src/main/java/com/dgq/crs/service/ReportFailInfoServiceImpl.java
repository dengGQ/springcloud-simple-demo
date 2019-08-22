package com.dgq.crs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dgq.crs.entity.ReportFailInfo;
import com.dgq.crs.xml.bean.CncrsRes;

public class ReportFailInfoServiceImpl implements ReportFailInfoService{
	
	@Override
	public List<ReportFailInfo> xmlConvertEntity(CncrsRes cncrs, Date dataYear) throws Exception {
		List<ReportFailInfo> list = new ArrayList<>();
		String resultType = cncrs.getResultGroup().getResultType();
		String messageRefId = cncrs.getMessageHeader().getMessageRefId();
		cncrs.getResultGroup().getAccountErrReports().parallelStream().forEach(item->{
			ReportFailInfo reportFailInfo = new ReportFailInfo(resultType, item.getDocRefId(), 
					item.getRErrCode(), LocalDateTime.now(), "1", 
					dataYear, messageRefId);
			list.add(reportFailInfo);
		});;
		return list;
	}
}
