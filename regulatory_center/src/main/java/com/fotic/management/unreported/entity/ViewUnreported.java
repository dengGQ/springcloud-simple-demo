package com.fotic.management.unreported.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 应报未报视图实体
 * @author zhaoqh
 */
@Entity  
@Table(name="V_RHZX_PER_LACK_DATA") 
public class ViewUnreported {
    /**
     * 项目编号
     */
	@Column(name="proj_id")
    private String projId;
	
	/**
	 * 项目名称
	 */
	@Column(name="PROJ_NAME")
	private String projName;
	
	/**
	 * 机构编号
	 */
    @Column(name="CO_ORG_CODE")
    private String coOrgCode;
    /**
     * 机构名称
     */
    @Column(name="CO_ORG_NAME")
    private String coOrgName;
    /**
     * 产品号
     */
    @Column(name="PROD_CODE")
    private String prodCode;
    /**
     * 合同号
     */
    @Column(name="CON_NO")
    private String conNo;
    /**
     * 业务号
     */
    @Column(name="IOU_NO")
    private String iouNo;
    /**
     * 证件类型
     */
    @Column(name="CRED_TYPE")
    private String credType;
    /**
     * 证件类型名称
     */
    @Column(name="CRED_TYPE_NAME")
    private String credTypeName;
    /**
     * 证件号码
     */
    @Column(name="CRED_CODE")
    private String credCode;
    /**
     * 期次号
     */
    @Column(name="TERM_NO")
    private String termNo;
    /**
     * 应报日期  
     */
    @Column(name="REPAY_DATE")
    private String repayDate;
    /**
     * 数据来源
     */
    @Column(name="DATA_SRC")
    private String dataSrc;
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
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
	public String getCredType() {
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	public String getCredTypeName() {
		return credTypeName;
	}
	public void setCredTypeName(String credTypeName) {
		this.credTypeName = credTypeName;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getIouNo() {
		return iouNo;
	}
	public void setIouNo(String iouNo) {
		this.iouNo = iouNo;
	}
	public String getCredCode() {
		return credCode;
	}
	public void setCredCode(String credCode) {
		this.credCode = credCode;
	}
	 
    
    
    
     
}