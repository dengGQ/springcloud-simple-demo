package com.fotic.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.SmsBody;
import com.fotic.sms.dao.SmsBodyMapper;
import com.fotic.sms.service.SmsSendService;

/*
* @Description: public class SmsBodyServiceImpl{ }
* @author dgq 
* @date 2018年4月18日
*/

@Service
public class SmsSendServiceImpl implements SmsSendService{
	
	@Autowired
	private SmsBodyMapper smsBodyMapper;
	
	@Override
	public void sendSms(List<SmsBody> sbs) throws Exception{
		smsBodyMapper.batchInsertEntity(sbs);
		
	}

	@Override
	public List<SmsBody> querySmsBody() {
		List<SmsBody> list = smsBodyMapper.querySmsBody();
		return list;
	}

	@Override
	public List<SmsSendDetail> querySendDetail(String iouNo,String firstday,String lastday) {
		List<SmsSendDetail> list = smsBodyMapper.querySendDetail(iouNo,firstday,lastday);
		return list;
		
	}

	@Override
	public List<SmsBody> querySmsBodys(String account,String sendDate) {
		// TODO Auto-generated method stub
		return smsBodyMapper.querySmsBodys(account,sendDate);
	}

}
