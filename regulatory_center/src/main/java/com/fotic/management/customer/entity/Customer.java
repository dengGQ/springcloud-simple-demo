package com.fotic.management.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 顾亚玺
 * @date 创建时间：2017年7月17日
 * @version 1.0 * @parameter
 */
@Entity
@Table(name = "RHZX_PER_CUSTOM")
public class Customer {
	private String credType;// 证件类型
	private String credCode;// 证件号
	private String custName;// 客户名
	private String custType;// 客户类型
	private String sex;// 性别
	private String tel;// 手机号码
	private Date birthday;// 出生日期
	private String marryStatus;// 婚姻状况
	private String hiestEdu;// 最高学历
	private String highestDegre;// 最高学位
	private String moble;// 手机号码
	private String email;// email
	private String houseTel;// 住宅电话
	private String postCode;// 通讯邮编
	private String postAddr;// 通讯地址
	private String houseRegst;// 户籍归属
	private String housePostCode;// 住宅邮编
	private String houseAdd;// 住宅地址
	private String livStatus;// 居住状况
	private String monthIncom;// 月收入（元）
	private String mateName;// 配偶名称
	private String mateCredType;// 配偶证件类型
	private String mateCredNo;// 配偶证件号码
	private String mateWorkUnit;// 配偶工作单位
	private String mateTel;// 配偶联系电话
	private String profes;// 职业
	private String workUnitName;// 工作单位名称
	private String workUnitIndOwned;// 工作单位所属行业
	private String workUnitPostCode;// 工作单位邮编
	private String workUnitAdd;// 工作单位地址
	private String duty;// 职务
	private String title;// 职称
	private String startWorkYear;// 工作起始年份
	private String workState;// 工作状态
	private Date etlDate;// 业务日期
	private Date modifDate;// 修改日期
	private String modifFlag;// 修改标识
	private String runFlag;// 运行标识
	private String hisModifFlag;// 历史修改标识
	private String dataSrc;// 数据来源

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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getHiestEdu() {
		return hiestEdu;
	}

	public void setHiestEdu(String hiestEdu) {
		this.hiestEdu = hiestEdu;
	}

	public String getHighestDegre() {
		return highestDegre;
	}

	public void setHighestDegre(String highestDegre) {
		this.highestDegre = highestDegre;
	}

	public String getMoble() {
		return moble;
	}

	public void setMoble(String moble) {
		this.moble = moble;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHouseTel() {
		return houseTel;
	}

	public void setHouseTel(String houseTel) {
		this.houseTel = houseTel;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostAddr() {
		return postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getHouseRegst() {
		return houseRegst;
	}

	public void setHouseRegst(String houseRegst) {
		this.houseRegst = houseRegst;
	}

	public String getHousePostCode() {
		return housePostCode;
	}

	public void setHousePostCode(String housePostCode) {
		this.housePostCode = housePostCode;
	}

	public String getHouseAdd() {
		return houseAdd;
	}

	public void setHouseAdd(String houseAdd) {
		this.houseAdd = houseAdd;
	}

	public String getLivStatus() {
		return livStatus;
	}

	public void setLivStatus(String livStatus) {
		this.livStatus = livStatus;
	}

	public String getMonthIncom() {
		return monthIncom;
	}

	public void setMonthIncom(String monthIncom) {
		this.monthIncom = monthIncom;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public String getMateCredType() {
		return mateCredType;
	}

	public void setMateCredType(String mateCredType) {
		this.mateCredType = mateCredType;
	}

	public String getMateCredNo() {
		return mateCredNo;
	}

	public void setMateCredNo(String mateCredNo) {
		this.mateCredNo = mateCredNo;
	}

	public String getMateWorkUnit() {
		return mateWorkUnit;
	}

	public void setMateWorkUnit(String mateWorkUnit) {
		this.mateWorkUnit = mateWorkUnit;
	}

	public String getMateTel() {
		return mateTel;
	}

	public void setMateTel(String mateTel) {
		this.mateTel = mateTel;
	}

	public String getProfes() {
		return profes;
	}

	public void setProfes(String profes) {
		this.profes = profes;
	}

	public String getWorkUnitName() {
		return workUnitName;
	}

	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}

	public String getWorkUnitIndOwned() {
		return workUnitIndOwned;
	}

	public void setWorkUnitIndOwned(String workUnitIndOwned) {
		this.workUnitIndOwned = workUnitIndOwned;
	}

	public String getWorkUnitPostCode() {
		return workUnitPostCode;
	}

	public void setWorkUnitPostCode(String workUnitPostCode) {
		this.workUnitPostCode = workUnitPostCode;
	}

	public String getWorkUnitAdd() {
		return workUnitAdd;
	}

	public void setWorkUnitAdd(String workUnitAdd) {
		this.workUnitAdd = workUnitAdd;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartWorkYear() {
		return startWorkYear;
	}

	public void setStartWorkYear(String startWorkYear) {
		this.startWorkYear = startWorkYear;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
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

}
