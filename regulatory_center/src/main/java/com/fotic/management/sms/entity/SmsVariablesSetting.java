package com.fotic.management.sms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 宏变量配置表
 * @author admin
 *
 */
@Entity
@Table(name="RHZX_SMS_VARIABLES_SETTING")
public class SmsVariablesSetting {

	private Integer id;//主键id
	private String variablesName;//宏变量名称
	private String variablesField;//宏变量对应的字段
	private String variablesTable;//宏变量对应的表
	private String variablesQueryCondition;//宏变量查询具体的值需要的查询条件
	
	
	public SmsVariablesSetting() {
		super();
	}
	public SmsVariablesSetting(Integer id, String variablesName, String variablesField, String variablesTable,
			String variablesQueryCondition) {
		super();
		this.id = id;
		this.variablesName = variablesName;
		this.variablesField = variablesField;
		this.variablesTable = variablesTable;
		this.variablesQueryCondition = variablesQueryCondition;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVariablesName() {
		return variablesName;
	}
	public void setVariablesName(String variablesName) {
		this.variablesName = variablesName;
	}
	public String getVariablesField() {
		return variablesField;
	}
	public void setVariablesField(String variablesField) {
		this.variablesField = variablesField;
	}
	public String getVariablesTable() {
		return variablesTable;
	}
	public void setVariablesTable(String variablesTable) {
		this.variablesTable = variablesTable;
	}
	public String getVariablesQueryCondition() {
		return variablesQueryCondition;
	}
	public void setVariablesQueryCondition(String variablesQueryCondition) {
		this.variablesQueryCondition = variablesQueryCondition;
	}
	@Override
	public String toString() {
		return "SmsVariablesSetting [id=" + id + ", variablesName=" + variablesName + ", variablesField="
				+ variablesField + ", variablesTable=" + variablesTable + ", variablesQueryCondition="
				+ variablesQueryCondition + "]";
	}
	
}
