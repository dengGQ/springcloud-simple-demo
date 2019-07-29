package com.dgq.quartz.commons.annotation;

public enum QueryTypeEnum {
	
	/**
	 * 等于
	 */
	EQUALS(" = "),
	/**
	 * 模糊查询
	 */
	LIKE(" like ");
	private String value;
	QueryTypeEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
