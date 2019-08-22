package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 控制人信息  AccountHolderType == CRS101时必填
* @author dgq 
* @date 2018年6月5日
*/
@XmlType(propOrder = {"name", "ctrlgPersonType", "resCountryCode", "tin", "birthInfo", "address", "explanation"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class ControllingPerson {
	
	@XmlElement(name="Name", namespace=NameSpaces.cncrs)
	private NamePerson name; //控制人姓名  强制
	
	@XmlElement(name="CtrlgPersonType", namespace=NameSpaces.cncrs)
	private String ctrlgPersonType;//控制人类型  固定值：CRS801-CRS813  可选
	
	@XmlElement(name="ResCountryCode", namespace=NameSpaces.cncrs)
	private String[] resCountryCode;// 税收居民国代码  固定长度：2  强制
	
	@XmlElement(name="TIN", namespace=NameSpaces.cncrs)
	private CommonElement[] tin; // 控制人在其税收居民国的纳税识别号，强制/可选

	@XmlElement(name="BirthInfo", namespace=NameSpaces.cncrs)
	private BirthInfo birthInfo; //N：强制 P：存量
	
	@XmlElement(name="Address", namespace=NameSpaces.cncrs)
	private Address address;//地址   强制
	
	@XmlElement(name="Explanation", namespace=NameSpaces.cncrs)
	private String explanation; //N: 如果没有报送TIN，则必须报送该字段   强制/可选
	
	public ControllingPerson() {
		super();
	}

	public ControllingPerson(NamePerson name, Address address, String[] resCountryCode) {
		super();
		this.name = name;
		this.address = address;
		this.resCountryCode = resCountryCode;
	}

	public ControllingPerson(NamePerson name, String ctrlgPersonType, Address address,
			String[] resCountryCode, CommonElement[] tin, String explanation, BirthInfo birthInfo) {
		super();
		this.name = name;
		this.ctrlgPersonType = ctrlgPersonType;
		this.address = address;
		this.resCountryCode = resCountryCode;
		this.tin = tin;
		this.explanation = explanation;
		this.birthInfo = birthInfo;
	}

	public NamePerson getName() {
		return name;
	}

	public void setName(NamePerson name) {
		this.name = name;
	}

	public String getCtrlgPersonType() {
		return ctrlgPersonType;
	}

	public void setCtrlgPersonType(String ctrlgPersonType) {
		this.ctrlgPersonType = ctrlgPersonType;
	}

	public String[] getResCountryCode() {
		return resCountryCode;
	}

	public void setResCountryCode(String[] resCountryCode) {
		this.resCountryCode = resCountryCode;
	}

	public CommonElement[] getTin() {
		return tin;
	}

	public void setTin(CommonElement[] tin) {
		this.tin = tin;
	}

	public BirthInfo getBirthInfo() {
		return birthInfo;
	}

	public void setBirthInfo(BirthInfo birthInfo) {
		this.birthInfo = birthInfo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
