//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fotic.it.support.signature.model;

import java.util.Date;

public class SignatureLogInfo {
    private Long id;
    private Long sid;
    private String flag;
    private String message;
    private Long signConfID;
    private Date callTime;
    private String remark;

    public SignatureLogInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return this.sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSignConfID() {
        return this.signConfID;
    }

    public void setSignConfID(Long signConfID) {
        this.signConfID = signConfID;
    }

    public Date getCallTime() {
        return this.callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
