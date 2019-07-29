package com.fotic.it.support.signature.model;

import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-06-26 9:22
 **/
public class ExInfo {
    private Integer statusCode;
    private String info;
    private Exception ex;

    public ExInfo(Integer statusCode, String info, Exception ex) {
        this.statusCode = statusCode;
        this.info = info;
        this.ex = ex;
    }

    public ExInfo(Integer statusCode, String info) {
        this.statusCode = statusCode;
        this.info = info;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public void printStackTrace() {
        if (Objects.nonNull(ex)) {
            ex.printStackTrace();
        }
    }

    public String getMessage() {
        if (Objects.nonNull(ex)) {
            return ex.getMessage();
        }
        return "无异常信息";
    }
}
