package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (SignatureLogInfo)实体类
 *
 * @author makejava-word2pdf
 * @since 2019-06-17 10:35:01
 */
public class SignatureLogInfo implements Serializable {
    private static final long serialVersionUID = -89222903544995597L;
    
    private Long id;
    
    private Long sid;
    
    private String flag;
    
    private String message;
    
    private int signconfid;
    
    private Date calltime;
    
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSignconfid() {
        return signconfid;
    }

    public void setSignconfid(int signconfid) {
        this.signconfid = signconfid;
    }

    public Date getCalltime() {
        return calltime;
    }

    public void setCalltime(Date calltime) {
        this.calltime = calltime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}