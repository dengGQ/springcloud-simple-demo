package com.fotic.sms.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fotic.common.httpclient.HttpResult;
import com.fotic.common.httpclient.service.HttpClientService;
import com.fotic.common.util.SmsUtil;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.SmsBody;
import com.fotic.sms.dao.SmsBodyMapper;
import com.fotic.sms.service.SmsSendService;

/*
* @Description: 发送短信job
* @author dgq 
* @date 2018年4月27日
*/
@DisallowConcurrentExecution //禁止同时执行同一个job
public class SmsSendJob implements Job,Serializable{
	
	private static Logger logger = LoggerFactory.getLogger(SmsSendJob.class);
	
	private static final long serialVersionUID = 1L;
	
	private SmsSendService sendService;
	private HttpClientService httpClientService;
	private SmsBodyMapper mapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("-------------------------SmsJob");
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
			this.sendService = applicationContext.getBean(SmsSendService.class);
			this.httpClientService = applicationContext.getBean(HttpClientService.class);
			this.mapper = applicationContext.getBean(SmsBodyMapper.class);
			job();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void job(){
		try {
			List<SmsBody> smsBodys = sendService.querySmsBody();
			logger.info("---------------------SmsSendJob:"+smsBodys.size());
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			String ym ="";
			if (month == 1 || month == 2 || month == 3 || month == 4||month == 5 || month == 6||month == 7 || month == 8 ||month == 9) {
				ym = year + "-0"+month;
			}else{
				ym = year+"-"+month;
			}
			
			System.out.println(ym);
			Iterator<SmsBody> iterator = smsBodys.iterator();
			while(iterator.hasNext()) {
				SmsBody smsBody = iterator.next();
				String account = smsBody.getAccount();
				List<SmsBody> list = sendService.querySmsBodys(account,ym);
//				SmsBody smsBody = smsBodys;
				String strID = smsBody.getId()+"";
				if (list.size() > 0) {
					iterator.remove();
					mapper.updateEntitys(strID);
				}
			}
			while (iterator.hasNext()) {
				smsBodys.add(iterator.next());
				
			}
			for (SmsBody smsBody : smsBodys) {			
				String smsCode = SmsUtil.SmsSend(smsBody.getPhone(), smsBody.getSmsContent(),smsBody.getSmsModuleType());
				logger.info("---------------------SmsSendJob->SmsUtil.uuid:"+smsBody.getUuid()+", SmsUtil.SmsSend:"+smsCode);
				Integer repeatSendCount = smsBody.getRepeatSendCount();
				logger.info("---------------------repeatSendCount:"+repeatSendCount);
				while(repeatSendCount > 0 && smsBody.getRepeatRole().indexOf(smsCode) != -1){
					smsCode = SmsUtil.SmsSend(smsBody.getPhone(), smsBody.getSmsContent(),smsBody.getSmsModuleType());
					repeatSendCount--;
				}
				smsBody.setStatus("1"); //已发送
				smsBody.setSmsCode(smsCode); //标识是否发送成功及失败原因
				smsBody.setRepeatSendCount(smsBody.getRepeatSendCount()-repeatSendCount);
				
				mapper.updateEntity(smsBody);
			}
			
			Map<String, List<SmsBody>> map = convertData(smsBodys);
			for (String key : map.keySet()) {
				JSONArray jsonArray = (JSONArray)JSON.toJSON(map.get(key));
				HttpResult result = httpClientService.doPostJson(key, jsonArray.toString());
				logger.info("---------------------SmsSendJob.callBack:"+key+", result:"+result.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<SmsBody>> convertData(List<SmsBody> smsBodys){
		
		Map<String, List<SmsBody>> map = new HashMap<>();
		for (SmsBody smsBody : smsBodys) {
			List<SmsBody> list = map.get(smsBody.getCallBackUrl());
			if(Objects.nonNull(list)){
				list.add(smsBody);
			}else{
				List<SmsBody> smsbodys = new ArrayList<SmsBody>();
				smsbodys.add(smsBody);
				map.put(smsBody.getCallBackUrl(), smsbodys);
			}
		}
		
		return map;
	}
}
