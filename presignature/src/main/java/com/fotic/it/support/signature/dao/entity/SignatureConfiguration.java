package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (SignatureConfiguration)实体类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:09:32
 */
public class SignatureConfiguration implements Serializable {
    private static final long serialVersionUID = 208114947963949109L;
    
    private Integer id;
    
    private String signtype;
    
    private String signanchortext;
    
    private Integer signpage;
    
    private String signnumber;
    
    private String inputpath;
    
    private String outputpath;
    
    private Integer signcycle;
    
    private String signcyclename;
    
    private Date lasttime;
    
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getSignanchortext() {
        return signanchortext;
    }

    public void setSignanchortext(String signanchortext) {
        this.signanchortext = signanchortext;
    }

    public Integer getSignpage() {
        return signpage;
    }

    public void setSignpage(Integer signpage) {
        this.signpage = signpage;
    }

    public String getSignnumber() {
        return signnumber;
    }

    public void setSignnumber(String signnumber) {
        this.signnumber = signnumber;
    }

    public String getInputpath() {
        return inputpath;
    }

    public void setInputpath(String inputpath) {
        this.inputpath = inputpath;
    }

    public String getOutputpath() {
        return outputpath;
    }

    public void setOutputpath(String outputpath) {
        this.outputpath = outputpath;
    }

    public Integer getSigncycle() {
        return signcycle;
    }

    public void setSigncycle(Integer signcycle) {
        this.signcycle = signcycle;
    }

    public String getSigncyclename() {
        return signcyclename;
    }

    public void setSigncyclename(String signcyclename) {
        this.signcyclename = signcyclename;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}