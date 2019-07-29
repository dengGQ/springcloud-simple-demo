package com.fotic.sms;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
* @Description: public class SmsBody{ }
* @author dgq 
* @date 2018年4月18日
*/

@Entity
@Table(name="SMS_BODY")
public class SmsBody {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="APP_SEQUENCE")  
	@SequenceGenerator(name="APP_SEQUENCE",sequenceName="APP_SEQUENCE")
	private Integer id;
	
	private String phone;
	
	private String smsContent;
	
	private String smsModuleType;
	
	private Date sendDate;
	
	private String status; //0:未发送  1：已发送 ; default:0
	
	private String smsCode;
	
	private Date createTime;//创建时间
	
	private String uuid; //同批次唯一标识
	
	private String platform;//平台标识
	
	private String callBackUrl;//回调地址
	
	private Integer repeatSendCount; //重发次数
	
	private String repeatRole; //重发规则
	
	private String account;//业务号
	
	private String mouthTime;//短信生成月份
	
	private String projId;
	private String coOrgCode;
	private String custName;
	private String credNo ;
	

	

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getCoOrgCode() {
		return coOrgCode;
	}

	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}

	

	

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMouthTime() {
		return mouthTime;
	}

	public void setMouthTime(String mouthTime) {
		this.mouthTime = mouthTime;
	}

	public SmsBody() {
	}

	public SmsBody(String phone, String smsContent, Date sendDate, String uuid, String platform, String callBackUrl,String projId ,String coOrgCode ,String custName ,String credNo) {
		this.phone = phone;
		this.smsContent = smsContent;
		this.sendDate = sendDate;
		this.uuid = uuid;
		this.platform = platform;
		this.callBackUrl = callBackUrl;
		this.credNo = credNo;
		this.coOrgCode = coOrgCode ;
		this.custName = custName;
		this.projId = projId ;
	}
	
	public String getSmsModuleType() {
		return smsModuleType;
	}

	public void setSmsModuleType(String smsModuleType) {
		this.smsModuleType = smsModuleType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public Integer getRepeatSendCount() {
		return repeatSendCount;
	}

	public void setRepeatSendCount(Integer repeatSendCount) {
		this.repeatSendCount = repeatSendCount;
	}

	public String getRepeatRole() {
		return repeatRole;
	}

	public void setRepeatRole(String repeatRole) {
		this.repeatRole = repeatRole;
	}
	
}
