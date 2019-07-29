package com.fotic.management.reported.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 交易结果表实体
 * @author zhaoqh
 */
@Entity  
@Table(name="RHZX_SUBMT_PER_TRADE") 
public class SubmitPerTrade {
    /**
     * 姓名
     */
	@Column(name="NAME")
    private String name;
	 
    /**
     * 证件类型
     */
    @Column(name="CERTTYPE")
    private String certType;
 
    /**
     * 证件号码
     */
    @Column(name="CERTNO")
    private String certNo;
    /**
     * 数据检验状态
     */
    @Column(name="DATA_STATUS")
    private String dataStatus;
    
    @Column(name="CO_ORG_CODE")
    private String coOrgCode;
    
    public String getCoOrgCode() {
		return coOrgCode;
	}
	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}
	private String coOrgName;
    /**
     * 数据来源
     */
    @Column(name="DATA_SRC")
    private String dataSrc;
    public String getCoOrgName() {
		return coOrgName;
	}
	public void setCoOrgName(String coOrgName) {
		this.coOrgName = coOrgName;
	}
	/**
     * 数据业务日期
     */
    @Column(name="BUSS_DATE")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
    private Date bussDate;
    
    /**
     * 数据生成时间
     */
  /*  @Column(name="INSERT_DTTM")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")*/ 
    private String insertDttm;
    
    
    /**
     * 批次号
     */
    @Column(name="JOB_BATCH")
    private Integer jobBatch;
    
    //@Column(name="JOB_BATCH")
    private Integer curtermspastdue;
    
    public Integer getCurtermspastdue() {
		return curtermspastdue;
	}
	public void setCurtermspastdue(Integer curtermspastdue) {
		this.curtermspastdue = curtermspastdue;
	}
	/**
	 * 总条数
	 */
    @Transient
	private Integer allNum;
	
	/**
	 * 验证成功条数
	 */
    @Transient
	private Integer successNum;
	
	/**
	 * 验证失败条数
	 */
    @Transient
	private Integer failNum;
    
    /**
     * 待校验条数
     */
    @Transient
    private Integer waitingNum;
    
	public Integer getWaitingNum() {
		return waitingNum;
	}
	public void setWaitingNum(Integer waitingNum) {
		this.waitingNum = waitingNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getInsertDttm() {
		return insertDttm;
	}
	public void setInsertDttm(String insertDttm) {
		this.insertDttm = insertDttm;
	}
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public Date getBussDate() {
		return bussDate;
	}
	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}
     
}