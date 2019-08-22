package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 反馈文件中的MessageHeader
* @author dgq 
* @date 2019年7月4日
*/
@XmlType(propOrder= {"reportingID", "fiID", "messageRefId", "tmstp"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class MessageHeaderRes {
	
	@XmlElement(name="ReportingID", namespace=NameSpaces.CNCRS_RES)
	private String reportingID; //系统用户账号  强制
	
	@XmlElement(name="FIID", namespace=NameSpaces.CNCRS_RES)
	private String fiID; //金融机构注册码  强制
		
	@XmlElement(name="MessageRefId", namespace=NameSpaces.CNCRS_RES)
	private String messageRefId;//报告唯一编码 与文件名相同 , 不可重复（如果被拒收也不可重复）  强制

	@XmlElement(name="Tmstp", namespace=NameSpaces.CNCRS_RES)
	private String tmstp;//报告生成时间戳   强制

	public MessageHeaderRes() {
		super();
	}

	public MessageHeaderRes(String reportingID, String fiID, String messageRefId, String tmstp) {
		super();
		this.reportingID = reportingID;
		this.fiID = fiID;
		this.messageRefId = messageRefId;
		this.tmstp = tmstp;
	}

	public String getReportingID() {
		return reportingID;
	}

	public void setReportingID(String reportingID) {
		this.reportingID = reportingID;
	}

	public String getFiID() {
		return fiID;
	}

	public void setFiID(String fiID) {
		this.fiID = fiID;
	}

	public String getMessageRefId() {
		return messageRefId;
	}

	public void setMessageRefId(String messageRefId) {
		this.messageRefId = messageRefId;
	}

	public String getTmstp() {
		return tmstp;
	}

	public void setTmstp(String tmstp) {
		this.tmstp = tmstp;
	}
}
