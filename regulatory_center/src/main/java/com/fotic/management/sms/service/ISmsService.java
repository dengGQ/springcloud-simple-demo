package com.fotic.management.sms.service;

import java.util.List;

import com.fotic.management.sms.entity.SmsCode;
import com.fotic.management.sms.entity.SmsModule;
import com.fotic.management.sms.entity.SmsPrepareSendRecord;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.management.sms.entity.SmsSettingRule;
import com.fotic.management.sms.entity.SmsVariablesSetting;
import com.fotic.sms.SmsBody;

public interface ISmsService {
	
	/**
	 * 查询短信模板列表
	 * @param createTime  登记时间
	 * @return
	 */
	public List<SmsModule> queryModuleList(String createTime,String smsModuleType);
	

	/**
	 * 短信发送明细查询
	 * @return
	 */
	public List<SmsSendDetail> querySmsSendDetailList(SmsSendDetail smsSendDetail);
	
	/**
	 * 查询待发短信
	 * @return
	 */
	public List<SmsBody> querySmsBodyList(SmsBody SmsBody);
	
	/**
	 * 通过id查询短信明细信息
	 * @param smsSendDetail
	 * @return
	 */
	public SmsSendDetail querySmsSendDetailById(Integer id);
	
	/**
	 * 查询短信发送统计台账
	 * @return
	 */
	public List<SmsSendDetail> querySmsSendStatistical(SmsSendDetail smsSendDetail);
	
	
	
	/**
	 * 更改启用/停用状态
	 * @param moduleId  模板id
	 * @param status    现在的模板状态
	 */
	public int updateStatus(Integer moduleId,String status);
	
	/**
	 * 提交保存短信模板
	 * @param smsModule
	 * @param smsSettingRuleList
	 * @return
	 */
	public int addSmsModule(SmsModule smsModule,List<SmsSettingRule> smsSettingRuleList);
	
	/**
	 * 查询宏变量信息
	 * @return
	 */
	public List<SmsVariablesSetting> querySmsVariablesSettingList();
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public SmsModule querySmsModuleById(Integer id);

	/**
	 * 根据模板id查询规则设置
	 * @param moduleId
	 * @return
	 */
	public List<SmsSettingRule> querySmsSettingRulesByModuleId(Integer moduleId);
	
	/**
	 * 手工短信发送之前保存将要发送的短信信息
	 * @param smsPrepareSendRecord
	 * @return
	 */
	public int saveSmsPrepareSendRecord(SmsPrepareSendRecord smsPrepareSendRecord);
	
	/**
	 * 保存短信发送相信信息
	 * @param smsSendDetail
	 * @return
	 */
	public void saveSmsSendDetail(List<SmsSendDetail> smsSendDetailList);
	
	public void editModule(SmsModule smsModule, List<SmsSettingRule> smsSettingRuleList);
	
	//通过短信发送返回的状态码，查询出具体的失败原因
	public SmsCode querySmsCodeDetailByCode(String smsCode);
	
	/**
	 * 修改Smssenddetail sms_code send_status
	 * @param ssds
	 */
	public void updateSmssenddetailOfSmscodeAndSendstatusByUuid(List<SmsSendDetail> ssds);
	
	/**
	 * 发送短信service
	 */
	public void sendSms();
}
