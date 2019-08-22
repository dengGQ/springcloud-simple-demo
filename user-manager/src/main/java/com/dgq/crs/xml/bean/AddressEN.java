package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 英文地址  包含固定格式：AddressFixCN  和  自由格式：AddressFreeCN
* 				新开账户必须同时填报固定格式和自由格式，存量账户可以选择只填报自由格式
* @author dgq 
* @date 2018年6月5日
*/
@XmlType(propOrder = {"addressFixEN", "addressFreeEN"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AddressEN {
	
	@XmlElement(name="AddressFixEN", namespace=NameSpaces.stc)
	private AddressFixEN addressFixEN; //N: 强制  P：可选
	
	@XmlElement(name="AddressFreeEN", namespace=NameSpaces.stc)
	private String addressFreeEN;  //强制
	
	public AddressEN() {
		super();
	}

	public AddressEN(AddressFixEN addressFixEN, String addressFreeEN) {
		super();
		this.addressFixEN = addressFixEN;
		this.addressFreeEN = addressFreeEN;
	}

	public AddressEN(String addressFreeEN) {
		super();
		this.addressFreeEN = addressFreeEN;
	}

	public AddressFixEN getAddressFixEN() {
		return addressFixEN;
	}

	public void setAddressFixEN(AddressFixEN addressFixEN) {
		this.addressFixEN = addressFixEN;
	}

	public String getAddressFreeEN() {
		return addressFreeEN;
	}

	public void setAddressFreeEN(String addressFreeEN) {
		this.addressFreeEN = addressFreeEN;
	}
}
