package com.fotic.management.sms.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


import com.fotic.management.sms.entity.SmsSettingRule;

public class SmsSettingRuleProvier {

	public String addSmsSetting(Map<String, Object> map){
		@SuppressWarnings("unchecked")
		List<SmsSettingRule> smsSettingRuleList = (List<SmsSettingRule>)map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("insert into RHZX_SMS_SETTING_RULE(id, MODULE_ID,CO_ORG_CODE,PROJ_ID, PROD_ID,CREATE_TIME,UPDATE_TIME) (select APP_SEQUENCE.NEXTVAL, cd.* from (");
		MessageFormat mf = new MessageFormat("select #'{'list[{0}].moduleId'}' as a,#'{'list[{0}].coOrgCode'}' as b,#'{'list[{0}].projId'}' as c, #'{'list[{0}].prodId'}' as d,current_date as e,current_date as f from dual");
		for (int i = 0; i < smsSettingRuleList.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if(i < smsSettingRuleList.size()-1){
            	sb.append(" union all ");
            }
		}
		return sb.append(") cd)").toString();
	}
}
