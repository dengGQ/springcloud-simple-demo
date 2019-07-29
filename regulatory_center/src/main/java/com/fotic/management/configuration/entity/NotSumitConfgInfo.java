package com.fotic.management.configuration.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="V_RHZX_PER_NOT_SUMIT_INFO") 
public class NotSumitConfgInfo {
	
	  private Long projId;

	  private String projName;

	  private String coOrgCode;

	  private String coOrgName;

	  private String prodCode;

	  private String prodName;

	  private String conNo;
	 
	  private Date validDate;
	  
	  private String dataSrc;
	  
	    public String getDataSrc() {
	    	if("1".equals(dataSrc)) {
	    		dataSrc="信贷";
	    	}else if("2".equals(dataSrc)) {
	    		dataSrc="CSV";
	    	}else {
	    		dataSrc="其他";
	    	}
	    	return dataSrc;
	    }
		public void setDataSrc(String dataSrc) {
			this.dataSrc = dataSrc;
		}
		public Long getProjId() {
			return projId;
		}
		public void setProjId(Long projId) {
			this.projId = projId;
		}
		public String getProjName() {
			return projName;
		}
		public void setProjName(String projName) {
			this.projName = projName;
		}
		public String getCoOrgCode() {
			return coOrgCode;
		}
		public void setCoOrgCode(String coOrgCode) {
			this.coOrgCode = coOrgCode;
		}
		public String getCoOrgName() {
			return coOrgName;
		}
		public void setCoOrgName(String coOrgName) {
			this.coOrgName = coOrgName;
		}
		public String getProdCode() {
			return prodCode;
		}
		public void setProdCode(String prodCode) {
			this.prodCode = prodCode;
		}
		public String getProdName() {
			return prodName;
		}
		public void setProdName(String prodName) {
			this.prodName = prodName;
		}
		public String getConNo() {
			return conNo;
		}
		public void setConNo(String conNo) {
			this.conNo = conNo;
		}
		public Date getValidDate() {
			return validDate;
		}
		public void setValidDate(Date validDate) {
			this.validDate = validDate;
		}
	  
	
}