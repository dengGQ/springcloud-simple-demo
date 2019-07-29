package com.fotic.management.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月19日 下午2:44:45 
 * @version 1.0 * @parameter  
 */
@Entity
@Table(name = "RHZX_PER_REBUY_BAK")
public class RhzxRebuy {
	private String	coOrgCode	;//
	private String	conNo	;//
	private Integer	projId	;//
	private Date	buyBackDate	;//
	private BigDecimal	pendBuyBackPrnpl	;//
	private BigDecimal	pendBuyBackIntes	;//
	private BigDecimal	pendBuyBackPnlty	;//
	private BigDecimal	buyBackAmt	;//
	private String	buyBackState	;//
	private Date	etlDate	;//
	private Date	modifDate	;//
	private String	modifFlag	;//
	private String	runFlag	;//
	private String	hisModifFlag	;//
	private String	dataSrc	;//
	@Transient
	private String coOrgName;//
	@Transient
	private String projName;//
	public String getCoOrgCode() {
		return coOrgCode;
	}
	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
	}
	public Integer getProjId() {
		return projId;
	}
	public void setProjId(Integer projId) {
		this.projId = projId;
	}
	public Date getBuyBackDate() {
		return buyBackDate;
	}
	public void setBuyBackDate(Date buyBackDate) {
		this.buyBackDate = buyBackDate;
	}
	public BigDecimal getPendBuyBackPrnpl() {
		return pendBuyBackPrnpl;
	}
	public void setPendBuyBackPrnpl(BigDecimal pendBuyBackPrnpl) {
		this.pendBuyBackPrnpl = pendBuyBackPrnpl;
	}
	public BigDecimal getPendBuyBackIntes() {
		return pendBuyBackIntes;
	}
	public void setPendBuyBackIntes(BigDecimal pendBuyBackIntes) {
		this.pendBuyBackIntes = pendBuyBackIntes;
	}
	public BigDecimal getPendBuyBackPnlty() {
		return pendBuyBackPnlty;
	}
	public void setPendBuyBackPnlty(BigDecimal pendBuyBackPnlty) {
		this.pendBuyBackPnlty = pendBuyBackPnlty;
	}
	public BigDecimal getBuyBackAmt() {
		return buyBackAmt;
	}
	public void setBuyBackAmt(BigDecimal buyBackAmt) {
		this.buyBackAmt = buyBackAmt;
	}
	public String getBuyBackState() {
		return buyBackState;
	}
	public void setBuyBackState(String buyBackState) {
		this.buyBackState = buyBackState;
	}
	public Date getEtlDate() {
		return etlDate;
	}
	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}
	public Date getModifDate() {
		return modifDate;
	}
	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}
	public String getModifFlag() {
		return modifFlag;
	}
	public void setModifFlag(String modifFlag) {
		this.modifFlag = modifFlag;
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
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getCoOrgName() {
		return coOrgName;
	}
	public void setCoOrgName(String coOrgName) {
		this.coOrgName = coOrgName;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
}
