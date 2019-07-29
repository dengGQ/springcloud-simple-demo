package com.fotic.common.httpclient;
/*
* @Description: public class HttpResult{ }
* @author dgq 
* @date 2018年4月20日
*/
public class HttpResult {
	
	/**
	 * 状态码
	 */
	private Integer status;
	
	/**
	 * 返回的数据
	 */
	private String data;

	public HttpResult(Integer status, String data) {
		this.status = status;
		this.data = data;
	}

	public HttpResult() {
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
}
