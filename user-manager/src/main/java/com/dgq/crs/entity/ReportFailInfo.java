package com.dgq.crs.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/*
* @Description: crs报送失败反馈ACD xml文件对应实体
* @author dgq 
* @date 2019年6月21日
*/
@Table(name="REG.REPORT_FAIL_INFO")
public class ReportFailInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="SELECT APP_SEQUENCE.nextval from dual")
	private Integer id;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String messageRefId;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String resultType;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String docRefId;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String errCode;
	
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date dataYear;
	
	@ColumnType(jdbcType=JdbcType.DATE)
	private LocalDateTime createTime;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String createUser;

	@Transient
	private String dictTwoLevel;
	@Transient
	private String dictName;
	
	public ReportFailInfo() {}
	
	public ReportFailInfo(String resultType, String docRefId, String errCode, LocalDateTime createTime,
			String createUser, Date dataYear, String messageRefId) {
		super();
		this.resultType = resultType;
		this.docRefId = docRefId;
		this.errCode = errCode;
		this.createTime = createTime;
		this.createUser = createUser;
		this.dataYear = dataYear;
		this.messageRefId = messageRefId;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getDocRefId() {
		return docRefId;
	}

	public void setDocRefId(String docRefId) {
		this.docRefId = docRefId;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDictTwoLevel() {
		return dictTwoLevel;
	}

	public void setDictTwoLevel(String dictTwoLevel) {
		this.dictTwoLevel = dictTwoLevel;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public Date getDataYear() {
		return dataYear;
	}

	public void setDataYear(Date dataYear) {
		this.dataYear = dataYear;
	}

	public String getMessageRefId() {
		return messageRefId;
	}

	public void setMessageRefId(String messageRefId) {
		this.messageRefId = messageRefId;
	}
}
