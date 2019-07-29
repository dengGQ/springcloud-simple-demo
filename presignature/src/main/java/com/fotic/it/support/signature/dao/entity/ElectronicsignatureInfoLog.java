package com.fotic.it.support.signature.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 重构后电子签章日志(ElectronicsignatureInfoLog)实体类
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-04 15:55:08
 */
public class ElectronicsignatureInfoLog implements Serializable {
    private static final long serialVersionUID = 452274287019368240L;
    //主键ID
    private String id;
    //返回状态码
    private String code;
    //返回状态信息
    private String info;
    //文件名称
    private String filename;
    //输入路径
    private String inputPath;
    //输出路径
    private String outputPath;
    //系统来源
    private String sysName;
    //签章方式 0：文字定位 1：像素定位

    private String sealtype;
    //签章定位
    private String sealpositionbytext;
    //签章序号 必须为数字，统一分配：11
    private String sealindex;
    //安全码
    private String keypwd;
    //签章所在页
    private String sealpositionpage;
    //创建时间
    private Date createtime;
    //创建时间-前台展示用
    private String createTimeStr;
    //创建时间-开始时间-搜索用
    private String createDateStartStr;
    //创建时间-结束时间-搜索用
    private String createDateEndStr;
    //修改时间
    private Date modifytime;
    //修改时间-前台展示用
    private String modifyTimeStr;
    //扩展字段1
    private String extend1;
    //扩展字段2
    private String extend2;
    //扩展字段3
    private String extend3;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSealtype() {
        return sealtype;
    }

    public void setSealtype(String sealtype) {
        this.sealtype = sealtype;
    }

    public String getSealpositionbytext() {
        return sealpositionbytext;
    }

    public void setSealpositionbytext(String sealpositionbytext) {
        this.sealpositionbytext = sealpositionbytext;
    }

    public String getSealindex() {
        return sealindex;
    }

    public void setSealindex(String sealindex) {
        this.sealindex = sealindex;
    }

    public String getKeypwd() {
        return keypwd;
    }

    public void setKeypwd(String keypwd) {
        this.keypwd = keypwd;
    }

    public String getSealpositionpage() {
        return sealpositionpage;
    }

    public void setSealpositionpage(String sealpositionpage) {
        this.sealpositionpage = sealpositionpage;
    }

    public Object getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Object getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifyTimeStr() {
        return modifyTimeStr;
    }

    public void setModifyTimeStr(String modifyTimeStr) {
        this.modifyTimeStr = modifyTimeStr;
    }

    public String getCreateDateStartStr() {
        return createDateStartStr;
    }

    public void setCreateDateStartStr(String createDateStartStr) {
        this.createDateStartStr = createDateStartStr;
    }

    public String getCreateDateEndStr() {
        return createDateEndStr;
    }

    public void setCreateDateEndStr(String createDateEndStr) {
        this.createDateEndStr = createDateEndStr;
    }

    @Override
    public String toString() {
        return "ElectronicsignatureInfoLog{" +
                "id='" + id + '\'' +
                ", code=" + code +
                ", info='" + info + '\'' +
                ", filename='" + filename + '\'' +
                ", inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", sysName='" + sysName + '\'' +
                ", sealtype='" + sealtype + '\'' +
                ", sealpositionbytext='" + sealpositionbytext + '\'' +
                ", sealindex='" + sealindex + '\'' +
                ", keypwd='" + keypwd + '\'' +
                ", sealpositionpage='" + sealpositionpage + '\'' +
                ", createtime=" + createtime +
                ", modifytime=" + modifytime +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}