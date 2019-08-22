package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 账户持有人：个人（Individual） 和 组织机构（Organisation）
* @author dgq 
* @date 2018年6月4日
*/
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AccountHolder {
	
	/**
	 * Individual: 个人账户持有人信息；当AccountHolderType为CRS100时此项目为强制，且报告中不得包含Organisation和控制人
	 * Organisation： 机构账户持有人信息：当AccountHolderType非CRS100时此项目为强制，且报告中不得包含Individual
	 */
	@XmlElements(value={
		@XmlElement(name="Organisation", namespace=NameSpaces.cncrs,type=Organisation.class),
		@XmlElement(name="Individual", namespace=NameSpaces.cncrs,type=Individual.class)
	})
	private Object IndividualAndOrganisation;

	
	public AccountHolder() {
		super();
	}
	public AccountHolder(Object individualAndOrganisation) {
		super();
		IndividualAndOrganisation = individualAndOrganisation;
	}

	public Object getIndividualAndOrganisation() {
		return IndividualAndOrganisation;
	}
	public void setIndividualAndOrganisation(Object individualAndOrganisation) {
		IndividualAndOrganisation = individualAndOrganisation;
	}
	
	
}
