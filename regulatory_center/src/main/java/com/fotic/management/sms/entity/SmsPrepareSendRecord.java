package com.fotic.management.sms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 手工发送短信前保存将要发送的短信信息
 *
 */
@Entity
@Table(name="RHZX_SMS_PREPARE_SEND_RECORD")
public class SmsPrepareSendRecord {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="APP_SEQUENCE")  
	@SequenceGenerator(name="APP_SEQUENCE",sequenceName="APP_SEQUENCE")
	private Integer id;//主键id
	private String coOrgCode;//合作机构代码
	private String projId;//项目代码
	private String custCode;//客户编码
	private String custName;//客户名称
	private String prodCode;//产品代码
	private String smsSendContent;//发送内容
	private String credType;//证件类型
	private String credNo;//证件号码
	private String phone;//手机号码
	private String iouNo;//业务号
	private Date insertDate;//发送日期
	private Integer moduleId;//模版Id
	
	public SmsPrepareSendRecord() {
		super();
	}

	public SmsPrepareSendRecord(Integer id, String coOrgCode, String projId, String custCode, String custName,
			String prodCode, String smsSendContent, String credType, String credNo, String phone, String iouNo,
			Date insertDate, Integer moduleId) {
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
		this.insertDate = insertDate;
		this.moduleId = moduleId;
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

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		return "SmsPrepareSendRecord [id=" + id + ", coOrgCode=" + coOrgCode + ", projId=" + projId + ", custCode="
				+ custCode + ", custName=" + custName + ", prodCode=" + prodCode + ", smsSendContent=" + smsSendContent
				+ ", credType=" + credType + ", credNo=" + credNo + ", phone=" + phone + ", iouNo=" + iouNo
				+ ", insertDate=" + insertDate + ", moduleId=" + moduleId + ", getId()=" + getId() + ", getCoOrgCode()="
				+ getCoOrgCode() + ", getProjId()=" + getProjId() + ", getCustCode()=" + getCustCode()
				+ ", getCustName()=" + getCustName() + ", getProdCode()=" + getProdCode() + ", getSmsSendContent()="
				+ getSmsSendContent() + ", getCredType()=" + getCredType() + ", getCredNo()=" + getCredNo()
				+ ", getPhone()=" + getPhone() + ", getIouNo()=" + getIouNo() + ", getInsertDate()=" + getInsertDate()
				+ ", getModuleId()=" + getModuleId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
