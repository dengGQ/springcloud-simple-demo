package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;

/**
 * (SystemInfo)实体类
 *
 * @author makejava-ElectronicSignature
 * @since 2019-06-04 16:51:36
 */
public class SystemInfo implements Serializable {
    private static final long serialVersionUID = -57439659714985937L;
    
    private Integer id;
    
    private String appid;
    
    private String systemname;
    
    private String seccode;
    
    private String ip;
    
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}