package com.fotic.management.publicquery.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "V_MFS_PRODUCT")
public class RhzxProdure {
	private String prodCode;

	private String coOrgCode;

	private String orgAcctType;

	private String prodName;

	private String prodRank;

	private Date prodValidDate;

	private String guarMode;

	private BigDecimal amtLowLmt;

	private BigDecimal amtHighLmt;

	private String termLowLmt;

	private String termHighLmt;

	private String prodState;

	private Date startDate;

	private Date endDate;
	
	@Transient
	private String coOrgName;
	
	@Transient
    private Integer level = 3;

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getCoOrgCode() {
		return coOrgCode;
	}

	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}

	public String getOrgAcctType() {
		return orgAcctType;
	}

	public void setOrgAcctType(String orgAcctType) {
		this.orgAcctType = orgAcctType;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdRank() {
		return prodRank;
	}

	public void setProdRank(String prodRank) {
		this.prodRank = prodRank;
	}

	public Date getProdValidDate() {
		return prodValidDate;
	}

	public void setProdValidDate(Date prodValidDate) {
		this.prodValidDate = prodValidDate;
	}

	public String getGuarMode() {
		return guarMode;
	}

	public void setGuarMode(String guarMode) {
		this.guarMode = guarMode;
	}

	public BigDecimal getAmtLowLmt() {
		return amtLowLmt;
	}

	public void setAmtLowLmt(BigDecimal amtLowLmt) {
		this.amtLowLmt = amtLowLmt;
	}

	public BigDecimal getAmtHighLmt() {
		return amtHighLmt;
	}

	public void setAmtHighLmt(BigDecimal amtHighLmt) {
		this.amtHighLmt = amtHighLmt;
	}

	public String getTermLowLmt() {
		return termLowLmt;
	}

	public void setTermLowLmt(String termLowLmt) {
		this.termLowLmt = termLowLmt;
	}

	public String getTermHighLmt() {
		return termHighLmt;
	}

	public void setTermHighLmt(String termHighLmt) {
		this.termHighLmt = termHighLmt;
	}

	public String getProdState() {
		return prodState;
	}

	public void setProdState(String prodState) {
		this.prodState = prodState;
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

	public String getCoOrgName() {
		return coOrgName;
	}

	public void setCoOrgName(String coOrgName) {
		this.coOrgName = coOrgName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}