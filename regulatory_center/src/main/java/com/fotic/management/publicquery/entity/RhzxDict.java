package com.fotic.management.publicquery.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RHZX_DIM_COM_DICT")
public class RhzxDict {
	private  String	dictId		;
	private  String	dictName		;
	private  String	dictColName		;
	private  String	dictColChineseName		;
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getDictColName() {
		return dictColName;
	}
	public void setDictColName(String dictColName) {
		this.dictColName = dictColName;
	}
	public String getDictColChineseName() {
		return dictColChineseName;
	}
	public void setDictColChineseName(String dictColChineseName) {
		this.dictColChineseName = dictColChineseName;
	}
	
	

}