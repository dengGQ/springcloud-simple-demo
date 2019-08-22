package com.dgq.crs.dto;

import java.util.List;

import com.dgq.crs.entity.DataEditLog;
import com.dgq.crs.entity.ReportTaxInfo;

/*
* @Description: public class ReportTaxInfoDto{ }
* @author dgq 
* @date 2019年3月25日
*/
public class ReportTaxInfoDto {
	
	private List<DataEditLog> logDataArr;
	
	private ReportTaxInfo updateData;
	
	private String accountNumber; 
	private String dataYear;
	private String reportStatus;
	private String errCode;
	private List<ReportTaxInfo> appointReportData;

	
	public ReportTaxInfoDto() {
		super();
	}

	public ReportTaxInfoDto(String accountNumber, String dataYear, String reportStatus, String errCode) {
		super();
		this.accountNumber = accountNumber;
		this.dataYear = dataYear;
		this.reportStatus = reportStatus;
		this.errCode = errCode;
	}

	public List<DataEditLog> getLogDataArr() {
		return logDataArr;
	}

	public void setLogDataArr(List<DataEditLog> logDataArr) {
		this.logDataArr = logDataArr;
	}

	public ReportTaxInfo getUpdateData() {
		return updateData;
	}

	public void setUpdateData(ReportTaxInfo updateData) {
		this.updateData = updateData;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDataYear() {
		return dataYear;
	}

	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public List<ReportTaxInfo> getAppointReportData() {
		return appointReportData;
	}

	public void setAppointReportData(List<ReportTaxInfo> appointReportData) {
		this.appointReportData = appointReportData;
	}
}
