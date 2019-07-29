
package com.fotic.it.support.signature.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureRule", namespace = "http://bean.webservice.signature.fotic.com", propOrder = {
		"sealIndex",
		"sealPositionByText",
		"sealPositionPages",
		"sealType"
})
public class SignatureRule {

	@XmlElement(namespace = "http://bean.webservice.signature.fotic.com", nillable = true)
	protected Integer sealIndex;
	@XmlElement(namespace = "http://bean.webservice.signature.fotic.com", nillable = true)
	protected String sealPositionByText;
	@XmlElement(namespace = "http://bean.webservice.signature.fotic.com", nillable = true)
	protected ArrayOfInt sealPositionPages;
	@XmlElement(namespace = "http://bean.webservice.signature.fotic.com", nillable = true)
	protected String sealType;

	/**
	 * 获取sealIndex属性的值。
	 *
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *
	 */
	public Integer getSealIndex() {
		return sealIndex;
	}

	/**
	 * 设置sealIndex属性的值。
	 *
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *
	 */
	public void setSealIndex(Integer value) {
		this.sealIndex = value;
	}

	/**
	 * 获取sealPositionByText属性的值。
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getSealPositionByText() {
		return sealPositionByText;
	}

	/**
	 * 设置sealPositionByText属性的值。
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setSealPositionByText(String value) {
		this.sealPositionByText = value;
	}

	/**
	 * 获取sealPositionPages属性的值。
	 *
	 * @return
	 *     possible object is
	 *     {@link ArrayOfInt }
	 *
	 */
	public ArrayOfInt getSealPositionPages() {
		return sealPositionPages;
	}

	public int[] getSealPositionPagesArray() {
		if (Objects.isNull(this.sealPositionPages)) {
			return new int[0];
		}
		return this.sealPositionPages.getArray();
	}
	/**
	 * 设置sealPositionPages属性的值。
	 *
	 * @param value
	 *     allowed object is
	 *     {@link ArrayOfInt }
	 *
	 */
	public void setSealPositionPages(ArrayOfInt value) {
		this.sealPositionPages = value;
	}

	/**
	 * 获取sealType属性的值。
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getSealType() {
		return sealType;
	}

	/**
	 * 设置sealType属性的值。
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setSealType(String value) {
		this.sealType = value;
	}

	@Override
	public String toString() {
		return "SignatureRule{" +
				"sealIndex=" + sealIndex +
				", sealPositionByText='" + sealPositionByText + '\'' +
				", sealPositionPages=" + sealPositionPages._int.get(0) +
				", sealType='" + sealType + '\'' +
				'}';
	}
}
