package com.fotic.it.support.signature.api.webservice.impl;

import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.entity.SystemInfo;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.model.SignatureRule;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.fotic.it.support.signature.service.SignatureInfoService;
import com.fotic.it.support.signature.service.SignatureService;
import com.fotic.it.support.signature.service.SystemInfoService;
import com.fotic.it.support.signature.util.CommonUtil;
import com.fotic.it.support.signature.util.SignatureArgumentsValidateUtil;
import com.fotic.it.support.signature.api.webservice.SignatureByRulesWebService;
import com.fotic.it.support.signature.model.ArrayOfSignatureRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: mfh
 * @Date: 2019-06-04 13:21
 **/
@javax.jws.WebService(serviceName = "SignatureByRulesWebService",
        targetNamespace = "http://xfire.webservice.com/SignatureByRulesWebService",
        endpointInterface = "com.fotic.it.support.signature.api.webservice.SignatureByRulesWebService")
@Component
public class SignatureByRulesWebServiceImpl implements SignatureByRulesWebService {
    private static final Logger logger = LoggerFactory.getLogger(SignatureByRulesWebServiceImpl.class);
    @Resource
    private SignatureInfoService signatureInfoService;
    @Resource
    private SystemInfoService systemInfoService;
    @Resource
    private SignatureService signatureService;
    @Resource
    private ElectronicsignatureInfoLogService electronicsignatureInfoLogService;

    public SignatureByRulesWebServiceImpl() {
    }

    @Override
    public String signatureByRules(ArrayOfSignatureRule[] signatureRules,
                                   String inputFilePath, String outputFilePath,
                                   String sysName, String keyPwd) {
        printArgs(signatureRules, inputFilePath, outputFilePath, sysName, keyPwd);
        String verifyIsNullResult = this.verifyIsNull(signatureRules, inputFilePath, outputFilePath, sysName, keyPwd);
        if (!String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode()).equals(verifyIsNullResult)) {
            /* 原码值：verifyIsNullResult */
            return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        }
        String verifySignatureRuleResult = this.verifySignatureRule(signatureRules, inputFilePath, outputFilePath, sysName, keyPwd);
        if (!String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode()).equals(verifySignatureRuleResult)) {
            /* 原码值：verifySignatureRuleResult */
            return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        }
        if (CommonUtil.isFolder(inputFilePath)) {
            if (CommonUtil.isFolder(outputFilePath)) {
                if (this.signatureFolder(inputFilePath, outputFilePath, signatureRules, sysName, keyPwd)) {
                    return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
                }
            } else {
                logger.info("输入路径 {} 为文件夹，输出路径 {} 必须也是文件夹", inputFilePath, outputFilePath);
                electronicsignatureInfoLogService.recordLog(ResultStatusCodeEnum.MISMATCH_PATH.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.MISMATCH_PATH.getCode()), inputFilePath, inputFilePath, outputFilePath, sysName, keyPwd, null, null, null, null);
                return String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
            }
        } else if (CommonUtil.isPdf(inputFilePath)) {
            if (this.signatureSingleFile(inputFilePath, outputFilePath, signatureRules, sysName, keyPwd)) {
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

    private String verifyIsNull(ArrayOfSignatureRule[] signatureRules, String inputFilePath, String outputFilePath, String sysName, String keyPwd) {
        if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(inputFilePath)) {
            return this.recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_INPUT_PATH.getCode(), "输入文件路径为 null",
                    inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        }
        if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(outputFilePath) || SignatureArgumentsValidateUtil.verifySignatureRulesIsNull(signatureRules)) {
            return this.recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_OUTPUT_PATH.getCode(), "输出文件路径为 null",
                    inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        }
        return String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode());
    }

    private String verifySignatureRule(ArrayOfSignatureRule[] signatureRules, String inputFilePath, String outputFilePath, String sysName, String keyPwd) {
        List<SignatureRule> rules = getSignatures(signatureRules[0]);
        if (rules.isEmpty()) {
            return this.recordLogAndReturnStatusCode(
                    ResultStatusCodeEnum.ILLEGAL_SIGNATURE_RULES.getCode(), "签章规则对象集合为空",
                    inputFilePath, outputFilePath,
                    sysName, keyPwd, null, null, null, null);
        } else {
            for (SignatureRule rule : rules) {
                if (SignatureArgumentsValidateUtil.verifySignatureRule(rule)) {
                    return this.recordLogAndReturnStatusCode(
                            ResultStatusCodeEnum.ILLEGAL_ATTRIBUTE_VALUE.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_ATTRIBUTE_VALUE.getCode()),
                            inputFilePath, outputFilePath,
                            sysName, keyPwd, rule.getSealType(), rule.getSealPositionByText(), String.valueOf(rule.getSealIndex()), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
                }

                if (SignatureArgumentsValidateUtil.verifySignatureInfoId(signatureInfoService.queryBySignId(rule.getSealIndex()), rule.getSealIndex())) {
                    return this.recordLogAndReturnStatusCode(
                            ResultStatusCodeEnum.ERROR_SIGNATURE_INFO_DATA.getCode(), "签章信息有误，请根据 sealIndex：" + rule.getSealIndex() + " 查询 SIGNATURE_INFO 表的 SIGNID 字段",
                            inputFilePath, outputFilePath,
                            sysName, keyPwd, rule.getSealType(), rule.getSealPositionByText(), String.valueOf(rule.getSealIndex()), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
                }

                SignatureInfo signatureInfo = getSignatureInfoBySealIndex(rule.getSealIndex());
                List<SystemInfo> systemInfos = systemInfoService.queryById(signatureInfo.getId());
                if (SignatureArgumentsValidateUtil.verifySystemInfoListSize(systemInfos, signatureInfo.getId())) {
                    return this.recordLogAndReturnStatusCode(
                            ResultStatusCodeEnum.NO_SYSTEM_PERMISSIONS.getCode(), "根据 SIGNATURE_INFO 表的 ID :" + signatureInfo.getId() + "，间接（关联表为 SIGN_SYSTEM_RELATION，逻辑关系自行查询）查询表 SYSTEM_INFO 的 id 字段，结果 SystemInfo 集合为空",
                            inputFilePath, outputFilePath,
                            sysName, keyPwd, rule.getSealType(), rule.getSealPositionByText(), String.valueOf(rule.getSealIndex()), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
                }
            }
        }
        return String.valueOf(ResultStatusCodeEnum.VERIFY_SUCCESS.getCode());
    }


    /**
     * 记录入参信息并返回状态码
     * @param statusCode    状态码
     * @param simpleInfo    描述信息
     * @param inputPath     输入路径
     * @param outputPath    输出路径
     * @param sysName       系统来源
     * @param keyPwd        安全码
     * @param signType      签章方式
     * @param signPosition  签章位置
     * @param sealIndex     签章编号
     * @param signPage      签章页码
     * @return              状态码
     */
    private String recordLogAndReturnStatusCode(int statusCode, String simpleInfo,
                                                String inputPath, String outputPath,
                                                String sysName, String keyPwd, String signType, String signPosition, String sealIndex, String signPage) {
        electronicsignatureInfoLogService.recordLog(
                statusCode, simpleInfo,
                inputPath, inputPath, outputPath,
                sysName, keyPwd, signType, signPosition, sealIndex, signPage);
        return String.valueOf(statusCode);
    }

    private boolean signatureSingleFile(String inputFilePath, String outputFilePath, ArrayOfSignatureRule[] signatureRules, String sysName, String keyPwd) {
        return signatureService.signatureSingleFile(inputFilePath, outputFilePath, signatureRules[0].getSignatureRule(), sysName, keyPwd);
    }

    private boolean signatureFolder(String inputFilePath, String outputFilePath, ArrayOfSignatureRule[] signatureRules, String sysName, String keyPwd) {
        return signatureService.signatureFolder(inputFilePath, outputFilePath, signatureRules[0].getSignatureRule(), sysName, keyPwd);
    }

    private void printArgs(ArrayOfSignatureRule[] signatureRules, String inputFilePath, String outputFilePath, String sysName, String keyPwd) {
        logger.info("进入 Webservice 接口方法 signatureByRules，输入文件路径：{}，输出文件路径：{}，系统名称：{}，安全码:{}", inputFilePath, outputFilePath, sysName, keyPwd);
        for (ArrayOfSignatureRule signatureRule : signatureRules) {
            List<SignatureRule> rules = signatureRule.getSignatureRule();
            for (SignatureRule rule : rules) {
                logger.info("签章编号：{}，签章定位：{}，签章方式:{}，签章所在页：{}", rule.getSealIndex(), rule.getSealPositionByText(), rule.getSealType(), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
            }
        }
    }

    private SignatureInfo getSignatureInfoBySealIndex(Integer sealIndex) {
        return signatureInfoService.queryBySignId(sealIndex).get(0);
    }

    private List<SignatureRule> getSignatures(ArrayOfSignatureRule rule) {
        return rule.getSignatureRule();
    }
}
