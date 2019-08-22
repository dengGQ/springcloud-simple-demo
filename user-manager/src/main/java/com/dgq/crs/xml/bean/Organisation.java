package com.dgq.crs.xml.bean;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 账户持有人 - 机构信息
* @author dgq 
* @date 2018年6月4日
*/

@XmlType(propOrder = {"name", "address", "phoneNo", "resCountryCode", "tin", "explanation"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Organisation {
	
	@XmlElement(name="Name", namespace=NameSpaces.stc)
	private NameOrganisation name; //姓名   强制
 	
	@XmlElement(name="Address", namespace=NameSpaces.stc)
	private Address address; //地址  强制

	@XmlElement(name="PhoneNo", namespace=NameSpaces.stc)
	private String phoneNo;//联系电话  可选

	@XmlElement(name="ResCountryCode", namespace=NameSpaces.stc)
	private String[] resCountryCode; //税收居民国代码  强制
	
	@XmlElement(name="TIN", namespace=NameSpaces.stc)
	private CommonElement[] tin; //纳税人识别号  强制/ 可选	
	
	@XmlElement(name="Explanation", namespace=NameSpaces.stc)
	private String explanation;  //不能提供纳税人识别号的理由；SelfCertification元素值为true且没有报送TIN的，必须报送本字段   强制/可选

	public Organisation() {
		super();
	}

	public Organisation(NameOrganisation name, Address address, String[] resCountryCode) {
		super();
		this.name = name;
		this.address = address;
		this.resCountryCode = resCountryCode;
	}

	public Organisation(NameOrganisation name, Address address, String[] resCountryCode, String phoneNo, CommonElement[] tin,
			String explanation) {
		super();
		this.name = name;
		this.address = address;
		this.resCountryCode = resCountryCode;
		this.phoneNo = phoneNo;
		this.tin = tin;
		this.explanation = explanation;
	}

	public NameOrganisation getName() {
		return name;
	}

	public void setName(NameOrganisation name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
