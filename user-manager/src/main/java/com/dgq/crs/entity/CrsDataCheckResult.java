package com.dgq.crs.entity;
/*
* @Description: public class CrsDataCheckResult{ }
* @author dgq 
* @date 2019年4月24日
*/
public class CrsDataCheckResult {
	private String accountNumber;
	private String errData;
	private String colCname;
	private String ruleTypeName;
	private String qualDexDesc;
	private String errCheckName;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getErrData() {
		return errData;
	}
	public void setErrData(String errData) {
		this.errData = errData;
	}
	public String getColCname() {
		return colCname;
	}
	public void setColCname(String colCname) {
		this.colCname = colCname;
	}
	public String getRuleTypeName() {
		return ruleTypeName;
	}
	public void setRuleTypeName(String ruleTypeName) {
		this.ruleTypeName = ruleTypeName;
	}
	public String getQualDexDesc() {
		return qualDexDesc;
	}
	public void setQualDexDesc(String qualDexDesc) {
		this.qualDexDesc = qualDexDesc;
	}
	public String getErrCheckName() {
		return errCheckName;
	}
	public void setErrCheckName(String errCheckName) {
		this.errCheckName = errCheckName;
	}
}
