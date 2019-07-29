package com.fotic.it.support.signature.api.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * @Author: mfh
 * @Date: 2019-05-29 11:25
 **/
@WebService(name = "ElectronicSignatureWebService", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService")
public interface ElectronicSignatureWebService {

    @RequestWrapper(localName = "electronicSignature",
            targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService",
            className = "com.fotic.it.support.word2pdf.api.webservice.ElectronicSignatureWebService.electronicSignature")
    @WebMethod(action = "urn:electronicSignature")
    @WebResult(name = "out", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService")
    @ResponseWrapper(localName = "electronicSignatureResponse",
            targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService/",
            className = "com.fotic.it.support.word2pdf.api.webservice.ElectronicSignatureResponse")
    String electronicSignature(
            @WebParam(name = "in0", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String sealType,
            @WebParam(name = "in1", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String sealPositionByText,
            @WebParam(name = "in2", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String sealIndex,
            @WebParam(name = "in3", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String inputFilePath,
            @WebParam(name = "in4", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String outputFilePath,
            @WebParam(name = "in5", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String sysName,
            @WebParam(name = "in6", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") String keyPwd,
            @WebParam(name = "in7", targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService") Integer sealPositionPage);
}
