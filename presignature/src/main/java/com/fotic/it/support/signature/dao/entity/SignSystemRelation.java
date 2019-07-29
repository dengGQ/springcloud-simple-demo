package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;

/**
 * (SignSystemRelation)实体类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 16:58:46
 */
public class SignSystemRelation implements Serializable {
    private static final long serialVersionUID = -90639346871690137L;
    
    private Integer sid;
    
    private Integer sysid;


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getSysid() {
        return sysid;
    }

    public void setSysid(Integer sysid) {
        this.sysid = sysid;
    }

}