package com.fotic.it.support.signature.model;

import com.fotic.it.support.signature.dao.entity.SignatureInfo;
/**
 * @Author: mfh
 * @Date: 2019-06-19 15:27
 **/
public class SignatureContainer {
    private SignatureRule signatureRule;
    private SignatureInfo signatureInfo;

    public SignatureContainer() {
    }

    public SignatureContainer(SignatureRule signatureRule, SignatureInfo signatureInfo) {
        this.signatureRule = signatureRule;
        this.signatureInfo = signatureInfo;
    }

    public String getSealType() {
        return signatureRule.getSealType();
    }

    public String getSealPositionByText() {
        return signatureRule.getSealPositionByText();
    }

    public int[] getSealPositionPagesArray() {
        return this.signatureRule.getSealPositionPagesArray();
    }

    public String getKeyNumber() {
        return this.signatureInfo.getKeynumber();
    }

    public String getKeyPwd() {
        return this.signatureInfo.getKeypwd();
    }

    public String getSignatureName() {
        return this.signatureInfo.getSignaturename();
    }

    public Integer getSealIndex() {
        return this.signatureRule.getSealIndex();
    }
}
