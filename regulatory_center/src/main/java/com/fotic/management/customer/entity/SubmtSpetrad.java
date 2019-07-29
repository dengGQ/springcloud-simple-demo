package com.fotic.management.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 特殊交易信息
 * 
 * @author Yaxi
 *
 */

@Entity
@Table(name = "RHZX_SUBMT_PER_SPETRADE")
public class SubmtSpetrad {
	private String account; // 业务号
	private String tradeid; // 交易ID
	private String deptcode; // 数据发生机构代码
	private Integer spectype; // 特殊交易类型
	private Date specdate; // 发生日期
	private Long specmonth; // 变更期数
	private Double specmoney; // 发生金额
	private String specdetail; // 明细记录
	private Date bussDate; // 数据业务日期
	private String dataStatus; // 数据检验状态
	private Date insertDttm; // 数据生成时间
	private String dataSrc; // 数据来源
	private Long jobBatchNo; // 批次号
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTradeid() {
		return tradeid;
	}
	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public Integer getSpectype() {
		return spectype;
	}
	public void setSpectype(Integer spectype) {
		this.spectype = spectype;
	}
	public Date getSpecdate() {
		return specdate;
	}
	public void setSpecdate(Date specdate) {
		this.specdate = specdate;
	}
	public Long getSpecmonth() {
		return specmonth;
	}
	public void setSpecmonth(Long specmonth) {
		this.specmonth = specmonth;
	}
	public Double getSpecmoney() {
		return specmoney;
	}
	public void setSpecmoney(Double specmoney) {
		this.specmoney = specmoney;
	}
	public String getSpecdetail() {
		return specdetail;
	}
	public void setSpecdetail(String specdetail) {
		this.specdetail = specdetail;
	}
	public Date getBussDate() {
		return bussDate;
	}
	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Date getInsertDttm() {
		return insertDttm;
	}
	public void setInsertDttm(Date insertDttm) {
		this.insertDttm = insertDttm;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public Long getJobBatchNo() {
		return jobBatchNo;
	}
	public void setJobBatchNo(Long jobBatchNo) {
		this.jobBatchNo = jobBatchNo;
	}
	
}
