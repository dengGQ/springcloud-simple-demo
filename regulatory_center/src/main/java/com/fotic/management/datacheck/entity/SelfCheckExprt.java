package com.fotic.management.datacheck.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="RHZX_SUBMT_Q_CHECK_FILE") 
public class SelfCheckExprt {
	
	@Column(name="SEQ_ID")
	private String seqId;
	
	@Column(name="QUATR")
	private String quatr;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="BUSS_DATE")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
	private Date bussDate;

	public SelfCheckExprt() {
		super();
	}

	
	@Override
	public String toString() {
		return "SelfCheckExprt [seqId=" + seqId + ", quatr=" + quatr + ", fileName=" + fileName + ", bussDate=" + bussDate
				+ "]";
	}
	

	public String getSeqId() {
		return seqId;
	}


	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}


	public String getQuatr() {
		return quatr;
	}

	public void setQuatr(String quatr) {
		this.quatr = quatr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Date getBussDate() {
		return bussDate;
	}

	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}

	
}
