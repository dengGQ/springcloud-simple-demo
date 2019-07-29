package com.fotic.management.creditinfo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity  
@Table(name="RHZX_LOG_CSV_LOAD") 
public class RhzxCsvLoadLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RHZX_JOB_BATCH_NO")  
	@SequenceGenerator(name="SEQ_RHZX_JOB_BATCH_NO",sequenceName="SEQ_RHZX_JOB_BATCH_NO")
    private Long id;
	
    /**
     * 文件名字
     */
	@Column(name="FIFLD_NAME")
    private String fifldName;
	/**
	 * 原始文件名
	 */
    @Column(name="OLD_FIFLD_NAME")
    private String oldFifldName;
    /**
     * FTP路径
     */
    @Column(name="FTP_URL")
    private String ftpUrl;
    /**
     * 操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") 
    @Column(name="LOAD_DATE")
    private Date loadDate;
    /**
     * 操作员
     */
    @Column(name="USER_CODE")
    private String userCode;
    /**
     * 状态
     */
    @Column(name="STATE")
    private String state;
    
    /**
     * 导入的方式：0单文件导入 1批量导入
     */
    @Column(name="import_type")
    private String importType;
    
    @Transient
    private String name;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFifldName() {
		return fifldName;
	}
	public void setFifldName(String fifldName) {
		this.fifldName = fifldName;
	}
	public String getOldFifldName() {
		return oldFifldName;
	}
	public void setOldFifldName(String oldFifldName) {
		this.oldFifldName = oldFifldName;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	public Date getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getImportType() {
		return importType;
	}
	public void setImportType(String importType) {
		this.importType = importType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
    
	 
    
     
}