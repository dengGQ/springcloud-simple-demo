
package com.fotic.it.support.signature.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignatureRule", namespace = "http://bean.webservice.signature.fotic.com", propOrder = {
    "signatureRule"
})
public class ArrayOfSignatureRule {
    @XmlElement(namespace = "http://bean.webservice.signature.fotic.com", name = "SignatureRule", nillable = true)
    protected List<SignatureRule> signatureRule;


    public List<SignatureRule> getSignatureRule() {
        if (signatureRule == null) {
            signatureRule = new ArrayList<>();
        }
        return this.signatureRule;
    }

    @Override
    public String toString() {
        return "ArrayOfSignatureRule{" +
                "signatureRule=" + signatureRule.get(0) +
                '}';
    }
}
