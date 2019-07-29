package com.fotic.management.sms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 短信模板有关的实体类
 *
 */
@Entity
@Table(name="RHZX_SMS_MODULE")
public class SmsModule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="APP_SEQUENCE")  
	@SequenceGenerator(name="APP_SEQUENCE",sequenceName="APP_SEQUENCE")
	private Integer id;//模板id
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String smsModuleName;//模板名称
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String smsModuleType;//模板类型
	@Transient
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String smsModuleTypeName;//模板类型
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String smsModuleContent;//模板内容
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String operatorId;//登记人id
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String operator;//操作人名称
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String status;//状态 0 新增后未操作过   1 启动   2 停用
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date createTime;//登记时间
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date updateTime;//修改时间  只有修改状态是会更新此时间，修改模板内容不更新此时间
	
	public SmsModule() {
		super();
	}
	
	public SmsModule(Integer id, String smsModuleName, String smsModuleType, String smsModuleTypeName,
			String smsModuleContent, String operatorId, String operator, String status, Date createTime,
			Date updateTime) {
		super();
		this.id = id;
		this.smsModuleName = smsModuleName;
		this.smsModuleType = smsModuleType;
		this.smsModuleTypeName = smsModuleTypeName;
		this.smsModuleContent = smsModuleContent;
		this.operatorId = operatorId;
		this.operator = operator;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSmsModuleName() {
		return smsModuleName;
	}
	public void setSmsModuleName(String smsModuleName) {
		this.smsModuleName = smsModuleName;
	}
	public String getSmsModuleType() {
		return smsModuleType;
	}
	public void setSmsModuleType(String smsModuleType) {
		this.smsModuleType = smsModuleType;
	}
	public String getSmsModuleContent() {
		return smsModuleContent;
	}
	public void setSmsModuleContent(String smsModuleContent) {
		this.smsModuleContent = smsModuleContent;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getSmsModuleTypeName() {
		return smsModuleTypeName;
	}
	public void setSmsModuleTypeName(String smsModuleTypeName) {
		this.smsModuleTypeName = smsModuleTypeName;
	}

	@Override
	public String toString() {
		return "SmsModule [id=" + id + ", smsModuleName=" + smsModuleName + ", smsModuleType=" + smsModuleType
				+ ", smsModuleTypeName=" + smsModuleTypeName + ", smsModuleContent=" + smsModuleContent
				+ ", operatorId=" + operatorId + ", operator=" + operator + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	
	
	

	
}
