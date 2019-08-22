package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 中文地址  包含固定格式：AddressFixCN  和  自由格式：AddressFreeCN
* 				CountryCode为CN且客户提供了中文地址的需填报
* 				CountryCode不为CN的无需填报
* @author dgq 
* @date 2018年6月5日
*/
@XmlType(propOrder = {"addressFixCN", "addressFreeCN"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AddressCN {

	@XmlElement(name="AddressFixCN", namespace=NameSpaces.stc)
	private AddressFixCN addressFixCN; //可选

	@XmlElement(name="AddressFreeCN", namespace=NameSpaces.stc)
	private String addressFreeCN; //可选
	
	public AddressCN() {
		super();
	}

	public AddressCN(String addressFreeCN, AddressFixCN addressFixCN) {
		super();
		this.addressFreeCN = addressFreeCN;
		this.addressFixCN = addressFixCN;
	}

	public AddressCN(String addressFreeCN) {
		super();
		this.addressFreeCN = addressFreeCN;
	}

	public AddressCN(AddressFixCN addressFixCN) {
		super();
		this.addressFixCN = addressFixCN;
	}

	public AddressFixCN getAddressFixCN() {
		return addressFixCN;
	}

	public void setAddressFixCN(AddressFixCN addressFixCN) {
		this.addressFixCN = addressFixCN;
	}

	public String getAddressFreeCN() {
		return addressFreeCN;
	}

	public void setAddressFreeCN(String addressFreeCN) {
		this.addressFreeCN = addressFreeCN;
	}
}
