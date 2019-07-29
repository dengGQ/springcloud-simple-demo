package com.fotic.management.sms.job;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fotic.management.sms.service.impl.SmsServiceImpl;
import com.fotic.sms.job.SmsSendJob;

/*
* @Description: 定期向短信平台推送数据
* @author dgq 
* @date 2018年4月25日
*/
@Service
public class PushOverdueDataJob implements Job, Serializable{
	private static Logger logger = LoggerFactory.getLogger(PushOverdueDataJob.class);

	private static final long serialVersionUID = -6605766126594260962L;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("数据推送：-------------------------SmsJob");
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
			SmsServiceImpl ss = (SmsServiceImpl)applicationContext.getBean("smsServiceImpl");//获取spring bean实例 
			ss.sendSms();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
