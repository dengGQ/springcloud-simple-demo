package com.fotic.it.support.signature.model;

/**
 * @Author: mfh
 * @Date: 2019-06-13 17:01
 **/
public class FtpInfo {
    private String address;
    private String name;
    private String pwd;
    private int port = 21;
    public FtpInfo() {
    }

    public FtpInfo(String address, String name, String pwd, int port) {
        this.address = address;
        this.name = name;
        this.pwd = pwd;
        this.port = port;
    }

    public FtpInfo(String address, String name, String pwd) {
        this.address = address;
        this.name = name;
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
