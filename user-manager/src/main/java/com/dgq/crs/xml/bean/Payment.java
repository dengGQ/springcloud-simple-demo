package com.dgq.crs.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;

/*
* @Description: 收入
* @author dgq 
* @date 2018年6月4日
*/

@XmlType(propOrder= {"paymentType", "paymentAmnt"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Payment {
	
	@XmlElement(name="PaymentType", namespace=NameSpaces.cncrs)
	private String paymentType;//收入类型  固定值：CRS501：股息; CRS502：利息; CRS503：销售或赎回金融资产总收入; CRS504:其他
	
	@XmlElement(name="PaymentAmnt", namespace=NameSpaces.cncrs)
	private CommonElement paymentAmnt;

	public Payment() {
		super();
	}

	public Payment(String paymentType, CommonElement paymentAmnt) {
		super();
		this.paymentType = paymentType;
		this.paymentAmnt = paymentAmnt;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public CommonElement getPaymentAmnt() {
		return paymentAmnt;
	}

	public void setPaymentAmnt(CommonElement paymentAmnt) {
		this.paymentAmnt = paymentAmnt;
	}
}
