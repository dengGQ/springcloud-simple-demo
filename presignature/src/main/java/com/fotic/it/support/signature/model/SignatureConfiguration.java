//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fotic.it.support.signature.model;

import java.util.Date;

public class SignatureConfiguration {
    private Long id;
    private String signType;
    private String signAnchorText;
    private Integer signPage;
    private String signNumber;
    private String inputPath;
    private String outputPath;
    private Long signCycle;
    private String signCycleName;
    private Date lastTime;
    private String remark;

    public SignatureConfiguration() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignAnchorText() {
        return this.signAnchorText;
    }

    public void setSignAnchorText(String signAnchorText) {
        this.signAnchorText = signAnchorText;
    }

    public Integer getSignPage() {
        return this.signPage;
    }

    public void setSignPage(Integer signPage) {
        this.signPage = signPage;
    }

    public String getSignNumber() {
        return this.signNumber;
    }

    public void setSignNumber(String signNumber) {
        this.signNumber = signNumber;
    }

    public String getInputPath() {
        return this.inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Long getSignCycle() {
        return this.signCycle;
    }

    public void setSignCycle(Long signCycle) {
        this.signCycle = signCycle;
    }

    public String getSignCycleName() {
        return this.signCycleName;
    }

    public void setSignCycleName(String signCycleName) {
        this.signCycleName = signCycleName;
    }

    public Date getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
