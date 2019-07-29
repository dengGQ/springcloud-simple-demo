package com.fotic.management.customer.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户家庭住址信息
 * @author GYX
 */
@Entity
@Table(name="RHZX_SUBMT_PER_ADDRESS")
public class SubmitAddress {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 证件类型
	 */
	@Column(name="certtype")
	private String certType;
	/**
	 * 证件号码
	 */
	@Column(name="certno")
	private String certNo;
	/**
	 * 数据发生机构代码
	 */
	@Column(name="deptcode")
	private String deptCode;
	/**
	 * 居住地址
	 */
	private String residence;
	/**
	 * 居住地址邮政编码
	 */
	@Column(name="reszip")
	private String resZip;
	/**
	 * 居住状况
	 */
	@Column(name="rescondition")
	private String resCondition;
	/**
	 * 数据业务日期
	 */
	@Column(name="buss_date")
	private Date bussDate;
	/**
	 * 数据检验状态
	 */
	@Column(name="data_status")
	private String dataStatus;
	/**
	 * 数据生成时间
	 */
	@Column(name="insert_dttm")
	private Date insertDttm;
	/**
	 * 数据来源
	 */
	@Column(name="data_src")
	private String dataSrc;
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
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getResZip() {
		return resZip;
	}
	public void setResZip(String resZip) {
		this.resZip = resZip;
	}
	public String getResCondition() {
		return resCondition;
	}
	public void setResCondition(String resCondition) {
		this.resCondition = resCondition;
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
	 
}
