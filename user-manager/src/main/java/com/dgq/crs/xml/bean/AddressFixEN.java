package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 英文固定格式地址
* @author dgq 
* @date 2018年6月5日
*/

@XmlType(propOrder = {"cityEN", "street", "buildingIdentifier", "suiteIdentifier", 
		"floorIdentifier", "districtName", "pob", "postCode", "countrySubentity"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AddressFixEN {

	@XmlElement(name="CityEN", namespace=NameSpaces.stc)
	private String cityEN;//城市  强制
	
	@XmlElement(name="Street", namespace=NameSpaces.stc)
	private String street;//街道
	
	@XmlElement(name="BuildingIdentifier", namespace=NameSpaces.stc)
	private String buildingIdentifier;//楼号
	
	@XmlElement(name="SuiteIdentifier", namespace=NameSpaces.stc)
	private String suiteIdentifier;//房门号
	
	@XmlElement(name="FloorIdentifier", namespace=NameSpaces.stc)
	private String floorIdentifier;//楼层
	
	@XmlElement(name="DistrictName", namespace=NameSpaces.stc)
	private String districtName;//区
	
	@XmlElement(name="Pob", namespace=NameSpaces.stc)
	private String pob;//邮箱
	
	@XmlElement(name="PostCode", namespace=NameSpaces.stc)
	private String postCode;//邮编
	
	@XmlElement(name="CountrySubentity", namespace=NameSpaces.stc)
	private String countrySubentity;//中国境内地址填写省/自治区/直辖市的拼音，境外地址填写相应的行政区划
	
	public AddressFixEN() {
		super();
	}

	public AddressFixEN(String cityEN) {
		super();
		this.cityEN = cityEN;
	}

	public String getCityEN() {
		return cityEN;
	}

	public void setCityEN(String cityEN) {
		this.cityEN = cityEN;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuildingIdentifier() {
		return buildingIdentifier;
	}

	public void setBuildingIdentifier(String buildingIdentifier) {
		this.buildingIdentifier = buildingIdentifier;
	}

	public String getSuiteIdentifier() {
		return suiteIdentifier;
	}

	public void setSuiteIdentifier(String suiteIdentifier) {
		this.suiteIdentifier = suiteIdentifier;
	}

	public String getFloorIdentifier() {
		return floorIdentifier;
	}

	public void setFloorIdentifier(String floorIdentifier) {
		this.floorIdentifier = floorIdentifier;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountrySubentity() {
		return countrySubentity;
	}

	public void setCountrySubentity(String countrySubentity) {
		this.countrySubentity = countrySubentity;
	}
}
