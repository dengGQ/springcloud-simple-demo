package com.fotic.management.publicquery.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="V_MFS_ORG")
public class RhzxOrg {
	private String coOrgCode;

    private String coOrgName;

    private String coOrgAbbr;

    private String coOrgType;

    private String acctType;

    private Date foundTime;

    private String buissLcnseNo;

    private Date buissLcnseRegstDate;

    private Date buissLcnseEndDate;

    private String stateTaxRegstCd;

    private String localTaxRegstCd;

    private String orgCode;

    private String orgCodeCredValidTerm;

    private String socalCredtCodeCred;

    private String loanCredNo;

    private BigDecimal etpseTotalAsset;

    private BigDecimal etpseTotalDebt;

    private BigDecimal regstCaptl;

    private BigDecimal recptCaptl;

    private String etpseType;

    private Date startDate;

    private Date endDate;
    
    @Transient
    private List<Project> projectList;
    
    @Transient
    private Integer level = 1;
    
/*    @Transient
    private List<RhzxProdure> productList;*/

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

	public String getCoOrgAbbr() {
		return coOrgAbbr;
	}

	public void setCoOrgAbbr(String coOrgAbbr) {
		this.coOrgAbbr = coOrgAbbr;
	}

	public String getCoOrgType() {
		return coOrgType;
	}

	public void setCoOrgType(String coOrgType) {
		this.coOrgType = coOrgType;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public Date getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}

	public String getBuissLcnseNo() {
		return buissLcnseNo;
	}

	public void setBuissLcnseNo(String buissLcnseNo) {
		this.buissLcnseNo = buissLcnseNo;
	}

	public Date getBuissLcnseRegstDate() {
		return buissLcnseRegstDate;
	}

	public void setBuissLcnseRegstDate(Date buissLcnseRegstDate) {
		this.buissLcnseRegstDate = buissLcnseRegstDate;
	}

	public Date getBuissLcnseEndDate() {
		return buissLcnseEndDate;
	}

	public void setBuissLcnseEndDate(Date buissLcnseEndDate) {
		this.buissLcnseEndDate = buissLcnseEndDate;
	}

	public String getStateTaxRegstCd() {
		return stateTaxRegstCd;
	}

	public void setStateTaxRegstCd(String stateTaxRegstCd) {
		this.stateTaxRegstCd = stateTaxRegstCd;
	}

	public String getLocalTaxRegstCd() {
		return localTaxRegstCd;
	}

	public void setLocalTaxRegstCd(String localTaxRegstCd) {
		this.localTaxRegstCd = localTaxRegstCd;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCodeCredValidTerm() {
		return orgCodeCredValidTerm;
	}

	public void setOrgCodeCredValidTerm(String orgCodeCredValidTerm) {
		this.orgCodeCredValidTerm = orgCodeCredValidTerm;
	}

	public String getSocalCredtCodeCred() {
		return socalCredtCodeCred;
	}

	public void setSocalCredtCodeCred(String socalCredtCodeCred) {
		this.socalCredtCodeCred = socalCredtCodeCred;
	}

	public String getLoanCredNo() {
		return loanCredNo;
	}

	public void setLoanCredNo(String loanCredNo) {
		this.loanCredNo = loanCredNo;
	}

	public BigDecimal getEtpseTotalAsset() {
		return etpseTotalAsset;
	}

	public void setEtpseTotalAsset(BigDecimal etpseTotalAsset) {
		this.etpseTotalAsset = etpseTotalAsset;
	}

	public BigDecimal getEtpseTotalDebt() {
		return etpseTotalDebt;
	}

	public void setEtpseTotalDebt(BigDecimal etpseTotalDebt) {
		this.etpseTotalDebt = etpseTotalDebt;
	}

	public BigDecimal getRegstCaptl() {
		return regstCaptl;
	}

	public void setRegstCaptl(BigDecimal regstCaptl) {
		this.regstCaptl = regstCaptl;
	}

	public BigDecimal getRecptCaptl() {
		return recptCaptl;
	}

	public void setRecptCaptl(BigDecimal recptCaptl) {
		this.recptCaptl = recptCaptl;
	}

	public String getEtpseType() {
		return etpseType;
	}

	public void setEtpseType(String etpseType) {
		this.etpseType = etpseType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	
	
}