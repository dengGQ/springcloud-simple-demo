package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 包含报送信息金融机构的系统用户账号、金融机构注册码、报告类型、序列号等内容
* @author dgq 
* @date 2018年6月4日
*/
@XmlType(propOrder= {"reportingID", "fiID", "reportingType", "messageRefId", "reportingPeriod", "messageTypeIndic", "tmstp"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class MessageHeader {
	
	@XmlElement(name="ReportingID", namespace=NameSpaces.cncrs)
	private String reportingID; //系统用户账号  强制
	
	@XmlElement(name="FIID", namespace=NameSpaces.cncrs)
	private String fiID; //金融机构注册码  强制
	
	@XmlElement(name="ReportingType", namespace=NameSpaces.cncrs)
	private String reportingType;//填写CRS  强制
	
	@XmlElement(name="MessageRefId", namespace=NameSpaces.cncrs)
	private String messageRefId;//报告唯一编码 与文件名相同 , 不可重复（如果被拒收也不可重复）  强制
	
	@XmlElement(name="ReportingPeriod", namespace=NameSpaces.cncrs)
	private String reportingPeriod;//报告所属公立年度  格式： YYYY-12-31
	
	@XmlElement(name="MessageTypeIndic", namespace=NameSpaces.cncrs)
	private String messageTypeIndic;//报告类型  取值：CRS701（对应DocTypeIndic：R1或T11）：新数据 ; CRS702(对应R2/R3/T12/T13)：修改/删除数据 ; CRS703：零申报(不能包含ReportingGroup)
	
	@XmlElement(name="Tmstp", namespace=NameSpaces.cncrs)
	private String tmstp;//报告生成时间戳   强制
	
	public MessageHeader() {
		super();
	}

	public MessageHeader(String reportingID, String fiID, String reportingType, String messageRefId,
			String reportingPeriod, String messageTypeIndic, String tmstp) {
		super();
		this.reportingID = reportingID;
		this.fiID = fiID;
		this.reportingType = reportingType;
		this.messageRefId = messageRefId;
		this.reportingPeriod = reportingPeriod;
		this.messageTypeIndic = messageTypeIndic;
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

	public String getReportingType() {
		return reportingType;
	}

	public void setReportingType(String reportingType) {
		this.reportingType = reportingType;
	}

	public String getMessageRefId() {
		return messageRefId;
	}

	public void setMessageRefId(String messageRefId) {
		this.messageRefId = messageRefId;
	}

	public String getReportingPeriod() {
		return reportingPeriod;
	}

	public void setReportingPeriod(String reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}

	public String getMessageTypeIndic() {
		return messageTypeIndic;
	}

	public void setMessageTypeIndic(String messageTypeIndic) {
		this.messageTypeIndic = messageTypeIndic;
	}

	public String getTmstp() {
		return tmstp;
	}

	public void setTmstp(String tmstp) {
		this.tmstp = tmstp;
	}
}
