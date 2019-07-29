//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fotic.it.support.signature.model;

public class SignConfigurationInfo {
    private String sealServeType;
    private String sealServeVersion;
    private String sealServePath;
    private String ftpAddress;
    private Integer ftpPort;
    private String ftpUser;
    private String ftpPwd;

    public SignConfigurationInfo() {
    }

    public String getFtpAddress() {
        return this.ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public Integer getFtpPort() {
        return this.ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpPwd() {
        return this.ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getFtpUser() {
        return this.ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getSealServePath() {
        return this.sealServePath;
    }

    public void setSealServePath(String sealServePath) {
        this.sealServePath = sealServePath;
    }

    public String getSealServeType() {
        return this.sealServeType;
    }

    public void setSealServeType(String sealServeType) {
        this.sealServeType = sealServeType;
    }

    public String getSealServeVersion() {
        return this.sealServeVersion;
    }

    public void setSealServeVersion(String sealServeVersion) {
        this.sealServeVersion = sealServeVersion;
    }
}
