package com.dgq.crs.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/*
* @Description: public class dataEditLog{ }
* @author dgq 
* @date 2019年3月25日
*/
@Entity
@Table(name="reg.data_edit_log")
public class DataEditLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="SELECT APP_SEQUENCE.nextval from dual")
	private Integer id;
	
	/**
	 * 修改人ID
	 */
	private String userid;
	/**
	 * 修改明星
	 */
	private String operatorname;
	/**
	 * 账户
	 */
	private String accountNum;
	
	/**
	 * 数据所属公立年度
	 */
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date reportingPeriod;
	
	/**
	 * 修改的字段
	 */
	private String editFiled;
	
	/**
	 * 修改之前的值
	 */
	private String editBeforeValue;
	
	/**
	 * 修改之后的值
	 */
	private String editAfterValue;
	
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Timestamp updateDate;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	public Date getReportingPeriod() {
		return reportingPeriod;
	}

	public void setReportingPeriod(Date reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}

	public String getEditFiled() {
		return editFiled;
	}

	public void setEditFiled(String editFiled) {
		this.editFiled = editFiled;
	}

	public String getEditBeforeValue() {
		return editBeforeValue;
	}

	public void setEditBeforeValue(String editBeforeValue) {
		this.editBeforeValue = editBeforeValue;
	}

	public String getEditAfterValue() {
		return editAfterValue;
	}

	public void setEditAfterValue(String editAfterValue) {
		this.editAfterValue = editAfterValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
}
