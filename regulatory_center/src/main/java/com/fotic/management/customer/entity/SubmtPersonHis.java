package com.fotic.management.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户个人信息_历史
 * 
 * @author Yaxi
 *
 */
@Entity
@Table(name = "RHZX_SUBMT_PER_PERSON_HIS")
public class SubmtPersonHis {

	private String name; // 客户姓名
	private String certtype; // 证件类型
	private String certno; // 证件号码
	private String deptcode; // 数据发生机构代码
	private String gender; // 性别
	private Date birthday; // 出生日期
	private Integer marriage; // 婚姻状况
	private Integer edulevel; // 最高学历
	private Integer edudegree; // 最高学位
	private String spouse_name; // 配偶姓名
	private String spouse_certno; // 配偶证件号码
	private String spouse_certtype; // 配偶证件类型
	private String spouse_office; // 配偶工作单位
	private String spouse_tel; // 配偶联系电话
	private String hometel; // 住宅电话
	private String mobiletel; // 手机号码
	private String officetel; // 单位电话
	private String email; // 电子邮件地址
	private String address; // 通讯地址
	private String zip; // 通讯邮政编码
	private String residence; // 户籍地址
	private Date buss_date; // 数据业务日期
	private String data_status; // 数据检验状态
	private Date insert_dttm; // 数据生成时间
	private String data_src; // 数据来源
	private Integer job_batch_no; // 批次号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public Integer getEdulevel() {
		return edulevel;
	}

	public void setEdulevel(Integer edulevel) {
		this.edulevel = edulevel;
	}

	public Integer getEdudegree() {
		return edudegree;
	}

	public void setEdudegree(Integer edudegree) {
		this.edudegree = edudegree;
	}

	public String getSpouse_name() {
		return spouse_name;
	}

	public void setSpouse_name(String spouse_name) {
		this.spouse_name = spouse_name;
	}

	public String getSpouse_certno() {
		return spouse_certno;
	}

	public void setSpouse_certno(String spouse_certno) {
		this.spouse_certno = spouse_certno;
	}

	public String getSpouse_certtype() {
		return spouse_certtype;
	}

	public void setSpouse_certtype(String spouse_certtype) {
		this.spouse_certtype = spouse_certtype;
	}

	public String getSpouse_office() {
		return spouse_office;
	}

	public void setSpouse_office(String spouse_office) {
		this.spouse_office = spouse_office;
	}

	public String getSpouse_tel() {
		return spouse_tel;
	}

	public void setSpouse_tel(String spouse_tel) {
		this.spouse_tel = spouse_tel;
	}

	public String getHometel() {
		return hometel;
	}

	public void setHometel(String hometel) {
		this.hometel = hometel;
	}

	public String getMobiletel() {
		return mobiletel;
	}

	public void setMobiletel(String mobiletel) {
		this.mobiletel = mobiletel;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public Date getBuss_date() {
		return buss_date;
	}

	public void setBuss_date(Date buss_date) {
		this.buss_date = buss_date;
	}

	public String getData_status() {
		return data_status;
	}

	public void setData_status(String data_status) {
		this.data_status = data_status;
	}

	public Date getInsert_dttm() {
		return insert_dttm;
	}

	public void setInsert_dttm(Date insert_dttm) {
		this.insert_dttm = insert_dttm;
	}

	public String getData_src() {
		return data_src;
	}

	public void setData_src(String data_src) {
		this.data_src = data_src;
	}

	public Integer getJob_batch_no() {
		return job_batch_no;
	}

	public void setJob_batch_no(Integer job_batch_no) {
		this.job_batch_no = job_batch_no;
	}
}
