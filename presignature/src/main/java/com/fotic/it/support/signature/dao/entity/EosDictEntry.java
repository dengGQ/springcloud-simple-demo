package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;

/**
 * (EosDictEntry)实体类
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-13 13:40:45
 */
public class EosDictEntry implements Serializable {
    private static final long serialVersionUID = -25642724162636412L;
    
    private String dicttypeid;
    
    private String dictid;
    
    private String dictname;
    
    private Double status;
    
    private Double sortno;
    
    private Double rank;
    
    private String parentid;
    
    private String seqno;
    
    private String filter1;
    
    private String filter2;


    public String getDicttypeid() {
        return dicttypeid;
    }

    public void setDicttypeid(String dicttypeid) {
        this.dicttypeid = dicttypeid;
    }

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
    }

    public Double getSortno() {
        return sortno;
    }

    public void setSortno(Double sortno) {
        this.sortno = sortno;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

}