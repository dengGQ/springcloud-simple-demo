package com.fotic.management.publicquery.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="V_MFS_PROJECT")
public class Project {
	private String projId;

    private String projName;

    private String coOrgCode;

    private String coOrgName;

    private String conNo;

    private String projNatr;

    private String funcClass;

    private String rcptAllotMode;

    private String rcptAllotFreq;

    private Date predPubStartDate;

    private Date predPubEndDate;

    private Date projFoundDate;

    private Date projValidDate;

    private Date projPredEndDate;

    private String projState;

    private Date startDate;

    private Date endDate;
    
    @Transient
    private Integer level = 2;
    
	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
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

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getProjNatr() {
		return projNatr;
	}

	public void setProjNatr(String projNatr) {
		this.projNatr = projNatr;
	}

	public String getFuncClass() {
		return funcClass;
	}

	public void setFuncClass(String funcClass) {
		this.funcClass = funcClass;
	}

	public String getRcptAllotMode() {
		return rcptAllotMode;
	}

	public void setRcptAllotMode(String rcptAllotMode) {
		this.rcptAllotMode = rcptAllotMode;
	}

	public String getRcptAllotFreq() {
		return rcptAllotFreq;
	}

	public void setRcptAllotFreq(String rcptAllotFreq) {
		this.rcptAllotFreq = rcptAllotFreq;
	}

	public Date getPredPubStartDate() {
		return predPubStartDate;
	}

	public void setPredPubStartDate(Date predPubStartDate) {
		this.predPubStartDate = predPubStartDate;
	}

	public Date getPredPubEndDate() {
		return predPubEndDate;
	}

	public void setPredPubEndDate(Date predPubEndDate) {
		this.predPubEndDate = predPubEndDate;
	}

	public Date getProjFoundDate() {
		return projFoundDate;
	}

	public void setProjFoundDate(Date projFoundDate) {
		this.projFoundDate = projFoundDate;
	}

	public Date getProjValidDate() {
		return projValidDate;
	}

	public void setProjValidDate(Date projValidDate) {
		this.projValidDate = projValidDate;
	}

	public Date getProjPredEndDate() {
		return projPredEndDate;
	}

	public void setProjPredEndDate(Date projPredEndDate) {
		this.projPredEndDate = projPredEndDate;
	}

	public String getProjState() {
		return projState;
	}

	public void setProjState(String projState) {
		this.projState = projState;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}