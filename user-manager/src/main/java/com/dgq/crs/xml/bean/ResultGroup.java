package com.dgq.crs.xml.bean;
/*
* @Description: public class ResultGroup{ }
* @author dgq 
* @date 2019年6月21日
*/

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

@XmlType(propOrder = {"resultType", "accountErrReports"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class ResultGroup {
	
	@XmlElement(name="ResultType", namespace = NameSpaces.CNCRS_RES)
	private String resultType;
	
	@XmlElement(name="AccountErrReport", namespace = NameSpaces.CNCRS_RES)
	private List<AccountErrReport> accountErrReports;
	
	public ResultGroup() {
		super();
	}

	public ResultGroup(String resultType, List<AccountErrReport> accountErrReports) {
		super();
		this.resultType = resultType;
		this.accountErrReports = accountErrReports;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public List<AccountErrReport> getAccountErrReports() {
		return accountErrReports;
	}

	public void setAccountErrReports(List<AccountErrReport> accountErrReports) {
		this.accountErrReports = accountErrReports;
	}
}
