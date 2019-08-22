package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/*
* @Description: xml 节点属性是currCode的统一映射到这个类
* @author dgq 
* @date 2018年6月4日
*/

@XmlAccessorType(value=XmlAccessType.FIELD)
public class CommonElement {
	
	@XmlValue
	private String value; //节点值
	
	@XmlAttribute(name="currCode")
	private String currCode; //currCode属性值
	
	@XmlAttribute(name="inType")
	private String inType; //识别号类型，固定值 （TIN）  可选
	@XmlAttribute(name="issuedBy")
	private String issuedBy; // 发放改识别号的国家代码  可选

	public CommonElement() {
		super();
	}

	public CommonElement(String value, String currCode) {
		super();
		this.value = value;
		this.currCode = currCode;
	}


	public CommonElement(String value) {
		super();
		this.value = value;
	}

	public CommonElement(String value, String inType, String issuedBy) {
		super();
		this.value = value;
		this.inType = inType;
		this.issuedBy = issuedBy;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
}
