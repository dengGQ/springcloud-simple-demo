package com.dgq.crs.xml.bean;
/*
* @Description: public class AccountErrReport{ }
* @author dgq 
* @date 2019年6月21日
*/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

@XmlType(propOrder = {"docRefId", "RErrCode"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AccountErrReport {
	
	@XmlElement(name="DocRefId", namespace = NameSpaces.CNCRS_RES)
	private String docRefId;
	
	@XmlElement(name="RErrCode", namespace = NameSpaces.CNCRS_RES)
	private String RErrCode;

	public String getDocRefId() {
		return docRefId;
	}

	public void setDocRefId(String docRefId) {
		this.docRefId = docRefId;
	}

	public String getRErrCode() {
		return RErrCode;
	}

	public void setRErrCode(String rErrCode) {
		RErrCode = rErrCode;
	}
}
