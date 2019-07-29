package com.fotic.it.support.signature.api.webservice;

import com.fotic.it.support.signature.model.ArrayOfSignatureRule;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * @Author: mfh
 * @Date: 2019-05-29 13:43
 **/
@WebService(name = "SignatureByRulesWebService", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService")
public interface SignatureByRulesWebService {
    /**
     * webservice 接口方法，调用方为 TA 系统
     * @param signatureRules    输出路径
     * @param inputFilePath     输入路径
     * @param outputFilePath    必须是已存在的路径且是文件夹，不能是文件
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return
     */
    @RequestWrapper(localName = "signatureByRules",
            targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService",
            className = "com.fotic.it.support.word2pdf.api.webservice.SignatureByRulesWebService.signatureByRules")
    @WebMethod(action = "urn:signatureByRules")
    @WebResult(name = "out", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService")
    @ResponseWrapper(localName = "signatureByRulesResponse",
            targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService/",
            className = "com.fotic.it.support.word2pdf.api.webservice.SignatureByRulesResponse")
    String signatureByRules(
            @WebParam(name = "in0", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService") ArrayOfSignatureRule[] signatureRules,
            @WebParam(name = "in1", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService") String inputFilePath,
            @WebParam(name = "in2", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService") String outputFilePath,
            @WebParam(name = "in3", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService") String sysName,
            @WebParam(name = "in4", targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService") String keyPwd);
}
