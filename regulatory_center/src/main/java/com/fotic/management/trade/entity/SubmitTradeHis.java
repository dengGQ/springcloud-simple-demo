package com.fotic.management.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="RHZX_SUBMT_PER_TRADE_HIS")
public class SubmitTradeHis {
    private String name;

    private String certtype;

    private String certno;

    private String deptcode;

    private String generaltype;

    private String type;

    private String account;

    private String tradeid;

    private String areacode;

    private Date dateopened;

    private Date dateclosed;

    private String currency;

    private BigDecimal creditlimit;

    private BigDecimal shareaccount;

    private BigDecimal maxdebt;

    private Long guaranteeway;

    private String termsfreq;

    private Long monthduration;

    private Long monthunpaid;

    private Date billingdate;

    private Date recentpaydate;

    private Long scheduledamount;

    private Long actualpayamount;

    private Long balance;

    private Long curtermspastdue;

    private Long amountpastdue;

    private Long amountpastdue30;

    private Long amountpastdue60;

    private Long amountpastdue90;

    private Long apastdue180;

    private Long termspastdue;

    private Long maxtermspastdue;

    private Long class5stat;

    private Long accountstat;

    private String paystat24month;

    private Date bussDate;

    private String dataStatus;

    private Date insertDttm;

    private String dataSrc;

    private Long jobBatchNo;

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

    public String getGeneraltype() {
        return generaltype;
    }

    public void setGeneraltype(String generaltype) {
        this.generaltype = generaltype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date getDateopened() {
        return dateopened;
    }

    public void setDateopened(Date dateopened) {
        this.dateopened = dateopened;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date getDateclosed() {
        return dateclosed;
    }

    public void setDateclosed(Date dateclosed) {
        this.dateclosed = dateclosed;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(BigDecimal creditlimit) {
        this.creditlimit = creditlimit;
    }

    public BigDecimal getShareaccount() {
        return shareaccount;
    }

    public void setShareaccount(BigDecimal shareaccount) {
        this.shareaccount = shareaccount;
    }

    public BigDecimal getMaxdebt() {
        return maxdebt;
    }

    public void setMaxdebt(BigDecimal maxdebt) {
        this.maxdebt = maxdebt;
    }

    public Long getGuaranteeway() {
        return guaranteeway;
    }

    public void setGuaranteeway(Long guaranteeway) {
        this.guaranteeway = guaranteeway;
    }

    public String getTermsfreq() {
        return termsfreq;
    }

    public void setTermsfreq(String termsfreq) {
        this.termsfreq = termsfreq;
    }

    public Long getMonthduration() {
        return monthduration;
    }

    public void setMonthduration(Long monthduration) {
        this.monthduration = monthduration;
    }

    public Long getMonthunpaid() {
        return monthunpaid;
    }

    public void setMonthunpaid(Long monthunpaid) {
        this.monthunpaid = monthunpaid;
    }

    public Date getBillingdate() {
        return billingdate;
    }

    public void setBillingdate(Date billingdate) {
        this.billingdate = billingdate;
    }

    public Date getRecentpaydate() {
        return recentpaydate;
    }

    public void setRecentpaydate(Date recentpaydate) {
        this.recentpaydate = recentpaydate;
    }

    public Long getScheduledamount() {
        return scheduledamount;
    }

    public void setScheduledamount(Long scheduledamount) {
        this.scheduledamount = scheduledamount;
    }

    public Long getActualpayamount() {
        return actualpayamount;
    }

    public void setActualpayamount(Long actualpayamount) {
        this.actualpayamount = actualpayamount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCurtermspastdue() {
        return curtermspastdue;
    }

    public void setCurtermspastdue(Long curtermspastdue) {
        this.curtermspastdue = curtermspastdue;
    }

    public Long getAmountpastdue() {
        return amountpastdue;
    }

    public void setAmountpastdue(Long amountpastdue) {
        this.amountpastdue = amountpastdue;
    }

    public Long getAmountpastdue30() {
        return amountpastdue30;
    }

    public void setAmountpastdue30(Long amountpastdue30) {
        this.amountpastdue30 = amountpastdue30;
    }

    public Long getAmountpastdue60() {
        return amountpastdue60;
    }

    public void setAmountpastdue60(Long amountpastdue60) {
        this.amountpastdue60 = amountpastdue60;
    }

    public Long getAmountpastdue90() {
        return amountpastdue90;
    }

    public void setAmountpastdue90(Long amountpastdue90) {
        this.amountpastdue90 = amountpastdue90;
    }

    public Long getApastdue180() {
        return apastdue180;
    }

    public void setApastdue180(Long apastdue180) {
        this.apastdue180 = apastdue180;
    }

    public Long getTermspastdue() {
        return termspastdue;
    }

    public void setTermspastdue(Long termspastdue) {
        this.termspastdue = termspastdue;
    }

    public Long getMaxtermspastdue() {
        return maxtermspastdue;
    }

    public void setMaxtermspastdue(Long maxtermspastdue) {
        this.maxtermspastdue = maxtermspastdue;
    }

    public Long getClass5stat() {
        return class5stat;
    }

    public void setClass5stat(Long class5stat) {
        this.class5stat = class5stat;
    }

    public Long getAccountstat() {
        return accountstat;
    }

    public void setAccountstat(Long accountstat) {
        this.accountstat = accountstat;
    }

    public String getPaystat24month() {
        return paystat24month;
    }

    public void setPaystat24month(String paystat24month) {
        this.paystat24month = paystat24month;
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

	public Long getJobBatchNo() {
		return jobBatchNo;
	}

	public void setJobBatchNo(Long jobBatchNo) {
		this.jobBatchNo = jobBatchNo;
	}

}