package com.fotic.management.sms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 短信发送明细
 *
 */
@Entity
@Table(name="RHZX_SMS_SEND_DETAIL")
public class SmsSendDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="APP_SEQUENCE")  
	@SequenceGenerator(name="APP_SEQUENCE",sequenceName="APP_SEQUENCE")
	private Integer id;//模板id
	private String coOrgCode;//合作机构代码
	private String projId;//项目代码
	private String custCode;//客户编号
	private String custName;//客户名称
	private String prodCode;//产品代码
	private String smsSendContent;//发送内容
	private String credType;//证件类型
	private String credNo;//证件号码
	private String phone;//手机号码
	private String iouNo;//业务号
	private Date sendDate;//发送日期
	private String smsCode;//发送短信返回的码值
	private String gender;//称呼
	private String uuid;
	private Integer moduleId;//模版Id
	private Date insertDttm;//

	

	public Date getInsertDttm() {
		return insertDttm;
	}

	public void setInsertDttm(Date insertDttm) {
		this.insertDttm = insertDttm;
	}

	private Integer repeatSendCount;//重发次数
	
	//冗余字段
	@Transient
	private String coOrgName;//合作机构名称
	@Transient
	private String projName;//项目名称
	@Transient
	private String prodName;//产品名称
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;//发送时间（开始）
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//发送时间（结束）
	@Transient
	private String sendDateStr;//发送日期字符串格式
	@Transient
	private String sendStatus;//发送状态
	@Transient
	private String smsCodeDetail;//原因说明（发送短信返回的码值说明）
	@Transient
	private Integer sendTotalNum;//短信发送总条数
	@Transient
	private Integer sendSuccessNum;//短信发送成功条数
	@Transient
	private Integer sendFailNum;//短信发送失败条数
	@Transient
	private String SendMonthStr;//短信发送月份
	@Transient
	private String startMonthStr;//开始月份
	@Transient
	private String endMonthStr;//结束月份
	@Transient
	private String smsModuleTypeName;//短信模版类型
	@Transient
	private String smsModuleType;//短信模版类型
	
	public SmsSendDetail() {
		super();
	}

	public SmsSendDetail(Integer id, String coOrgCode, String projId, String custCode, String custName, String prodCode,
			String smsSendContent, String credType, String credNo, String phone, String iouNo, Date sendDate,
			String smsCode) {
		super();
		this.id = id;
		this.coOrgCode = coOrgCode;
		this.projId = projId;
		this.custCode = custCode;
		this.custName = custName;
		this.prodCode = prodCode;
		this.smsSendContent = smsSendContent;
		this.credType = credType;
		this.credNo = credNo;
		this.phone = phone;
		this.iouNo = iouNo;
		this.sendDate = sendDate;
		this.smsCode = smsCode;
	}

	
	
	public SmsSendDetail(String coOrgCode, String projId, String custCode, String custName, String prodCode,
			String smsSendContent, String credType, String credNo, String phone, String iouNo, Date sendDate,
			String uuid) {
		super();
		this.coOrgCode = coOrgCode;
		this.projId = projId;
		this.custCode = custCode;
		this.custName = custName;
		this.prodCode = prodCode;
		this.smsSendContent = smsSendContent;
		this.credType = credType;
		this.credNo = credNo;
		this.phone = phone;
		this.iouNo = iouNo;
		this.sendDate = sendDate;
		this.uuid = uuid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoOrgCode() {
		return coOrgCode;
	}

	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getSmsSendContent() {
		return smsSendContent;
	}

	public void setSmsSendContent(String smsSendContent) {
		this.smsSendContent = smsSendContent;
	}

	public String getCredType() {
		return credType;
	}

	public void setCredType(String credType) {
		this.credType = credType;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIouNo() {
		return iouNo;
	}

	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getCoOrgName() {
		return coOrgName;
	}

	public void setCoOrgName(String coOrgName) {
		this.coOrgName = coOrgName;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSendDateStr() {
		return sendDateStr;
	}

	public void setSendDateStr(String sendDateStr) {
		this.sendDateStr = sendDateStr;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getSmsCodeDetail() {
		return smsCodeDetail;
	}

	public void setSmsCodeDetail(String smsCodeDetail) {
		this.smsCodeDetail = smsCodeDetail;
	}

	public Integer getSendTotalNum() {
		return sendTotalNum;
	}

	public void setSendTotalNum(Integer sendTotalNum) {
		this.sendTotalNum = sendTotalNum;
	}

	public Integer getSendSuccessNum() {
		return sendSuccessNum;
	}

	public void setSendSuccessNum(Integer sendSuccessNum) {
		this.sendSuccessNum = sendSuccessNum;
	}

	public Integer getSendFailNum() {
		return sendFailNum;
	}

	public void setSendFailNum(Integer sendFailNum) {
		this.sendFailNum = sendFailNum;
	}

	public String getSendMonthStr() {
		return SendMonthStr;
	}

	public void setSendMonthStr(String sendMonthStr) {
		SendMonthStr = sendMonthStr;
	}

	public String getStartMonthStr() {
		return startMonthStr;
	}

	public void setStartMonthStr(String startMonthStr) {
		this.startMonthStr = startMonthStr;
	}

	public String getEndMonthStr() {
		return endMonthStr;
	}

	public void setEndMonthStr(String endMonthStr) {
		this.endMonthStr = endMonthStr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

	public Integer getRepeatSendCount() {
		return repeatSendCount;
	}

	public void setRepeatSendCount(Integer repeatSendCount) {
		this.repeatSendCount = repeatSendCount;
	}

	public String getSmsModuleTypeName() {
		return smsModuleTypeName;
	}

	public void setSmsModuleTypeName(String smsModuleTypeName) {
		this.smsModuleTypeName = smsModuleTypeName;
	}

	public String getSmsModuleType() {
		return smsModuleType;
	}

	public void setSmsModuleType(String smsModuleType) {
		this.smsModuleType = smsModuleType;
	}
	
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		return "SmsSendDetail [id=" + id + ", coOrgCode=" + coOrgCode + ", projId=" + projId + ", custCode=" + custCode
				+ ", custName=" + custName + ", prodCode=" + prodCode + ", smsSendContent=" + smsSendContent
				+ ", credType=" + credType + ", credNo=" + credNo + ", phone=" + phone + ", iouNo=" + iouNo
				+ ", sendDate=" + sendDate + ", smsCode=" + smsCode + ", gender=" + gender + ", uuid=" + uuid
				+ ", moduleId=" + moduleId + ", repeatSendCount=" + repeatSendCount + ", coOrgName=" + coOrgName
				+ ", projName=" + projName + ", prodName=" + prodName + ", startDate=" + startDate + ", endDate="
				+ endDate + ", sendDateStr=" + sendDateStr + ", sendStatus=" + sendStatus + ", smsCodeDetail="
				+ smsCodeDetail + ", sendTotalNum=" + sendTotalNum + ", sendSuccessNum=" + sendSuccessNum
				+ ", sendFailNum=" + sendFailNum + ", SendMonthStr=" + SendMonthStr + ", startMonthStr=" + startMonthStr
				+ ", endMonthStr=" + endMonthStr + ", smsModuleTypeName=" + smsModuleTypeName + ", smsModuleType="
				+ smsModuleType + ", getId()=" + getId() + ", getCoOrgCode()=" + getCoOrgCode() + ", getProjId()="
				+ getProjId() + ", getCustCode()=" + getCustCode() + ", getCustName()=" + getCustName()
				+ ", getProdCode()=" + getProdCode() + ", getSmsSendContent()=" + getSmsSendContent()
				+ ", getCredType()=" + getCredType() + ", getCredNo()=" + getCredNo() + ", getPhone()=" + getPhone()
				+ ", getIouNo()=" + getIouNo() + ", getSendDate()=" + getSendDate() + ", getSmsCode()=" + getSmsCode()
				+ ", getCoOrgName()=" + getCoOrgName() + ", getProjName()=" + getProjName() + ", getProdName()="
				+ getProdName() + ", getStartDate()=" + getStartDate() + ", getEndDate()=" + getEndDate()
				+ ", getSendDateStr()=" + getSendDateStr() + ", getSendStatus()=" + getSendStatus()
				+ ", getSmsCodeDetail()=" + getSmsCodeDetail() + ", getSendTotalNum()=" + getSendTotalNum()
				+ ", getSendSuccessNum()=" + getSendSuccessNum() + ", getSendFailNum()=" + getSendFailNum()
				+ ", getSendMonthStr()=" + getSendMonthStr() + ", getStartMonthStr()=" + getStartMonthStr()
				+ ", getEndMonthStr()=" + getEndMonthStr() + ", getGender()=" + getGender() + ", getUuid()=" + getUuid()
				+ ", getRepeatSendCount()=" + getRepeatSendCount() + ", getSmsModuleTypeName()="
				+ getSmsModuleTypeName() + ", getSmsModuleType()=" + getSmsModuleType() + ", getModuleId()="
				+ getModuleId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
