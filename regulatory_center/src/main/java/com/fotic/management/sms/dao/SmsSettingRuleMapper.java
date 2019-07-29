package com.fotic.management.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.sms.entity.SmsSettingRule;
import com.fotic.management.sms.providers.SmsSettingRuleProvier;

import tk.mybatis.mapper.common.Mapper;

public interface SmsSettingRuleMapper extends Mapper<SmsSettingRule>{

	@InsertProvider(type=SmsSettingRuleProvier.class,method="addSmsSetting")
	public void addSmsSetting(@Param(value="list")List<SmsSettingRule> smsSettingRuleList);
	
	
	@Select("select * from RHZX_SMS_SETTING_RULE where module_id = #{moduleId }")
	public List<SmsSettingRule> querySmsSettingRules(Integer moduleId);
	
	@Delete("delete from RHZX_SMS_SETTING_RULE where module_id = #{moduleId }")
	public void deleteEntity(Integer moduleId);
}
