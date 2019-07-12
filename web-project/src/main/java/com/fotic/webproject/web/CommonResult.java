package com.fotic.webproject.web;

import com.fotic.webproject.exception.ExceptionCodeEnum;

public class CommonResult {

    private String code = "200";
    private String msg = "操作成功";

    private Object data;

    private CommonResult() {}
    public CommonResult(Object data) {
        this.data = data;
    }
    
    public static CommonResult build() {
    	return new CommonResult();
    }
    public static CommonResult build(Object data) {
    	return new CommonResult(data);
    }

    public CommonResult of(String msg) {
    	this.msg = msg;
    	return this;
    }
    public CommonResult of(String code, String msg) {
    	this.code = code;
    	this.msg = msg;
    	return this;
    }
    public CommonResult of(ExceptionCodeEnum tipMsgEnum) {
    	this.code = tipMsgEnum.getErrorCode();
    	this.msg = tipMsgEnum.getErrorMsg();
    	return this;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
