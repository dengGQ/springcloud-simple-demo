package com.fotic.management.errormessage.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月14日 下午2:47:32 
 * @version 1.0 * @parameter  
 */
@Entity
@Table(name = "RHZX_CHECK_RESULT")
public class CheckResult {
	
	private Long jobBatchNo; //  批次号
	private String	ruleNo;     //	校验编号
	private Date	checkDate;  //	检查日期
	private String	checkType;  //	检查目标类型
	private String	dataSrc;    //	数据来源
	private String	coOrgCode;      //	机构代码
	private String	conNo; //	合同号  
	private String  iouNo;//    
	private String	credType;   //	证件类型
	private String	credCode;     //	证件号码
	private String	termNo;     //	期次号
	private String	tableName;  //	检查表名
	private String  columnName;//
	private String  columnCname;//
	private String  columnValue;//
	private String	ruleDesc;   //	交易规则描述 
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckType() {
		if("1".equals(checkType)){
			checkType = "交易信息";
		}else if("2".equals(checkType)){
			checkType = "客户信息";
		}else if("3".equals(checkType)){
			checkType = "特殊交易信息";
		}else {
			checkType = "未知";
		}
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
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
	
	public String getCredType() {
		if("0".equals(credType)){
			credType = "身份证";
		}else if("1".equals(credType)){
			credType = "户口簿";
		}else if("2".equals(credType)){
			credType = "护照";
		}else if("3".equals(credType)){
			credType = "军官证";
		}else if("4".equals(credType)){
			credType = "士兵证";
		}else if("5".equals(credType)){
			credType = "港澳居民来往内地通行证";
		}else if("6".equals(credType)){
			credType = "台湾同胞来往内地通行证";
		}else if("7".equals(credType)){
			credType = "临时身份证";
		}else if("8".equals(credType)){
			credType = "外国人居留证";
		}else if("9".equals(credType)){
			credType = "警官证";
		}else if("X".equals(credType)){
			credType = "其他证件";
		}else {
			credType = "未知";
		}
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public Long getJobBatchNo() {
		return jobBatchNo;
	}
	public void setJobBatchNo(Long jobBatchNo) {
		this.jobBatchNo = jobBatchNo;
	}
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnCname() {
		return columnCname;
	}
	public void setColumnCname(String columnCname) {
		this.columnCname = columnCname;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
	
}
