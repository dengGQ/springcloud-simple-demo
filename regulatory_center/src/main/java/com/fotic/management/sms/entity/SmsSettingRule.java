package com.fotic.management.sms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 短信规则配置
 *
 */
@Entity
@Table(name="RHZX_SMS_SETTING_RULE")
public class SmsSettingRule {
	
	private Integer id;//主键id
	private Integer moduleId;//短信模板id
	private String coOrgCode;//合作机构号
	private String projId;//项目id
	private String prodId; //产品Id
	private Date createTime;//创建时间（登记时间）
	private Date updateTime;//修改时间    
	
	
	public SmsSettingRule() {
		super();
	}
	public SmsSettingRule(Integer id, Integer moduleId, String coOrgCode, String projId, Date createTime,
			Date updateTime, String prodId) {
		super();
		this.id = id;
		this.moduleId = moduleId;
		this.coOrgCode = coOrgCode;
		this.projId = projId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.prodId = prodId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getCoOrgCode() {
		return coOrgCode;
	}
	public void setCoOrgCode(String coOrgCode) {
		this.coOrgCode = coOrgCode;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	@Override
	public String toString() {
		return "SmsSettingRule [id=" + id + ", moduleId=" + moduleId + ", coOrgCode=" + coOrgCode + ", projId=" + projId
				+ ", prodId=" + prodId + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
}
