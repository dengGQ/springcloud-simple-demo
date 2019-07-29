package com.fotic.it.support.signature.util;

import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.entity.SystemInfo;
import com.fotic.it.support.signature.model.ArrayOfSignatureRule;
import com.fotic.it.support.signature.model.SignatureRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-06-10 14:59
 **/
public class SignatureArgumentsValidateUtil {
    private static  Logger logger = LoggerFactory.getLogger(SignatureArgumentsValidateUtil.class);

    public static boolean verifySignatureType(String signatureType) {
        if (!Constant.SEAL_TYPE_POSITION_PIXEL.equals(signatureType) && !Constant.SEAL_TYPE_POSITION_TEXT.equals(signatureType)) {
            logger.error("签章方式 {} 既不是文字定位也不是像素定位", signatureType);
            return true;
        }
        return false;
    }

    public static boolean verifySignatureInfoId(List<SignatureInfo> signatureInfos, Integer id) {
        if (signatureInfos.size() != 1) {
            /* SignatureInfo 信息有误，正常情况下有且只有一条数据 */
            logger.error("根据签章编号 {}，查询表 SIGNATURE_INFO 的 signid 字段，结果 SignatureInfo 集合为应该有且只有一条数据，但查询出多条", id);
            return true;
        }
        if (signatureInfos.get(0) == null || StringUtils.isEmpty(signatureInfos.get(0).getId())) {
            logger.error("根据签章编号 {}，查询表 SIGNATURE_INFO 的 signid 字段，结果 SignatureInfo 集合中的对象为 null 或者集合中的对象的 id 为 null ", id);
            return true;
        }
        return false;
    }

    public static  boolean verifySignatureRule(SignatureRule rule) {
        return verifySealIndex(rule) || verifySealPositionByText(rule) || verifySignatureType(rule);
    }

    public static  boolean verifySignatureRulesIsNull(ArrayOfSignatureRule[] signatureRules) {
        if (signatureRules == null || signatureRules.length == 0) {
            logger.error("signatureRules的值为 null");
            return true;
        }
        return false;
    }

    public static  boolean verifyFilePathIsEmpty(String inputFilePath) {
        if (StringUtils.isEmpty(inputFilePath)) {
            logger.error("输入路径 {} 为 null", inputFilePath);
            return true;
        }
        return false;
    }

    public static  boolean verifySystemName(List<SystemInfo> systemInfos, String systemName, String sealIndex) {
        for (SystemInfo systemInfo : systemInfos) {
            String appId = systemInfo.getAppid();
            if (Objects.nonNull(appId) && appId.equalsIgnoreCase(systemName)) {
                return false;
            }
        }
        logger.error("签章编号为 {} 的电子印章没有系统权限", sealIndex);
        return true;
    }

    public static  boolean verifySystemInfoListSize(List<SystemInfo> systemInfos, Integer id) {
        if (systemInfos.isEmpty()) {
            logger.error("根据 {}，间接（关联表为 SIGN_SYSTEM_RELATION，逻辑关系自行查询）查询表 SYSTEM_INFO 的 id 字段，结果 SystemInfo 集合为空", id);
            return true;
        }
        return false;
    }

    public static  boolean verifySignPosition(String signPosition) {
        if (StringUtils.isEmpty(signPosition)) {
            logger.error("签章位置 {} 异常", signPosition);
            return true;
        }
        String splitFlag = ",";
        if (signPosition.contains(splitFlag)) {
            String[] split = signPosition.split(splitFlag);
            int coordinateXY = 2;
            if (split.length < coordinateXY) {
                logger.error("签章位置 {} 坐标值异常", StringUtils.arrayToCommaDelimitedString(split));
                return true;
            }
        }
        return false;
    }
    /**
     * 校验签章要盖到的文字
     * @param rule  签章规则
     * @return      不为空：false；空：true
     */
    private static boolean verifySealPositionByText(SignatureRule rule) {
        if (StringUtils.isEmpty(rule.getSealPositionByText())) {
            logger.error("签章编号为 {} 的电子印章的定位方式为空", rule.getSealIndex());
            return true;
        }
        return false;
    }

    /**
     * 校验签章用印类型
     * @param rule  签章规则
     * @return      既不是文字定位也不是像素定位：true；是文字定位或者像素定位：false
     */
    private static boolean verifySignatureType(SignatureRule rule) {
       return verifySignatureType(rule.getSealType());
    }

    /**
     * 校验签章编号
     * @param rule  签章规则
     * @return      编号为空：true；不为空：false
     */
    private static boolean verifySealIndex(SignatureRule rule) {
        return verifySealIndex(String.valueOf(rule.getSealIndex()));
    }

    public static boolean verifySealIndex(String sealIndex) {
        if (StringUtils.isEmpty(sealIndex)) {
            logger.error("签章编号为空");
            return true;
        }
        return false;
    }

    /**
     * 是否包含非数字的字符
     * @param val   被验证的字符串
     * @return      包含非数字字符：true；不包含：false
     */
    public static boolean containsNonNumbers(String val) {
        for (int i = 0; i < val.length(); i++) {
            if (!Character.isDigit(val.charAt(i))){
                logger.error("{} 包含非数字字符", val);
                return true;
            }
        }
        return false;
    }

    /**
     * 验证电子印章安全码是否正确
     * @param systemInfos   签章信息
     * @param securityCode  安全码
     * @param sealIndex     签章编号
     * @return              securityCode 不能与 systemInfos 匹配：true；securityCode 与 systemInfos 匹配：false
     */
    public static boolean verifySecurityCode(List<SystemInfo> systemInfos, String securityCode, String sealIndex) {
        for (SystemInfo systemInfo : systemInfos) {
            String code = systemInfo.getSeccode();
            if (Objects.nonNull(code) && code.equals(securityCode)) {
                return false;
            }
        }
        logger.error("签章编号为 {} 的电子印章安全码不正确", sealIndex);
        return true;
    }

    /**
     * 验证入参的系统名称是否有权限
     * 调用方式：if(verifySystemName(args)) {return String.valueOf(ResultStatusCodeEnum.NO_SYSTEM_PERMISSIONS.getCode())}
     *
     * @param sysName       系统来源
     * @param systemInfos   签章信息
     * @return              签章信息为空或者 sysName 不能与 systemInfos 匹配：true；签章信息不为空并且 sysName 与 systemInfos 匹配：false
     */
    private boolean verifySystemName(String sysName, List<SystemInfo> systemInfos, SignatureRule rule) {
        if (rule == null) {
            logger.error("signatureRule 实例为 null");
            return true;
        }

        for (SystemInfo systemInfo : systemInfos) {
            if (systemInfo.getSystemname().equals(sysName)) {
                return false;
            }
        }
        logger.error("签章编号为 {} 的电子印章没有系统权限", rule.getSealIndex());
        return true;
    }

    /**
     * 验证入参的电子印章安全码是否正确
     * 调用方式：if(verifySecurityCode(args)) {return String.valueOf(ResultStatusCodeEnum.ERROR_SIGNATURE_SECURITY_CODE.getCode())}
     * @param securityCode  安全码
     * @param systemInfos   签章信息
     * @param rule          签章规则
     * @return              签章信息为空或者 securityCode 不能与 systemInfos 匹配：true；签章信息不为空并且securityCode 与 systemInfos 匹配：false
     */
    private boolean verifySecurityCode(String securityCode, List<SystemInfo> systemInfos, SignatureRule rule) {
        if (rule == null) {
            logger.error("signatureRule 实例为 null");
            return true;
        }

        for (SystemInfo systemInfo : systemInfos) {
            if (systemInfo.getSeccode().equals(securityCode)) {
                return false;
            }
        }
        logger.error("签章编号为 {} 的电子印章安全码不正确", rule.getSealIndex());
        return true;
    }
}
