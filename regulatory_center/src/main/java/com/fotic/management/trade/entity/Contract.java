package com.fotic.management.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 顾亚玺
 * @date 创建时间：2017年7月18日
 * @version 1.0 * @parameter
 */
@Entity
@Table(name = "RHZX_PER_CONTRACT")
public class Contract {
	private String conNo;// 合同号
	private String coOrgCode;// 合作机构号
	private String credType;// 证件类型
	private String credCode;// 证件号码
	private Date conStartDate;// 开始日期
	private Date conEndDate;// 到期日期
	private Integer loanTerm;// 贷款期限
	private BigDecimal loanAmt;// 贷款金额
	private BigDecimal loanBlanc;// 贷款余额
	private String guarMode;// 担保方式
	private String curry;// 币种
	private BigDecimal loanMonthRate;// 贷款月利率
	private BigDecimal ovdueRate;// 逾期利率
	private String loanState;// 贷款状态
	private String fiveCalss;// 五级分类状态
	private String iouNo;// 借据号
	private String repayFreq;// 还款频率
	private String graceType;// 宽限类型
	private Integer graceDay;// 宽限期限
	private Integer projId;// 信托项目编号
	private String prodCode;// 产品号
	private Date etlDate;// 业务日期
	private Date modifDate;// 修改日期
	private String modifFlag;// 数据来源
	private String runFlag;// 运行标识
	private String hisModifFlag;// 历史修改标识
	private String dataSrc;// 数据来源
	@Transient
	private String orgName;//合作机构名称

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

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
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

	public String getGuarMode() {
		return guarMode;
	}

	public void setGuarMode(String guarMode) {
		this.guarMode = guarMode;
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

	public Integer getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
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
		if("1".equals(dataSrc)) {
			dataSrc = "信贷";
		}else if("2".equals(dataSrc)) {
			dataSrc = "CSV";
		}else {
			dataSrc = "未知";
		}
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
