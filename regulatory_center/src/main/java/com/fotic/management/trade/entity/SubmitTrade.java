package com.fotic.management.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="RHZX_SUBMT_PER_TRADE")
public class SubmitTrade {
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

    private Integer guaranteeway;

    private String termsfreq;

    private Integer monthduration;

    private Integer monthunpaid;

    private Date billingdate;

    private Date recentpaydate;

    private BigDecimal scheduledamount;

    private BigDecimal actualpayamount;

    private BigDecimal balance;

    private Integer curtermspastdue;

    private BigDecimal amountpastdue;

    private BigDecimal amountpastdue30;

    private BigDecimal amountpastdue60;

    private BigDecimal amountpastdue90;

    private BigDecimal apastdue180;

    private Integer termspastdue;

    private Integer maxtermspastdue;

    private Integer class5stat;

    private Integer accountstat;

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

    public Integer getGuaranteeway() {
		return guaranteeway;
	}

	public void setGuaranteeway(Integer guaranteeway) {
		this.guaranteeway = guaranteeway;
	}

	public String getTermsfreq() {
		return termsfreq;
	}

	public void setTermsfreq(String termsfreq) {
		this.termsfreq = termsfreq;
	}

	public Integer getMonthduration() {
		return monthduration;
	}

	public void setMonthduration(Integer monthduration) {
		this.monthduration = monthduration;
	}

	public Integer getMonthunpaid() {
		return monthunpaid;
	}

	public void setMonthunpaid(Integer monthunpaid) {
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

	public BigDecimal getScheduledamount() {
		return scheduledamount;
	}

	public void setScheduledamount(BigDecimal scheduledamount) {
		this.scheduledamount = scheduledamount;
	}

	public BigDecimal getActualpayamount() {
		return actualpayamount;
	}

	public void setActualpayamount(BigDecimal actualpayamount) {
		this.actualpayamount = actualpayamount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getCurtermspastdue() {
		return curtermspastdue;
	}

	public void setCurtermspastdue(Integer curtermspastdue) {
		this.curtermspastdue = curtermspastdue;
	}

	public BigDecimal getAmountpastdue() {
		return amountpastdue;
	}

	public void setAmountpastdue(BigDecimal amountpastdue) {
		this.amountpastdue = amountpastdue;
	}

	public BigDecimal getAmountpastdue30() {
		return amountpastdue30;
	}

	public void setAmountpastdue30(BigDecimal amountpastdue30) {
		this.amountpastdue30 = amountpastdue30;
	}

	public BigDecimal getAmountpastdue60() {
		return amountpastdue60;
	}

	public void setAmountpastdue60(BigDecimal amountpastdue60) {
		this.amountpastdue60 = amountpastdue60;
	}

	public BigDecimal getAmountpastdue90() {
		return amountpastdue90;
	}

	public void setAmountpastdue90(BigDecimal amountpastdue90) {
		this.amountpastdue90 = amountpastdue90;
	}

	public BigDecimal getApastdue180() {
		return apastdue180;
	}

	public void setApastdue180(BigDecimal apastdue180) {
		this.apastdue180 = apastdue180;
	}

	public Integer getTermspastdue() {
		return termspastdue;
	}

	public void setTermspastdue(Integer termspastdue) {
		this.termspastdue = termspastdue;
	}

	public Integer getMaxtermspastdue() {
		return maxtermspastdue;
	}

	public void setMaxtermspastdue(Integer maxtermspastdue) {
		this.maxtermspastdue = maxtermspastdue;
	}

	public Integer getClass5stat() {
		return class5stat;
	}

	public void setClass5stat(Integer class5stat) {
		this.class5stat = class5stat;
	}

	public Integer getAccountstat() {
		return accountstat;
	}

	public void setAccountstat(Integer accountstat) {
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