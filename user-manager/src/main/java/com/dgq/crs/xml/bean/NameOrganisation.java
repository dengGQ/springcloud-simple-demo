package com.dgq.crs.xml.bean;
/*
* @Description: 机构name
* @author dgq 
* @date 2018年12月18日
*/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

@XmlType(propOrder= {"orgNameType", "organisationNameEN", "organisationNameCN"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class NameOrganisation {
	
	@XmlAttribute(name="nameType")
	private String orgNameType;//机构姓名类型
	
	@XmlElement(name="OrganisationNameEN", namespace=NameSpaces.stc)
	private String organisationNameEN; //机构英文，名称   强制
	
	@XmlElement(name="OrganisationNameCN", namespace=NameSpaces.stc)
	private String organisationNameCN; //机构中文名称  强制/可选
	
	public NameOrganisation() {
	}
	
	public NameOrganisation(String organisationNameEN) {
		super();
		this.organisationNameEN = organisationNameEN;
	}

	public String getOrgNameType() {
		return orgNameType;
	}

	public void setOrgNameType(String orgNameType) {
		this.orgNameType = orgNameType;
	}

	public String getOrganisationNameEN() {
		return organisationNameEN;
	}

	public void setOrganisationNameEN(String organisationNameEN) {
		this.organisationNameEN = organisationNameEN;
	}

	public String getOrganisationNameCN() {
		return organisationNameCN;
	}

	public void setOrganisationNameCN(String organisationNameCN) {
		this.organisationNameCN = organisationNameCN;
	}
}
