package com.fotic.management.customer.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
* @Title: SubmitEmpHis.java 
* @Package com.fotic.management.customer.entity 
* @Description: 客户职业信息_历史
* @author 王明月   
* @date 2017年6月30日 下午3:27:00
 */
@Entity
@Table(name = "RHZX_SUBMT_PER_EMP_HIS")
public class SubmitEmpHis {
    private String name;

    private String certtype;

    private String certno;

    private String deptcode;

    private String occupation;

    private String company;

    private String industry;

    private String occaddress;

    private String occzip;

    private Long startyear;

    private Long duty;

    private Long caste;

    private Long annualincome;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date bussDate;

    private String dataStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOccaddress() {
        return occaddress;
    }

    public void setOccaddress(String occaddress) {
        this.occaddress = occaddress;
    }

    public String getOcczip() {
        return occzip;
    }

    public void setOcczip(String occzip) {
        this.occzip = occzip;
    }

    public Long getStartyear() {
        return startyear;
    }

    public void setStartyear(Long startyear) {
        this.startyear = startyear;
    }

    public Long getDuty() {
        return duty;
    }

    public void setDuty(Long duty) {
        this.duty = duty;
    }

    public Long getCaste() {
        return caste;
    }

    public void setCaste(Long caste) {
        this.caste = caste;
    }

    public Long getAnnualincome() {
        return annualincome;
    }

    public void setAnnualincome(Long annualincome) {
        this.annualincome = annualincome;
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

    public void setJobBatch(Long jobBatchNo) {
        this.jobBatchNo = jobBatchNo;
    }
}