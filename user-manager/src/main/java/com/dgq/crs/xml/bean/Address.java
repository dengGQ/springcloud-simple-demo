package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 地址信息，所有账户必须填报英文地址及其国家代码，  CountryCode为CN的如有中文地址应填报
* @author dgq 
* @date 2018年6月4日
*/
@XmlType(propOrder = {"countryCode", "addressEN", "addressCN"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Address {
	
	@XmlAttribute(name="legalAddressType")
	private String legalAddressType; //地址类型  取值：OECD301-OECD305  可选
	
	@XmlElement(name="CountryCode", namespace=NameSpaces.stc)
	private String countryCode; //国家代码   强制
	
	@XmlElement(name="AddressEN", namespace=NameSpaces.stc)
	private AddressEN addressEN; //英文地址  强制
	
	@XmlElement(name="AddressCN", namespace=NameSpaces.stc)
	private AddressCN addressCN; //中文地址 可选

	public Address() {
		super();
	}

	public Address(String countryCode, AddressEN addressEN) {
		super();
		this.countryCode = countryCode;
		this.addressEN = addressEN;
	}

	public Address(String legalAddressType, String countryCode, AddressEN addressEN, AddressCN addressCN) {
		super();
		this.legalAddressType = legalAddressType;
		this.countryCode = countryCode;
		this.addressEN = addressEN;
		this.addressCN = addressCN;
	}

	public String getLegalAddressType() {
		return legalAddressType;
	}

	public void setLegalAddressType(String legalAddressType) {
		this.legalAddressType = legalAddressType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public AddressEN getAddressEN() {
		return addressEN;
	}

	public void setAddressEN(AddressEN addressEN) {
		this.addressEN = addressEN;
	}

	public AddressCN getAddressCN() {
		return addressCN;
	}

	public void setAddressCN(AddressCN addressCN) {
		this.addressCN = addressCN;
	}
}
