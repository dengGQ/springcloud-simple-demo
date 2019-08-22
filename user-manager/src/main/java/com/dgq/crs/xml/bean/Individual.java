package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 账户持有人- 个人信息
* @author dgq 
* @date 2018年6月4日
*/
@XmlType(propOrder = { "name", "gender", "address", "phoneNo", "idType", "idNumber", "resCountryCode",
						"tin", "nationality", "birthInfo", "explanation"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Individual {
	
	@XmlElement(name="Name", namespace=NameSpaces.stc)
	private NamePerson name; //姓名   强制
	
	@XmlElement(name="Gender", namespace=NameSpaces.stc)
	private String gender; //性别   强制
 	
	@XmlElement(name="Address", namespace=NameSpaces.stc)
	private Address address; //地址  强制
	
	@XmlElement(name="PhoneNo", namespace=NameSpaces.stc)
	private String phoneNo;//联系电话  可选
	
	@XmlElement(name="IDType", namespace=NameSpaces.stc)
	private String idType; //身份证件类型 固定值：ACC201--  可选
	
	@XmlElement(name="IDNumber", namespace=NameSpaces.stc)
	private String idNumber; //身份证件号码  可选

	@XmlElement(name="ResCountryCode", namespace=NameSpaces.stc)
	private String[] resCountryCode; //税收居民国代码  强制

	@XmlElement(name="TIN", namespace=NameSpaces.stc)
	private CommonElement[] tin; //纳税人识别号  强制/ 可选	

	@XmlElement(name="Nationality", namespace=NameSpaces.stc)
	private String nationality; //国籍代码  可选
	
	@XmlElement(name="BirthInfo", namespace=NameSpaces.stc)
	private BirthInfo birthInfo; //N: 强制  P：可选
	
	@XmlElement(name="Explanation", namespace=NameSpaces.stc)
	private String explanation;  //SelfCertification元素值为true且没有报送TIN的，必须报送本字段   强制/可选
	
	public Individual() {
		super();
	}

	public Individual(NamePerson name, String gender, Address address, String[] resCountryCode) {
		super();
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.resCountryCode = resCountryCode;
	}

	public NamePerson getName() {
		return name;
	}

	public void setName(NamePerson name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public BirthInfo getBirthInfo() {
		return birthInfo;
	}

	public void setBirthInfo(BirthInfo birthInfo) {
		this.birthInfo = birthInfo;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
