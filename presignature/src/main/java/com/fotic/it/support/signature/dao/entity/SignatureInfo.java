package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;

/**
 * (SignatureInfo)实体类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:05:06
 */
public class SignatureInfo implements Serializable {
    private static final long serialVersionUID = 391760392199985639L;
    
    private Integer id;
    
    private Integer signid;
    
    private String keynumber;
    
    private String keypwd;
    
    private String signno;
    
    private String signaturename;
    
    private String holder;
    
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSignid() {
        return signid;
    }

    public void setSignid(Integer signid) {
        this.signid = signid;
    }

    public String getKeynumber() {
        return keynumber;
    }

    public void setKeynumber(String keynumber) {
        this.keynumber = keynumber;
    }

    public String getKeypwd() {
        return keypwd;
    }

    public void setKeypwd(String keypwd) {
        this.keypwd = keypwd;
    }

    public String getSignno() {
        return signno;
    }

    public void setSignno(String signno) {
        this.signno = signno;
    }

    public String getSignaturename() {
        return signaturename;
    }

    public void setSignaturename(String signaturename) {
        this.signaturename = signaturename;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}