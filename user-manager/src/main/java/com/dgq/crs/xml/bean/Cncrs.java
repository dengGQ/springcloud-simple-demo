package com.dgq.crs.xml.bean;
/*
* @Description: public class Cncrs{ }
* @author dgq 
* @date 2018年6月4日
*/

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/**
 * 
 * @author Administrator
 *	@XmlAccessorType: 用于指定由java对象生成xml文件时对java对象的访问方式
 *		XmlAccessType.FIELD： 对象中的所有成员变量
 *		XmlAccessType.PROPERTY：对象中所有使用getter/setter方式访问的成员变量
 *		XmlAccessType.PUBLIC_MEMBER：默认；对象中所有的public访问权限的成员变量
 *				和通过getter/setter方式访问的成员变量；对于设置了public权限的getter/setter方法的成员变量
 *				就不要再private变量上使用@XmlAttribute和@XmlElement注解，否则会报错。
 *		XmlAccessType.NONE：对象的所有属性都不映射为xml的元素
 */
@XmlRootElement(name="CNCRS", namespace=NameSpaces.cncrs)
@XmlType(propOrder = {"mh", "accountReport"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Cncrs {
	
	@XmlAttribute(name="version")
	private static String version = "1.0";
	
	@XmlElement(name="MessageHeader", namespace=NameSpaces.cncrs)
	private MessageHeader mh;
	
	@XmlElementWrapper(name="ReportingGroup",namespace=NameSpaces.cncrs)
	@XmlElement(name="AccountReport", namespace=NameSpaces.cncrs)
	private List<AccountReport> accountReport;

	public Cncrs(MessageHeader mh, List<AccountReport> accountReport) {
		super();
		this.mh = mh;
		this.accountReport = accountReport;
	}

	public Cncrs() {
		super();
	}
	
	public MessageHeader getMh() {
		return mh;
	}
	public void setMh(MessageHeader mh) {
		this.mh = mh;
	}
	
	
	public List<AccountReport> getAccountReport() {
		return accountReport;
	}
	public void setAccountReport(List<AccountReport> accountReport) {
		this.accountReport = accountReport;
	}
}
