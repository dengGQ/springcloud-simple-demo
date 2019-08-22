package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: public class BirthInfo{ }
* @author dgq 
* @date 2018年6月4日
*/
@XmlType(propOrder = {"BirthDate", "city", "countryInfo"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class BirthInfo {
	
	@XmlElement(name="BirthDate", namespace=NameSpaces.stc)
	private String BirthDate; //出生日期    强制
	
	@XmlElement(name="City", namespace=NameSpaces.stc)
	private String city;//出生城市   可选
	
	@XmlElement(name="CountryInfo", namespace=NameSpaces.stc)
	private CountryInfo countryInfo; //强制

	public BirthInfo() {
		super();
	}

	public BirthInfo(String birthDate, CountryInfo countryInfo) {
		super();
		BirthDate = birthDate;
		this.countryInfo = countryInfo;
	}

	public String getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(String birthDate) {
		BirthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public CountryInfo getCountryInfo() {
		return countryInfo;
	}

	public void setCountryInfo(CountryInfo countryInfo) {
		this.countryInfo = countryInfo;
	}
}
