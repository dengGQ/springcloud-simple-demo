package com.fotic.sms.controller;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.sms.SmsBody;
import com.fotic.sms.service.SmsSendService;

/*
* @Description: public class SmsBodyController{ }
* @author dgq 
* @date 2018年4月18日
*/
@Controller
@RequestMapping("smsServer")
public class SmsServerController {
	
	private static Logger logger = LoggerFactory.getLogger(SmsServerController.class);
	
	@Autowired
	private SmsSendService smsSendService;
	
	/**
	 * 接收来自业务平台的待发短信
	 * @param sbs
	 * @return
	 */
	@RequestMapping("sendSms")
	@ResponseBody
	public boolean sendSms(@RequestBody List<SmsBody> sbs){
		try {
			List<List<SmsBody>> rsList = ListUtils.partition(sbs, 1000);
			for (int i = 0; i < rsList.size(); i++) {	
				smsSendService.sendSms(rsList.get(i));
				System.out.println(i+":数据持久化成功，持久化数据数目："+rsList.size());
				
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("短信发送发生异常.....................");
			return false;
		}
	}
}
