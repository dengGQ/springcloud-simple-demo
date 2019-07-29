package com.fotic.management.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.fotic.management.sms.entity.SmsModule;
import com.fotic.management.sms.providers.SmsModuleProvider;

import tk.mybatis.mapper.common.Mapper;

public interface SmsModuleMapper extends Mapper<SmsModule>{

	@Select("select * from RHZX_SMS_MODULE order by create_time desc")
	public List<SmsModule> queryModuleList();
	
	//@Select("select * from RHZX_SMS_MODULE where TO_CHAR(create_time, 'yyyy-mm-dd') = #{createTime} order by create_time desc")
	@SelectProvider(type=SmsModuleProvider.class,method="queryModuleListByCondition")
	public List<SmsModule> queryModuleListByCondition(String createTime,String smsModuleType);
	
	@Update("update RHZX_SMS_MODULE set status = #{status} where id = #{moduleId}")
	public int updateStatus(@Param(value="moduleId")Integer moduleId,@Param(value="status")String status);
	
	@InsertProvider(type=SmsModuleProvider.class,method="addSmsModule")
	@SelectKey(keyProperty="id", before=true, statement="SELECT APP_SEQUENCE.NEXTVAL FROM DUAL", resultType =java.lang.Integer.class)
	public Integer addSmsModule(SmsModule smsModule);
	
	//@Select("select * from RHZX_SMS_MODULE where id = #{id}")
	@SelectProvider(type=SmsModuleProvider.class,method="getEntityById")
	public SmsModule getEntityById(Integer id);
	
	@Update("update RHZX_SMS_MODULE set sms_module_type=#{smsModuleType},sms_module_name=#{smsModuleName}, sms_module_content=#{smsModuleContent}, update_time=current_date where id = #{id}")
	public void editModule(SmsModule smsModule);
}
