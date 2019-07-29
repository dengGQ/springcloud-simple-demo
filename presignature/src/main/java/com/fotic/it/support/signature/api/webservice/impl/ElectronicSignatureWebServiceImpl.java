package com.fotic.it.support.signature.api.webservice.impl;

import com.fotic.it.support.signature.api.webservice.ElectronicSignatureWebService;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.entity.SystemInfo;
import com.fotic.it.support.signature.model.ArrayOfInt;
import com.fotic.it.support.signature.model.SignatureRule;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.fotic.it.support.signature.service.SignatureInfoService;
import com.fotic.it.support.signature.service.SignatureService;
import com.fotic.it.support.signature.service.SystemInfoService;
import com.fotic.it.support.signature.util.CommonUtil;
import com.fotic.it.support.signature.util.SignatureArgumentsValidateUtil;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author mfh
 */
@javax.jws.WebService(serviceName = "ElectronicSignatureWebService",
        targetNamespace = "http://xfire.webservice.com/ElectronicSignatureWebService",
        endpointInterface = "com.fotic.it.support.signature.api.webservice.ElectronicSignatureWebService")
@Component
public class ElectronicSignatureWebServiceImpl implements ElectronicSignatureWebService {
    @Resource
    private WebServiceContext webServiceContext;
    @Resource
    private SignatureInfoService signatureInfoService;
    @Resource
    private SystemInfoService systemInfoService;
    @Resource
    private SignatureService signatureService;
    @Resource
    private ElectronicsignatureInfoLogService electronicsignatureInfoLogService;
    private static Logger logger = LoggerFactory.getLogger(ElectronicSignatureWebServiceImpl.class);

    @Override
    public String electronicSignature(String sealType, String sealPositionByText, String sealIndex, String inputFilePath, String outputFilePath, String sysName, String keyPwd, Integer sealPositionPage) {
        printArgs(sealType, sealPositionByText, sealIndex, inputFilePath, outputFilePath, sysName, keyPwd, sealPositionPage);
        String verifyIsNullResult = this.verifyIsNull(inputFilePath, outputFilePath, sealIndex, sysName, keyPwd);
        if (!String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode()).equals(verifyIsNullResult)) {
            /* 原码值：verifyIsNullResult */
            return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        }
        if (SignatureArgumentsValidateUtil.verifySignatureType(sealType)) {
            /* 原码值：ILLEGAL_SIGNATURE_TYPE */
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(),
                    ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_SIGNATURE_TYPE.getCode()),
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));
        }

        if (SignatureArgumentsValidateUtil.containsNonNumbers(sealIndex)) {
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.ILLEGAL_SIGNATURE_NO.getCode(),
                    ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_SIGNATURE_NO.getCode()),
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));
        }

        List<SignatureInfo> signatureInfos = signatureInfoService.queryBySignId(Integer.valueOf(sealIndex));
        if (SignatureArgumentsValidateUtil.verifySignatureInfoId(signatureInfos, Integer.valueOf(sealIndex))) {
            /* 原码值：ERROR_SIGNATURE_INFO_DATA */
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(),
                    "签章信息有误，请根据 sealIndex：" + sealIndex + " 查询 SIGNATURE_INFO 表的 SIGNID 字段",
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));
        }

        SignatureInfo signatureInfo = signatureInfos.get(0);
        List<SystemInfo> systemInfos = systemInfoService.queryById(signatureInfo.getId());
        if (SignatureArgumentsValidateUtil.verifySystemInfoListSize(systemInfos, signatureInfo.getId()) ||
                SignatureArgumentsValidateUtil.verifySystemName(systemInfos, sysName, sealIndex)) {
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.NO_SYSTEM_PERMISSIONS.getCode(),
                    "根据 SIGNATURE_INFO 表的 ID :" + signatureInfo.getId() + "，间接（关联表为 SIGN_SYSTEM_RELATION，逻辑关系自行查询）查询表 SYSTEM_INFO 的 id 字段，结果 SystemInfo 集合为空",
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));
        }

        if (SignatureArgumentsValidateUtil.verifySecurityCode(systemInfos, keyPwd, sealIndex)) {
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.ERROR_SIGNATURE_SECURITY_CODE.getCode(),
                    ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ERROR_SIGNATURE_SECURITY_CODE.getCode()),
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));

        }
        if (verifyIp(systemInfos)) {
            return recordLogAndReturnStatusCode(ResultStatusCodeEnum.ACCESS_IP_UNAUTHORIZED.getCode(),
                    ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ACCESS_IP_UNAUTHORIZED.getCode()),
                    inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText,
                    sealIndex, String.valueOf(sealPositionPage));
        }
        if (CommonUtil.isFolder(inputFilePath)) {
            if (CommonUtil.isFolder(outputFilePath)) {
                if (signatureService.signatureFolder(inputFilePath, outputFilePath, buildSignatureRule(sealType, sealPositionByText, sealPositionPage), signatureInfo, sysName, keyPwd)) {
                    return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
                }
            } else {
                logger.error("输入路径 {} 为文件夹，输出路径 {} 必须也是文件夹", inputFilePath, outputFilePath);
                electronicsignatureInfoLogService.recordLog(ResultStatusCodeEnum.MISMATCH_PATH.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.MISMATCH_PATH.getCode()), inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, null, null, null, null);
                return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
            }
        } else if (CommonUtil.isPdf(inputFilePath)) {
            if (signatureService.signatureSingleFile(inputFilePath, outputFilePath, buildSignatureRule(sealType, sealPositionByText, sealPositionPage), signatureInfo, sysName, keyPwd)) {
                return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
            }
        } else {
            logger.error("签章文件类型必须为 pdf");
            electronicsignatureInfoLogService.recordLog(ResultStatusCodeEnum.ERROR_FILE_TYPE.getCode(), "签章文件类型必须为 pdf", inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, null, null, null, null);
            return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        }
        logger.info("输入参数为：{}，输出参数为：{} ，签章文件夹执行成功", inputFilePath, outputFilePath);
        return String.valueOf(ResultStatusCodeEnum.SUCCESS.getCode());
    }

    private String verifyIsNull(String inputFilePath, String outputFilePath, String sealIndex, String sysName, String keyPwd) {
        if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(inputFilePath)) {
            return recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_INPUT_PATH.getCode(), "输入文件路径为 null",
                    null, inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        }
        if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(outputFilePath)) {
            return recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_OUTPUT_PATH.getCode(), "输出文件路径为 null",
                    null, inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        }
        if (SignatureArgumentsValidateUtil.verifySealIndex(sealIndex)) {
            return recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_SIGNATURE_INDEX.getCode(), "签章编号为 null",
                    null, inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        }

        return String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode());
    }

    private String recordLogAndReturnStatusCode(int statusCode, String simpleInfo,
                                                String fileName, String inputPath, String outputPath,
                                                String sysName, String keyPwd, String signType, String signPosition, String sealIndex, String signPage) {
        electronicsignatureInfoLogService.recordLog(
                statusCode, simpleInfo,
                fileName, inputPath, outputPath,
                sysName, keyPwd, signType, signPosition, sealIndex, signPage);
        return String.valueOf(statusCode);
    }

    private void printArgs(String sealType, String sealPositionByText, String sealIndex, String inputFilePath, String outputFilePath, String sysName, String keyPwd, Integer sealPositionPage) {
        logger.info("进入 Webservice 接口方法 electronicSignature，输入文件路径：{}，输出文件路径：{}，系统名称：{}，安全码:{}，签章方式：{}，签章位置：{}，签章编号：{}，签章所在页：{}",
                inputFilePath, outputFilePath, sysName, keyPwd, sealType, sealPositionByText, sealIndex, sealPositionPage);

    }

    private SignatureRule buildSignatureRule(String sealType, String sealPositionByText, Integer sealPositionPage) {
        SignatureRule rule = new SignatureRule();
        rule.setSealType(sealType);
        rule.setSealPositionByText(sealPositionByText);
        rule.setSealPositionPages(buildArrayOfInt(sealPositionPage));
        return rule;
    }

    private ArrayOfInt buildArrayOfInt(Integer sealPositionPage) {
        ArrayOfInt arrayOfInt = new ArrayOfInt();
        List<Integer> list = new ArrayList<>();
        list.add(sealPositionPage);
        arrayOfInt.set_int(list);
        return arrayOfInt;
    }

    /**
     * 验证请求的客户端 IP 是否在系统中授权
     *
     * @param systemInfos 用于检查客户端请求 ip 是否包含在此集合对象中
     * @return 客户端请求 ip 是否包含在某一个集合对象中
     */
    private boolean verifyIp(List<SystemInfo> systemInfos) {
        String ip = this.getIp();
        for (SystemInfo systemInfo : systemInfos) {
            String ips = systemInfo.getIp();
            if (Objects.nonNull(ips) && ips.contains(ip)) {
                return false;
            }
        }
        logger.error("访问的IP地址没有在系统中授权");
        return true;
    }

    /**
     * 获取请求客户端的 IP
     *
     * @return 请求客户端的 IP
     */
    private String getIp() {
        MessageContext ctx = webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
        return request.getRemoteAddr();
    }
}
