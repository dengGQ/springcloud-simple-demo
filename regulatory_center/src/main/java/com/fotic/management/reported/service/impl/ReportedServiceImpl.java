package com.fotic.management.reported.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.auth.entity.LoginUser;
import com.fotic.common.enums.CommonEnum.StatusEnum;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.DsJobUtil;
import com.fotic.common.util.PropertiesUtil;
import com.fotic.common.util.SessionUtil;
import com.fotic.management.reported.dao.RhzxLogSubmittedMapper;
import com.fotic.management.reported.dao.SubmitPerTradeMapper;
import com.fotic.management.reported.entity.RhzxLogSubmitted;
import com.fotic.management.reported.entity.SubmitPerTrade;
import com.fotic.management.reported.service.IReportedService;
import com.fotic.management.reported.service.ISubmitPerTradeService;

@Service
public class ReportedServiceImpl implements IReportedService {
	
	/**
	 * 交易结果表service
	 */
	@Autowired
	private ISubmitPerTradeService submitPerTradeService;
	
	@Autowired
	private RhzxLogSubmittedMapper rhzxLogSubmittedMapper;
	@Autowired
	private SubmitPerTradeMapper submitPerTradeMapper ;
	
	@Override
	public List<SubmitPerTrade> findList(Integer dataSrc) {
		return submitPerTradeService.findList(dataSrc);
	}

	
	@Override
	public Map<String, Object> reporting(String insertDates,HttpServletRequest request) {
		LoginUser user = SessionUtil.getUserFromHttpRequest(request);
		Map<String, Object> retMap = new HashMap<>();
		//调用dsjob返回
		boolean jobFlag = false;
		//组装dsJob参数
		String jobName = null;
		String paramKey = PropertiesUtil.get("SUBMITTED_JOB_PARAM");
		
		String[] paramsSplit = insertDates.split(":");
		if("1".equals(paramsSplit[2].toString())) {
			jobName = PropertiesUtil.get("SUBMITTED_JOB_NAME_XD");
		}else if("2".equals(paramsSplit[2].toString())) {
			jobName = PropertiesUtil.get("SUBMITTED_JOB_NAME_CSV");
		}
		 
		String paramVal = insertDates;
		//生成dsJob脚本
		String[] cmds = DsJobUtil.processCMDS(jobName, paramKey, paramVal);
		//调用dsJob
		String retStr = DsJobUtil.exec(cmds);
		
        retStr = retStr.replaceAll(" ", "").toLowerCase();
        //获取执行结果 ,status code, 1:成功, 2:成功有警告, 其他:错误
        jobFlag = retStr.contains("statuscode=1") || retStr.contains("statuscode=2") ;
        
		//调用dsJob结果
		if(jobFlag){
			retMap.put("status", StatusEnum.SUCCESS.getStatus());
			retMap.put("msg", "上报成功");
		}else{
			retMap.put("status", StatusEnum.FAIL.getStatus());
			retMap.put("msg", "DsJob任务执行失败");
		}
		//插入上报日志表
		insetLog(insertDates,user.getUsercode());
		return retMap;
	}


	/**
	 * 记录上报日志
	 * @param insertDates
	 * @param userCode
	 */
	private void insetLog(String insertDates,String userCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String bussDate = insertDates.substring(0,8);
		//String dataScr = insertDates.substring(9,10);
		
		String[] dateScr = insertDates.split(":");
		String dateScrs = dateScr[2];
				
		try {
			Date date = sdf1.parse(bussDate);
			List<SubmitPerTrade>  list = submitPerTradeService.findByBussDate(Integer.parseInt(dateScrs), bussDate);
			for (SubmitPerTrade sp : list) {
				Date date_seq = DateUtils.getCurrentDate();
				String str_seq = sdf.format(date_seq);
				RhzxLogSubmitted rls = new RhzxLogSubmitted();
				rls.setSeq(str_seq);//暂定序号
				rls.setBussDate(date);//业务日期
				rls.setDataCont(sp.getAllNum());//总条数
				rls.setDataSrc(dateScrs);//数据来源
				rls.setOpDate(DateUtils.getCurrentDate());//操作日期
				rls.setUserCode(userCode);//操作人
				rls.setDataRigthCont(sp.getSuccessNum());//成功条数
				rls.setDataErrCont(sp.getFailNum());//失败条数
				rhzxLogSubmittedMapper.insertSelective(rls);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<SubmitPerTrade> findListAll(String startDate, String endDate,
			Integer dataSrc,String coOrgName) {
		// TODO Auto-generated method stub
		return submitPerTradeService.findListAll(startDate,endDate,dataSrc,coOrgName);
	}


	@Override
	public List<SubmitPerTrade> findListOrg(Integer dataSrc) {
		// TODO Auto-generated method stub
		return submitPerTradeMapper.findListOrg(dataSrc);
	}
}
