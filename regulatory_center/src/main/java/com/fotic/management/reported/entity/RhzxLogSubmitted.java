package com.fotic.management.reported.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 上报日志表
 * 
 * @author 顾亚玺
 */
@Entity
@Table(name = "rhzx_log_submitted")
public class RhzxLogSubmitted {
//	@SequenceGenerator(name="",sequenceName="",allocationSize = 1)
	private String seq;// 序号
	
	private Date bussDate;// 日期
	
	private String dataSrc;// 数据来源
	
	private Integer dataCont;// 数据总条数
	
	private Integer dataRigthCont;// 数据正确条数
	
	private Integer dataErrCont;// 数据错误条数
	
	private String userCode;// 操作人
	
	private Date opDate;// 操作日期
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Date getBussDate() {
		return bussDate;
	}

	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public Integer getDataCont() {
		return dataCont;
	}

	public void setDataCont(Integer dataCont) {
		this.dataCont = dataCont;
	}

	public Integer getDataRigthCont() {
		return dataRigthCont;
	}

	public void setDataRigthCont(Integer dataRigthCont) {
		this.dataRigthCont = dataRigthCont;
	}

	public Integer getDataErrCont() {
		return dataErrCont;
	}

	public void setDataErrCont(Integer dataErrCont) {
		this.dataErrCont = dataErrCont;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}
}