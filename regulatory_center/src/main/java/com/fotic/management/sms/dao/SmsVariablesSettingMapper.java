package com.fotic.management.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.fotic.management.sms.entity.SmsVariablesSetting;

import tk.mybatis.mapper.common.Mapper;

public interface SmsVariablesSettingMapper extends Mapper<SmsVariablesSetting>{
	
	@Select("SELECT * FROM RHZX_SMS_VARIABLES_SETTING ORDER BY ID")
	public List<SmsVariablesSetting> querySmsVariablesSettingList();

}
