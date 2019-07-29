package com.fotic.management.creditinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 小微实际还款信息—实体类
 * 
 * @author Yaxi
 *
 */
@Entity
@Table(name = "RHZX_PER_ACTUAL_REPAY")
public class ActualRepay {
	private String id;//序列号
	
	private String conNo;//合同编号
	
	private int termNo;//期次号
	
	private String coOrgCode;//机构编码
	
	private Date actualRepayDate;//实际还款日期
	
	private BigDecimal actualAmt;//实际还款金额
	
	private BigDecimal actualPrnpl;//实际还款本金
	
	private BigDecimal actualIntes;//实际还款利息
	
	private BigDecimal intesPnlty;//罚息金额
	
	private BigDecimal leavePicpl;//剩余本金
	
	private Date etlDate;//业务日期
	
	private String modifFlag;//修改标识
	
	private Date modifDate;//修改日期
	
	private String runFlag;//处理标识 
	
	private String hisModifFlag;//历史修改标识
	
	private String dataSrc;//数据来源

	
	public BigDecimal getIntesPnlty() {
		return intesPnlty;
	}

	public void setIntesPnlty(BigDecimal intesPnlty) {
		this.intesPnlty = intesPnlty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getModifDate() {
		return modifDate;
	}

	public String getCoOrgCode() {
		return coOrgCode;
	}

	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}

	public int getTermNo() {
		return termNo;
	}

	public void setTermNo(int termNo) {
		this.termNo = termNo;
	}

	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public Date getActualRepayDate() {
		return actualRepayDate;
	}

	public void setActualRepayDate(Date actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}

	public BigDecimal getActualAmt() {
		return actualAmt;
	}

	public void setActualAmt(BigDecimal actualAmt) {
		this.actualAmt = actualAmt;
	}

	public BigDecimal getActualPrnpl() {
		return actualPrnpl;
	}

	public void setActualPrnpl(BigDecimal actualPrnpl) {
		this.actualPrnpl = actualPrnpl;
	}

	public BigDecimal getActualIntes() {
		return actualIntes;
	}

	public void setActualIntes(BigDecimal actualIntes) {
		this.actualIntes = actualIntes;
	}

	public BigDecimal getLeavePicpl() {
		return leavePicpl;
	}

	public void setLeavePicpl(BigDecimal leavePicpl) {
		this.leavePicpl = leavePicpl;
	}


	public Date getEtlDate() {
		return etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getRunFlag() {
		return runFlag;
	}

	public void setRunFlag(String runFlag) {
		this.runFlag = runFlag;
	}

	public String getHisModifFlag() {
		return hisModifFlag;
	}

	public void setHisModifFlag(String hisModifFlag) {
		this.hisModifFlag = hisModifFlag;
	}

	public String getModifFlag() {
		return modifFlag;
	}

	public void setModifFlag(String modifFlag) {
		this.modifFlag = modifFlag;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
}
