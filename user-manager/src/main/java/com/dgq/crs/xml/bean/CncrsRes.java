package com.dgq.crs.xml.bean;
/*
* @Description: public class CncrsRes{ }
* @author dgq 
* @date 2019年6月21日
*/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dgq.crs.constant.NameSpaces;

@XmlRootElement(name="CNCRS", namespace = NameSpaces.CNCRS_RES)
@XmlAccessorType(value=XmlAccessType.FIELD)
public class CncrsRes {
	
	@XmlAttribute(name="version")
	private String version;
	
	@XmlElement(name="MessageHeader", namespace=NameSpaces.CNCRS_RES)
	private MessageHeaderRes messageHeader;
	
	@XmlElement(name="ResultGroup", namespace = NameSpaces.CNCRS_RES)
	private ResultGroup resultGroup;

	public CncrsRes() {
		super();
	}

	public CncrsRes(ResultGroup resultGroup, MessageHeaderRes messageHeader) {
		super();
		this.resultGroup = resultGroup;
		this.messageHeader = messageHeader;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ResultGroup getResultGroup() {
		return resultGroup;
	}

	public void setResultGroup(ResultGroup resultGroup) {
		this.resultGroup = resultGroup;
	}

	public MessageHeaderRes getMessageHeader() {
		return messageHeader;
	}

	public void setMessageHeader(MessageHeaderRes messageHeader) {
		this.messageHeader = messageHeader;
	}
}
