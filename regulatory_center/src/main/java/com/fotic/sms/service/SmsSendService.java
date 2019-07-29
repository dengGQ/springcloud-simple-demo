package com.fotic.sms.service;

import java.util.List;

import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.SmsBody;

/*
* @Description: public class SmsBodyService{ }
* @author dgq 
* @date 2018年4月18日
*/
public interface SmsSendService {
	
	void sendSms(List<SmsBody> sbs) throws Exception;
	
	List<SmsBody> querySmsBody();
	
	List<SmsBody> querySmsBodys(String account,String sendDate);
	
	List<SmsSendDetail> querySendDetail(String iouNo,String firstday,String lastday);
}
