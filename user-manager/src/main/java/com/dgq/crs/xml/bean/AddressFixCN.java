package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 中文固定格式地址
* @author dgq 
* @date 2018年6月5日
*/
@XmlType(propOrder = {"cityCN", "districtName", "postCode", "province"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AddressFixCN {
	
	@XmlElement(name="CityCN", namespace=NameSpaces.stc)
	private String cityCN; //地级行政区划代码  强制

	@XmlElement(name="DistrictName", namespace=NameSpaces.stc)
	private String districtName;  //县级行政区划代码  可选
	
	@XmlElement(name="PostCode", namespace=NameSpaces.stc)
	private String postCode; //邮编  可选

	@XmlElement(name="Province", namespace=NameSpaces.stc)
	private String province; //省级行政区划代码   强制
	
	public AddressFixCN() {
		super();
	}

	public AddressFixCN(String province, String cityCN) {
		super();
		this.province = province;
		this.cityCN = cityCN;
	}
	
	
	public AddressFixCN(String province, String cityCN, String districtName, String postCode) {
		super();
		this.province = province;
		this.cityCN = cityCN;
		this.districtName = districtName;
		this.postCode = postCode;
	}

	public String getCityCN() {
		return cityCN;
	}

	public void setCityCN(String cityCN) {
		this.cityCN = cityCN;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
