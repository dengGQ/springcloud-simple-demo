package com.fotic.sms.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.fotic.sms.SmsBody;

/*
* @Description: public class SmsBodyDetailProvider{ }
* @author dgq 
* @date 2018年4月18日
*/
public class SmsBodyProvider {
	
	public String batchInsertSmsBody(Map<String, Object> map){
		@SuppressWarnings("unchecked")
		List<SmsBody> smsBodys = (List<SmsBody>)map.get("list");
		System.out.println(smsBodys.get(0).getAccount());
		StringBuilder sb = new StringBuilder("insert into SMS_BODY(id,"
																+ "sms_module_type, "
																+ "phone, "
																+ "sms_content, "
																+ "send_date, "
																+ "create_time, "
																+ "uuid, "
																+ "platform, "
																+ "call_back_url, "
																+ "repeat_send_count, "
																+ "repeat_role,"
																+ "account, "
																+ "co_org_code, "
																+ "cust_name, "
																+ "certno, "
																+ "proj_id ) (select APP_SEQUENCE.NEXTVAL, cd.* from (");
		MessageFormat mf = new MessageFormat("select #'{'list[{0}].smsModuleType'}' as sms_module_type,"
												  + "#'{'list[{0}].phone'}' as b,"
												  + "#'{'list[{0}].smsContent'}' as c, "
												  + "#'{'list[{0}].sendDate'}' as d,"
												  + "current_date as e, "
												  + "#'{'list[{0}].uuid'}' as f, "
												  + "#'{'list[{0}].platform'}' as g, "
												  + "#'{'list[{0}].callBackUrl'}' as h, "
												  + "#'{'list[{0}].repeatSendCount'}' as i, "
												  + "#'{'list[{0}].repeatRole'}' as j ,"
												  + "#'{'list[{0}].account'}' as k ,"
												  + "#'{'list[{0}].coOrgCode'}' as l ,"
												  + "#'{'list[{0}].custName'}' as m ,"
												  + "#'{'list[{0}].credNo'}' as n ,"
												  + "#'{'list[{0}].projId'}' as o from dual");
		for (int i = 0; i < smsBodys.size(); i++) {
			//System.out.println(smsBodys.get(0).getAccount());
            sb.append(mf.format(new Object[]{i}));
            if(i < smsBodys.size()-1){
            	sb.append(" union all ");
            }
		}
		return sb.append(") cd)").toString();
	}
}
