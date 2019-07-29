package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.dao.entity.SignatureConfiguration;
import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.entity.SignatureLogInfo;
import com.fotic.it.support.signature.dao.entity.SystemInfo;
import com.fotic.it.support.signature.dao.mapper.SignatureConfigurationMapper;
import com.fotic.it.support.signature.model.ArrayOfInt;
import com.fotic.it.support.signature.model.SignatureRule;
import com.fotic.it.support.signature.util.CommonUtil;
import com.fotic.it.support.signature.util.SignatureArgumentsValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-06-14 10:14
 **/
@Component
public class SignatureScheduledTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SignatureScheduledTask.class);
    @Resource
    private SignatureConfigurationMapper signatureConfigurationMapper;
    @Resource
    private SignatureInfoService signatureInfoService;
    @Resource
    private SignatureLogInfoService signatureLogInfoService;
    @Resource
    private SignatureService signatureService;
    @Resource
    private SystemInfoService systemInfoService;
    @Resource
    private ElectronicsignatureInfoLogService service;

    @Override
    public void run() {
        doTask();
    }

    private void doTask() {
        List<SignatureConfiguration> signatureConfigurations = signatureConfigurationMapper.queryAll(null);
        for (SignatureConfiguration signConfig : signatureConfigurations) {
            Long lastTime = getLastTime(signConfig);
            if (lastTime <= System.currentTimeMillis()) {
                List<SignatureInfo> signatureInfos = signatureInfoService.queryById(Integer.valueOf(signConfig.getSignnumber()));
                SignatureInfo signatureInfo = signatureInfos.get(0);
                if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(signConfig.getInputpath())) {
                    signatureConfigurationMapper.update(signConfig);
                    this.recordLogOld(signConfig, signatureInfo);
                    SystemInfo systemInfo = this.getSystemInfo(signatureInfo);
                    service.recordLog(
                            ResultStatusCodeEnum.ILLEGAL_INPUT_PATH.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_INPUT_PATH.getCode()),
                            signConfig.getInputpath(), signConfig.getInputpath(), signConfig.getOutputpath(),
                            systemInfo.getSystemname(), systemInfo.getSeccode(),
                            signConfig.getSigntype(), signConfig.getSignanchortext(), String.valueOf(signatureInfo.getSignid()), String.valueOf(signConfig.getSignpage()));
                    continue;
                }
                if (SignatureArgumentsValidateUtil.verifyFilePathIsEmpty(signConfig.getOutputpath())) {
                    signatureConfigurationMapper.update(signConfig);
                    this.recordLogOld(signConfig, signatureInfo);
                    SystemInfo systemInfo = this.getSystemInfo(signatureInfo);
                    service.recordLog(
                            ResultStatusCodeEnum.ILLEGAL_OUTPUT_PATH.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_OUTPUT_PATH.getCode()),
                            signConfig.getInputpath(), signConfig.getInputpath(), signConfig.getOutputpath(),
                            systemInfo.getSystemname(), systemInfo.getSeccode(),
                            signConfig.getSigntype(), signConfig.getSignanchortext(), String.valueOf(signatureInfo.getSignid()), String.valueOf(signConfig.getSignpage()));
                    continue;
                }
                if (CommonUtil.isFolder(signConfig.getInputpath())) {
                    this.signatureFolder(signConfig, signatureInfo);
                } else if (CommonUtil.isPdf(signConfig.getInputpath())) {
                    this.signatureSingleFile(signConfig, signatureInfo);
                } else {
                    logger.info(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_PATH.getCode()));
                    SystemInfo systemInfo = this.getSystemInfo(signatureInfo);
                    service.recordLog(
                            ResultStatusCodeEnum.ILLEGAL_PATH.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_PATH.getCode()),
                            signConfig.getInputpath(), signConfig.getInputpath(), signConfig.getOutputpath(),
                            systemInfo.getSystemname(), systemInfo.getSeccode(),
                            signConfig.getSigntype(), signConfig.getSignanchortext(), String.valueOf(signatureInfo.getSignid()), String.valueOf(signConfig.getSignpage()));
                }
            }
        }
    }

    private void signatureSingleFile(SignatureConfiguration signConfig, SignatureInfo signatureInfo) {
        SignatureLogInfo signLogInfo = assembleSignatureLogInfo(signConfig, signatureInfo);
        SystemInfo systemInfo = this.getSystemInfo(signatureInfo);
        boolean isExistFailure = signatureService.signatureSingleFile(
                signConfig.getInputpath(), signConfig.getOutputpath(),
                assembleSignatureRule(signConfig), signatureInfo,
                systemInfo.getSystemname(), systemInfo.getSeccode());
        this.afterSignature(signConfig, signLogInfo, isExistFailure);
    }

    private SignatureLogInfo assembleSignatureLogInfo(SignatureConfiguration signConfig, SignatureInfo signatureInfo) {
        /* SignatureLogInfo 对象创建不使用 builder */
        SignatureLogInfo signLogInfo = new SignatureLogInfo();
        signLogInfo.setSid(Long.valueOf(signatureInfo.getId()));
        signLogInfo.setSignconfid(signConfig.getId());
        signLogInfo.setCalltime(new Date());
        return signLogInfo;
    }

    private void signatureFolder(SignatureConfiguration signConfig, SignatureInfo signatureInfo) {
        SignatureLogInfo signLogInfo = assembleSignatureLogInfo(signConfig, signatureInfo);
        SystemInfo systemInfo = this.getSystemInfo(signatureInfo);
        boolean isExistFailure = signatureService.signatureFolder(
                signConfig.getInputpath(), signConfig.getOutputpath(),
                assembleSignatureRule(signConfig), signatureInfo,
                systemInfo.getSystemname(), systemInfo.getSeccode());

        this.afterSignature(signConfig, signLogInfo, isExistFailure);
    }

    private void afterSignature(SignatureConfiguration signConfig, SignatureLogInfo signLogInfo, boolean isExistFailure) {
        if (isExistFailure) {
            signLogInfo.setMessage(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
            signLogInfo.setSignconfid(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        } else {
            signLogInfo.setMessage(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SUCCESS.getCode()));
            signLogInfo.setSignconfid(ResultStatusCodeEnum.SUCCESS.getCode());
            logger.info("输入参数为：{}，输出参数为：{} ，签章文件夹执行成功", signConfig.getInputpath(), signConfig.getOutputpath());
        }
        signatureLogInfoService.insert(signLogInfo);
        signConfig.setLasttime(new Date());
        signatureConfigurationMapper.update(signConfig);
    }

    private SystemInfo getSystemInfo(SignatureInfo signatureInfo) {
        List<SystemInfo> systemInfos = systemInfoService.queryById(signatureInfo.getId());
        SystemInfo systemInfo = new SystemInfo();
        /* 没有对 systemInfo 的数量进行验证。默认只取第一个，正常情况下应该只有一个值 */
        if (systemInfos.isEmpty() || Objects.isNull(systemInfos.get(0))) {
            systemInfo.setSystemname("由于 SystemInfo 实例为空，所以系统来源未知");
            systemInfo.setSeccode("由于 SystemInfo 实例为空，所以安全码未知");
        } else {
            systemInfo = systemInfos.get(0);
        }
        return systemInfo;
    }

    private SignatureRule assembleSignatureRule(SignatureConfiguration signConfig) {
        /* SignatureRule 对象创建不使用 builder */
        SignatureRule rule = new SignatureRule();
        rule.setSealType(signConfig.getSigntype());
        rule.setSealPositionByText(signConfig.getSignanchortext());
        rule.setSealPositionPages(assembleArrayOfInt(signConfig));
        return rule;
    }

    private ArrayOfInt assembleArrayOfInt(SignatureConfiguration signConfig) {
        ArrayOfInt arrayOfInt = new ArrayOfInt();
        List<Integer> list = new ArrayList<>();
        list.add(signConfig.getSignpage());
        arrayOfInt.set_int(list);
        return arrayOfInt;
    }

    private void recordLogOld(SignatureConfiguration signConfig, SignatureInfo signatureInfo) {
        SignatureLogInfo signLogInfo = new SignatureLogInfo();
        signLogInfo.setSid(Long.valueOf(signatureInfo.getId()));
        signLogInfo.setFlag(String.valueOf(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
        signLogInfo.setMessage(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
        signLogInfo.setSignconfid(signConfig.getId());
        signLogInfo.setCalltime(new Date());
        signatureLogInfoService.insert(signLogInfo);
    }

    private Long getLastTime(SignatureConfiguration configuration) {
        Integer signCycle = configuration.getSigncycle();
        Date lastTime = configuration.getLasttime();
        return (signCycle * 60 * 1000) + lastTime.getTime();
    }
}
