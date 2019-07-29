package com.fotic.management.sms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="RHZX_SMS_CODE")
public class SmsCode {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="APP_SEQUENCE")  
	@SequenceGenerator(name="APP_SEQUENCE",sequenceName="APP_SEQUENCE")
	private Integer id;//主键id
	private String smsCode;//返回码值
	private String smsCodeDetail;//码值说明
	
	
	public SmsCode() {
		super();
	}
	public SmsCode(Integer id, String smsCode, String smsCodeDetail) {
		super();
		this.id = id;
		this.smsCode = smsCode;
		this.smsCodeDetail = smsCodeDetail;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getSmsCodeDetail() {
		return smsCodeDetail;
	}
	public void setSmsCodeDetail(String smsCodeDetail) {
		this.smsCodeDetail = smsCodeDetail;
	}
	
	
}
