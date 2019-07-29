package com.fotic.management.sms.providers;

import org.apache.ibatis.jdbc.SQL;

import com.fotic.management.sms.entity.SmsPrepareSendRecord;

public class SmsPrepareSendRecordProvider {

	public String saveSmsPrepareSendRecord(SmsPrepareSendRecord smsPrepareSendRecord){
		String sql = new SQL(){
			{
				INSERT_INTO("RHZX_SMS_PREPARE_SEND_RECORD");
				VALUES("id","#{id,javaType=Integer,jdbcType=INTEGER}");
				VALUES("CO_ORG_CODE","#{coOrgCode,javaType=string,jdbcType=VARCHAR}");
				VALUES("PROJ_ID","#{projId,javaType=string,jdbcType=VARCHAR}");
				VALUES("CUST_CODE","#{custCode,javaType=string,jdbcType=VARCHAR}");
				VALUES("CUST_NAME","#{custName,javaType=string,jdbcType=VARCHAR}");
				VALUES("PROD_CODE","#{prodCode,javaType=string,jdbcType=VARCHAR}");
				VALUES("SMS_SEND_CONTENT","#{smsSendContent,javaType=string,jdbcType=VARCHAR}");
				VALUES("CRED_TYPE","#{credType,javaType=string,jdbcType=VARCHAR}");
				VALUES("CRED_NO","#{credNo,javaType=string,jdbcType=VARCHAR}");
				VALUES("PHONE","#{phone,javaType=string,jdbcType=VARCHAR}");
				VALUES("IOU_NO","#{iouNo,javaType=string,jdbcType=VARCHAR}");
				VALUES("MODULE_ID","#{moduleId,javaType=integer,jdbcType=INTEGER}");
				VALUES("INSERT_DATE","sysdate");
			}
		}.toString();
		return sql;
	}
}
