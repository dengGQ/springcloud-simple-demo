package com.dgq.quartz.util;

public enum StatusEnum {

	WAITING(0, "等待"),
	PAUSED(1, "暂停"),
	ACQUIRED(2, "正常执行"),
	BLOCKED(3, "阻塞"),
	ERROR(4, "错误");
	
	private Integer code;
	private String desc;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	StatusEnum(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}
}
