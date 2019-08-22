package com.dgq.crs.xml.bean;
/*
* @Description: public class DocSpec{ }
* @author dgq 
* @date 2018年6月4日
*/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

@XmlType(propOrder = {"docRefId", "docTypeIndic", "corrDocRefId"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class DocSpec {
	
	@XmlElement(name="DocRefId", namespace=NameSpaces.stc)
	private String docRefId; //账户记录编号，不可重复；格式：CN+4位年份信息+14位金融机构注册码+9位数字序列号
	
	@XmlElement(name="DocTypeIndic", namespace=NameSpaces.stc)
	private String docTypeIndic;//账户报告的类型：取值：R1-R3, T1-T3

	@XmlElement(name="CorrDocRefId", namespace=NameSpaces.stc)
	private String corrDocRefId; //被修改会删除的账户记录编号， docTypeIndic为R1或T1时不得包含该字段
	
	public DocSpec() {
		super();
	}

	public DocSpec(String docRefId, String docTypeIndic) {
		super();
		this.docRefId = docRefId;
		this.docTypeIndic = docTypeIndic;
	}

	public DocSpec(String docRefId, String docTypeIndic, String corrDocRefId) {
		super();
		this.docRefId = docRefId;
		this.docTypeIndic = docTypeIndic;
		this.corrDocRefId = corrDocRefId;
	}

	public String getDocRefId() {
		return docRefId;
	}

	public void setDocRefId(String docRefId) {
		this.docRefId = docRefId;
	}

	public String getDocTypeIndic() {
		return docTypeIndic;
	}

	public void setDocTypeIndic(String docTypeIndic) {
		this.docTypeIndic = docTypeIndic;
	}

	public String getCorrDocRefId() {
		return corrDocRefId;
	}

	public void setCorrDocRefId(String corrDocRefId) {
		this.corrDocRefId = corrDocRefId;
	}
}
