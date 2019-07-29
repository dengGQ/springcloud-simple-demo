package com.fotic.management.creditinfo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="V_RHZX_PER_TRADE_INFO") 
public class RhzxSubmtPerTrade {
	
	private String IOUNo;
	
	private String credNo;
	
	private String conNo;
	
	private String projName;
	
	private String projId;
	
	private String coOrgCode;
	
	private String coOrgName;
	
	private String dataSrc;
			       
	private String checkResult;
	
	private String credType;
	
	private String curtermspastdue;
	
	public String getCurtermspastdue() {
		return curtermspastdue;
	}

	public void setCurtermspastdue(String curtermspastdue) {
		this.curtermspastdue = curtermspastdue;
	}

	@Column(name="REPAY_DATA")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
    private Date repayData;

	private Integer loanAmt;
	
	

	public Integer getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(Integer loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getCredType() {
		return credType;
	}

	public void setCredType(String credType) {
		this.credType = credType;
	}

	public String getIOUNo() {
		return IOUNo;
	}

	public void setIOUNo(String iOUNo) {
		IOUNo = iOUNo;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getCoOrgCode() {
		return coOrgCode;
	}

	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}

	
	public String getCoOrgName() {
		return coOrgName;
	}

	public void setCoOrgName(String coOrgName) {
		this.coOrgName = coOrgName;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public Date getRepayData() {
		return repayData;
	}

	public void setRepayData(Date repayData) {
		this.repayData = repayData;
	}
	
}
