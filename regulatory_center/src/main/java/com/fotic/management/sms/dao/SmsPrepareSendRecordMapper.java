package com.fotic.management.sms.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

import com.fotic.management.sms.entity.SmsPrepareSendRecord;
import com.fotic.management.sms.providers.SmsPrepareSendRecordProvider;

import tk.mybatis.mapper.common.Mapper;

public interface SmsPrepareSendRecordMapper extends Mapper<SmsPrepareSendRecord>{

	@InsertProvider(type=SmsPrepareSendRecordProvider.class,method="saveSmsPrepareSendRecord")
	@SelectKey(keyProperty="id", before=true, statement="SELECT APP_SEQUENCE.NEXTVAL FROM DUAL", resultType =java.lang.Integer.class)
	public int saveSmsPrepareSendRecord(SmsPrepareSendRecord smsPrepareSendRecord);
}
