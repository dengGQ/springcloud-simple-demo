package com.dgq.crs.xml.bean;

/*
* @Description: 校验结果
* @author dgq 
* @date 2018年12月11日
*/
public class CheckResult {
	
	/**
	 * 账号
	 */
	private String accountNumber;

	/**
	 * 说明，校验失败数据的失败原因
	 */
	private String desc;

	public CheckResult(String desc, String accountNumber) {
		super();
		this.accountNumber = accountNumber;
		this.desc = desc;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public String getDesc() {
		return desc;
	}

}
