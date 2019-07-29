package com.fotic.management.configuration.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="RHZX_CFG_NOT_SUMIT_INFO") 
public class NotSumitConfg {
	private String ruleType;
	
    private String value;
    
    private String value_name;
    
    @Column(name="VALID_DATE")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
    private Date validDate;

    private String iUserCode;

    private String ifValid;

    
    @Column(name="INVLD_DATE")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
    private Date invldDate;

    private String dUserCode;
    
    private String dataSrc;

    public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getiUserCode() {
        return iUserCode;
    }

    public void setiUserCode(String iUserCode) {
        this.iUserCode = iUserCode;
    }

    public String getIfValid() {
        return ifValid;
    }

    public void setIfValid(String ifValid) {
        this.ifValid = ifValid;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
    public Date getInvldDate() {
        return invldDate;
    }

    public void setInvldDate(Date invldDate) {
        this.invldDate = invldDate;
    }

    public String getdUserCode() {
        return dUserCode;
    }

    public void setdUserCode(String dUserCode) {
        this.dUserCode = dUserCode;
    }

	public String getValue_name() {
		return value_name;
	}

	public void setValue_name(String value_name) {
		this.value_name = value_name;
	}

	

    
    
}
