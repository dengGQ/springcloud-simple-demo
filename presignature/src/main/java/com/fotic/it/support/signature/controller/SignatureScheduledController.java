package com.fotic.it.support.signature.controller;

import com.fotic.it.support.signature.service.SignatureScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mfh
 * @Date: 2019-06-24 8:59
 **/
@Controller
public class SignatureScheduledController {
    private SignatureScheduled signatureScheduled;

    @RequestMapping("/startSignatureScheduledTask")
    @ResponseBody
    public String startSignatureScheduledTask() {
        return signatureScheduled.startTask() ? "startSuccess" : "startFailure";
    }

    @RequestMapping("/stopSignatureScheduledTask")
    @ResponseBody
    public String stopSignatureScheduledTask() {
        return signatureScheduled.stopTask() ? "stopSuccess" : "stopFailure";
    }

    @Autowired
    public void setSignatureScheduled(SignatureScheduled signatureScheduled) {
        this.signatureScheduled = signatureScheduled;
    }
}
