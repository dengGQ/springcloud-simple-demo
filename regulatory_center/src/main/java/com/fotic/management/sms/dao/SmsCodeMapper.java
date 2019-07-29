package com.fotic.management.sms.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.sms.entity.SmsCode;

import tk.mybatis.mapper.common.Mapper;

public interface SmsCodeMapper extends Mapper<SmsCode>{

	@Select("select * from rhzx_sms_code where sms_code = #{smsCode}")
	public SmsCode querySmsCodeDetailByCode(@Param(value="smsCode") String smsCode);
}
