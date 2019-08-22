package com.dgq.crs.xml.bean;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 个人name
* @author dgq 
* @date 2018年12月18日
*/
@XmlType(propOrder= {"nameType", "firstName", "middleName", "lastName", "nameCN", "namePrefix", 
		"precedingTitle", "title", "suffix", "generationIdentifier", "generalSuffix"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class NamePerson {
	
	@XmlAttribute(name="nameType")
	private String nameType; //固定值：OECD202 - OECD205，OECD208  可选
	
	@XmlElement(name="FirstName", namespace=NameSpaces.stc)
	private String firstName; //英文名，中文拼音 强制
	
	@XmlElement(name="MiddleName", namespace=NameSpaces.stc)
	private String middleName; //可选
	
	@XmlElement(name="LastName", namespace=NameSpaces.stc)
	private String lastName; //英文姓，中文拼音，强制
	
	@XmlElement(name="NameCN", namespace=NameSpaces.stc)
	private String nameCN; //中文姓名 开户证件有中文姓名的应填报；可选/强制
	
	@XmlElement(name="NamePrefix", namespace=NameSpaces.stc)
	private String namePrefix;//可选
	
	@XmlElement(name="PrecedingTitle", namespace=NameSpaces.stc)
	private String precedingTitle;//可选
	
	@XmlElement(name="Title", namespace=NameSpaces.stc)
	private String title[];//可选
	
	@XmlElement(name="Suffix", namespace=NameSpaces.stc)
	private String suffix[];//可选
	
	@XmlElement(name="GenerationIdentifier", namespace=NameSpaces.stc)
	private String generationIdentifier;//可选; 测试显示不可用
	
	@XmlElement(name="GeneralSuffix", namespace=NameSpaces.stc)
	private String generalSuffix;//可选
	
	public NamePerson() {
	}
	
	public NamePerson(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getPrecedingTitle() {
		return precedingTitle;
	}

	public void setPrecedingTitle(String precedingTitle) {
		this.precedingTitle = precedingTitle;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public String[] getSuffix() {
		return suffix;
	}

	public void setSuffix(String[] suffix) {
		this.suffix = suffix;
	}

	public String getGenerationIdentifier() {
		return generationIdentifier;
	}

	public void setGenerationIdentifier(String generationIdentifier) {
		this.generationIdentifier = generationIdentifier;
	}

	public String getGeneralSuffix() {
		return generalSuffix;
	}

	public void setGeneralSuffix(String generalSuffix) {
		this.generalSuffix = generalSuffix;
	}
}
