package com.fotic.management.publicquery.entity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="V_MFS_CONTRACT")
public class RhzxContract {
	private String conNo;

    private String coOrgCode;

    private String coOrgName;

    private String credType;

    private String credCode;

    private Date conStartDate;

    private Date conEndDate;

    private Long loanTerm;

    private BigDecimal loanAmt;

    private BigDecimal loanBlanc;

    private String curry;

    private BigDecimal loanMonthRate;

    private BigDecimal ovdueRate;

    private String loanState;

    private String fiveCalss;

    private String iouNo;

    private String repayFreq;

    private String graceType;

    private Long graceDay;

    private Long projId;

    private String prodName;

    private String prodCode;

    private String projName;

    public String getConNo() {
        return conNo;
    }

    public void setConNo(String conNo) {
        this.conNo = conNo;
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

    public String getCredType() {
        return credType;
    }

    public void setCredType(String credType) {
        this.credType = credType;
    }

    public String getCredCode() {
        return credCode;
    }

    public void setCredCode(String credCode) {
        this.credCode = credCode;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(Date conEndDate) {
        this.conEndDate = conEndDate;
    }

    public Long getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Long loanTerm) {
        this.loanTerm = loanTerm;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getLoanBlanc() {
        return loanBlanc;
    }

    public void setLoanBlanc(BigDecimal loanBlanc) {
        this.loanBlanc = loanBlanc;
    }

    public String getCurry() {
        return curry;
    }

    public void setCurry(String curry) {
        this.curry = curry;
    }

    public BigDecimal getLoanMonthRate() {
        return loanMonthRate;
    }

    public void setLoanMonthRate(BigDecimal loanMonthRate) {
        this.loanMonthRate = loanMonthRate;
    }

    public BigDecimal getOvdueRate() {
        return ovdueRate;
    }

    public void setOvdueRate(BigDecimal ovdueRate) {
        this.ovdueRate = ovdueRate;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getFiveCalss() {
        return fiveCalss;
    }

    public void setFiveCalss(String fiveCalss) {
        this.fiveCalss = fiveCalss;
    }

    public String getIouNo() {
        return iouNo;
    }

    public void setIouNo(String iouNo) {
        this.iouNo = iouNo;
    }

    public String getRepayFreq() {
        return repayFreq;
    }

    public void setRepayFreq(String repayFreq) {
        this.repayFreq = repayFreq;
    }

    public String getGraceType() {
        return graceType;
    }

    public void setGraceType(String graceType) {
        this.graceType = graceType;
    }

    public Long getGraceDay() {
        return graceDay;
    }

    public void setGraceDay(Long graceDay) {
        this.graceDay = graceDay;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

	    
}