package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: public class CountryInfo{ }
* @author dgq 
* @date 2018年6月4日
*/

@XmlType(propOrder= {"countryCode", "formerCountryName"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class CountryInfo {

	@XmlElement(name="CountryCode",namespace=NameSpaces.stc)
	private String countryCode;

	@XmlElement(name="FormerCountryName",namespace=NameSpaces.stc)
	private String formerCountryName;

	public CountryInfo() {
		super();
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFormerCountryName() {
		return formerCountryName;
	}
	public void setFormerCountryName(String formerCountryName) {
		this.formerCountryName = formerCountryName;
	}
}
