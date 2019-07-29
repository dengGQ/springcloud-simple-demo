package com.fotic.it.support.signature.service.impl;

import com.KGitextpdf.text.DocumentException;
import com.KGitextpdf.text.exceptions.InvalidPdfException;
import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.exception.commons.BusinessException;
import com.fotic.it.support.signature.manager.SignatureManager;
import com.fotic.it.support.signature.model.ExInfo;
import com.fotic.it.support.signature.model.FtpInfo;
import com.fotic.it.support.signature.model.SignatureContainer;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.fotic.it.support.signature.service.SignatureCallback;
import com.fotic.it.support.signature.service.SignatureTaskService;
import com.fotic.it.support.signature.util.CharacterEncodingUtil;
import com.fotic.it.support.signature.util.CommonUtil;
import com.fotic.it.support.signature.util.FtpFileOperator;
import com.fotic.it.support.signature.util.SignatureArgumentsValidateUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * @Author: mfh
 * @Date: 2019-06-10 10:11
 **/
@Service
public class SignatureTaskServiceImpl implements SignatureTaskService {
    /**
     *
     */
    private static Logger logger = LoggerFactory.getLogger(SignatureTaskServiceImpl.class);
    private ElectronicsignatureInfoLogService electronicsignatureInfoLogService;
    private SignatureManager signatureManager;

    @Async("asyncExecutor")
    @Override
    public Future<Integer> executeSignature(String ftpAddress, String ftpUserName, String ftpPwd,
                                            String inputPath, String outputPath,
                                            String signServerUrl, String keySn, String pwd, String signName,
                                            String signType, String signPosition, int[] signPage, String sealIndex,
                                            String sysName, String keyPwd) {

        printArgs(
                ftpAddress, ftpUserName,
                inputPath, outputPath,
                signServerUrl, keySn, signName,
                signType, signPosition, signPage);
        Future<Integer> result = new AsyncResult<>(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        if (!StringUtils.isEmpty(signPosition)) {
            FTPClient inputFtpClient = null;
            FTPClient outputFtpClient = null;
            InputStream fis = null;
            OutputStream fos = null;
            boolean isCatchEx = true;
            ExInfo exInfo = new ExInfo(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
            try {
                verifySignatureTypeAndPosition(signType, signPosition, exInfo);
                inputFtpClient = this.createFtpClientAndEnterLocalPassiveMode(ftpAddress, ftpUserName, ftpPwd);
                outputFtpClient = this.createFtpClientAndEnterLocalPassiveMode(ftpAddress, ftpUserName, ftpPwd);
                fis = this.retrieveFileStream(inputFtpClient, inputPath, exInfo);
                fos = this.createStoreFileStream(outputFtpClient, outputPath, exInfo);
                signatureManager.signature(
                        fis, fos,
                        inputPath, outputPath,
                        signServerUrl, keySn, pwd, signName,
                        signType, signPosition, signPage);
                isCatchEx = false;
            } catch (DocumentException e) {
                afterCatchDocumentException(exInfo, e);
                exInfo.setEx(e);
            } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | NoSuchProviderException | KeyStoreException e) {
                exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_CERTIFICATE.getCode());
                exInfo.setInfo("请检查证书许可相关信息：\n" +" 1、证书存放在了指定路径下\n" +" 2、证书是否过期\n" + " 3、签章密码是否正确\n" + " 4、密码是否与证书匹配");
                exInfo.setEx(e);
            } catch (InvalidPdfException e) {
                exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_FILE.getCode());
                exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ERROR_FILE.getCode()));
                exInfo.setEx(e);
            } catch (Exception e) {
                exInfo.setInfo(e.getMessage());
                exInfo.setEx(e);
            } finally {
                if (isCatchEx) {
                    exInfo.printStackTrace();
                    logger.error(exInfo.getMessage());
                    /* 异常 */
                    afterCatchEx(exInfo.getEx(),
                            inputFtpClient, outputFtpClient,
                            fis, fos,
                            inputPath, outputPath,
                            ftpAddress, ftpUserName, ftpPwd);
                    electronicsignatureInfoLogService.recordLog(
                            exInfo.getStatusCode(), exInfo.getInfo(),
                            inputPath, inputPath, outputPath,
                            sysName, keyPwd,
                            signType, signPosition, sealIndex, CommonUtil.array2Str(signPage)
                    );

                } else {
                    result = afterSuccess(
                            inputFtpClient, outputFtpClient,
                            fis, fos,
                            inputPath, outputPath,
                            ftpAddress, ftpUserName, ftpPwd);
                    electronicsignatureInfoLogService.recordLog(
                            ResultStatusCodeEnum.SUCCESS.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SUCCESS.getCode()),
                            inputPath, inputPath, outputPath,
                            sysName, keyPwd,
                            signType, signPosition, sealIndex, CommonUtil.array2Str(signPage)
                    );
                }
            }
        }
        return result;
    }

    @Async("asyncExecutor")
    @Override
    public Future<Integer> executeSignature(
            String ftpAddress, String ftpUserName, String ftpPwd,
            String inputPath, String outputPath,
            String signServerUrl,
            List<SignatureContainer> signatureContainers, String sysName, String keyPwd) {
        /* 没有对 signatureObjs 做校验 */
        printArgs(ftpAddress, ftpUserName, inputPath, outputPath);
        FTPClient inputFtpClient = null;
        FTPClient outputFtpClient = null;
        InputStream fis = null;
        OutputStream fos = null;
        boolean isCatchEx = true;
        Future<Integer> result = new AsyncResult<>(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode());
        ExInfo exInfo = new ExInfo(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()), new Exception("没有捕获到任何异常，请检查 executeSignature 方法 catch 部分代码，看是否存在有未发现的异常。"));
        try {
            inputFtpClient = this.createFtpClientAndEnterLocalPassiveMode(ftpAddress, ftpUserName, ftpPwd);
            outputFtpClient = this.createFtpClientAndEnterLocalPassiveMode(ftpAddress, ftpUserName, ftpPwd);
            fis = this.retrieveFileStream(inputFtpClient, inputPath, exInfo);
            fos = this.createStoreFileStream(outputFtpClient, outputPath, exInfo);
            signatureManager.signature(
                    fis, fos,
                    inputPath, outputPath,
                    signServerUrl, signatureContainers);
            isCatchEx = false;
        } catch (InvalidPdfException e) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_FILE.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ERROR_FILE.getCode()));
            exInfo.setEx(e);
        } catch (DocumentException e) {
            afterCatchDocumentException(exInfo, e);
            exInfo.setEx(e);
        } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | NoSuchProviderException | KeyStoreException e) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_CERTIFICATE.getCode());
            exInfo.setInfo("请检查证书许可相关信息：\n" +
                    " 1、证书存放在了指定路径下\n" +
                    " 2、证书是否过期\n" +
                    " 3、签章密码是否正确\n" +
                    " 4、密码是否与证书匹配");
            exInfo.setEx(e);
        } catch (Exception e) {
            exInfo.setInfo(e.getMessage());
            exInfo.setEx(e);
        } finally {
            if (isCatchEx) {
                exInfo.printStackTrace();
                logger.error(exInfo.getMessage());
                afterCatchEx(
                        exInfo.getEx(),
                        inputFtpClient, outputFtpClient,
                        fis, fos,
                        inputPath, outputPath,
                        ftpAddress, ftpUserName, ftpPwd);
                electronicsignatureInfoLogService.recordLog(
                        exInfo.getStatusCode(), exInfo.getInfo(),
                        inputPath, inputPath, outputPath,
                        sysName, keyPwd,
                        signatureContainers);
            } else {
                result = afterSuccess(
                        inputFtpClient, outputFtpClient,
                        fis, fos,
                        inputPath, outputPath,
                        ftpAddress, ftpUserName, ftpPwd);
                electronicsignatureInfoLogService.recordLog(
                        ResultStatusCodeEnum.SUCCESS.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SUCCESS.getCode()),
                        inputPath, inputPath, outputPath,
                        sysName, keyPwd,
                        signatureContainers);
            }
        }
        return result;
    }

    /**
     * 根据给定的 inputClient，获取 ftp 服务器上 inputPath 对应的输入流，
     * 如果此 InputStream 为空，则说明根据 inputPath 没有找到对应的文件，
     * 或者 inputPath 中存在乱码，导致没有获取到输入流
     * @param inputFtpClient ftp 客户端
     * @param inputPath      输入路径
     * @param exInfo         异常信息
     * @return               从 ftp 服务器上取回的 inputPath 路径下的文件输入流
     * @throws IOException   io异常
     */
    private InputStream retrieveFileStream(FTPClient inputFtpClient, String inputPath, ExInfo exInfo) throws IOException {
        String transCodedInputPath;
        try {
            transCodedInputPath = CharacterEncodingUtil.gbk2Utf8(inputPath);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
            transCodedInputPath = inputPath;
        }
        logger.info("开始获取 " + transCodedInputPath + " 的输入流");
        InputStream fis = inputFtpClient.retrieveFileStream(StringUtils.cleanPath(inputPath));
        if (Objects.isNull(fis)) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_RETRIEVE_FILE.getCode());
            throw new RuntimeException(transCodedInputPath + " 文件不存在或者文件名中包含未能识别的字符");
        }
        logger.info("结束获取 " + transCodedInputPath + " 的输入流");
        return fis;
    }
    /**
     * 校验 SignatureType 与 SignaturePosition
     * @param signType          签章方式
     * @param signPosition      签章位置
     * @param exInfo            异常信息
     */
    private void verifySignatureTypeAndPosition(String signType, String signPosition, ExInfo exInfo) {
        if (SignatureArgumentsValidateUtil.verifySignatureType(signType)) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ILLEGAL_SIGNATURE_TYPE.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_SIGNATURE_TYPE.getCode()));
            throw new BusinessException("signType:" + signType + " 值异常");
        }
        if (SignatureArgumentsValidateUtil.verifySignPosition(signPosition)) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ILLEGAL_SEAL_POSITION.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_SEAL_POSITION.getCode()));
            throw new BusinessException("signPosition:" + signPosition + " 值异常");
        }
    }

    /**
     * 捕获到 DocumentException 的处理方式
     * 捕获到 DocumentException 分两种情况处理
     * 1、异常信息中带有 Not find the text 字样，则使用 ILLEGAL_SEAL_POSITION 进行标记
     * 2、如果异常信息中不带有 Not find the text 字样，则按照 ERROR_FILE 进行标记，即 pdf 文件存在问题
     * @param exInfo 异常信息
     * @param e      金格的异常类
     */
    private void afterCatchDocumentException(ExInfo exInfo, DocumentException e) {
        if (e.getMessage().contains(Constant.NOT_FIND_THE_TEXT)) {
            /* 需要盖章的文字未找到或者提供的像素信息错误 */
            exInfo.setStatusCode(ResultStatusCodeEnum.ILLEGAL_SEAL_POSITION.getCode());
            /* 由于此处为盖多种章，所以提示信息未能精确到每一个具体是什么文字没有找到，或者像素是如何异常的。但分析异常可以看到具体信息 */
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ILLEGAL_SEAL_POSITION.getCode()));
        } else {
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_FILE.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.ERROR_FILE.getCode()));
        }
    }


    private Future<Integer> afterSuccess(FTPClient inputFtpClient, FTPClient outputFtpClient,
                                         InputStream fis, OutputStream fos,
                                         String inputPath, String outputPath,
                                         String ftpAddress, String ftpUserName, String ftpPwd) {
        try {
            logger.info("输入文件路径：{}，输出文件路径：{} 的签章任务，签章任务正常执行完成", CharacterEncodingUtil.gbk2Utf8(inputPath), CharacterEncodingUtil.gbk2Utf8(outputPath));
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
            logger.info("输入文件路径：{}，输出文件路径：{} 的签章任务，签章任务正常执行完成", inputPath, outputPath);

        }
        /* 正常 */
        ListenableFuture<Integer> listenableFuture = AsyncResult.forValue(ResultStatusCodeEnum.SUCCESS.getCode());
        listenableFuture.addCallback(new SignatureCallback(
                inputFtpClient, outputFtpClient, fis, fos,
                inputPath, outputPath,
                new FtpInfo(ftpAddress, ftpUserName, ftpPwd)));
        return new AsyncResult<>(ResultStatusCodeEnum.SUCCESS.getCode());
    }

    /**
     * 捕获到异常后的处理方式
     *
     * @param ex              捕获到的异常
     * @param inputFtpClient  输入路径的 ftp 客户端，负责读取输入路径下的文件以及删除文件
     * @param outputFtpClient 输出路径的 ftp 客户端，负责存储输出文件
     * @param fis             输入流
     * @param fos             输出流
     * @param inputPath       输入路径
     * @param outputPath      输出路径
     * @param ftpAddress      ftp url
     * @param ftpUserName     ftp 用户名
     * @param ftpPwd          ftp 密码
     */
    private void afterCatchEx(Exception ex,
                              FTPClient inputFtpClient, FTPClient outputFtpClient,
                              InputStream fis, OutputStream fos,
                              String inputPath, String outputPath,
                              String ftpAddress, String ftpUserName, String ftpPwd) {
        logger.info("输出文件路径：{}，输入文件路径：{} 的签章任务，在执行过程中出现异常", inputPath, outputPath);
        ListenableFuture<Integer> listenableFuture = AsyncResult.forExecutionException(ex);
        listenableFuture.addCallback(new SignatureCallback(
                inputFtpClient, outputFtpClient, fis, fos,
                inputPath, outputPath,
                new FtpInfo(ftpAddress, ftpUserName, ftpPwd)));

    }

    private OutputStream createStoreFileStream(FTPClient client, String outputPath, ExInfo exInfo) throws IOException {
        OutputStream fos;
        logger.info("获取输出流");
        fos = client.storeFileStream(StringUtils.cleanPath(outputPath));
        if (Objects.isNull(fos)) {
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_STORE_FILE.getCode());
            throw new RuntimeException(outputPath + " 路径并没有指向真正的文件，有可能指向了一个文件夹");
        }
        logger.info("获取输出流完成");
        return fos;
    }

    private FTPClient createFtpClientAndEnterLocalPassiveMode(String ftpAddress, String ftpUserName, String ftpPwd) throws IOException {
        FtpFileOperator operator = new FtpFileOperator();
        logger.info("创建 FTP 客户端");
        FTPClient client = operator.connect(ftpAddress, ftpUserName, ftpPwd);
        client.enterLocalPassiveMode();
        logger.info("创建 FTP 客户端完毕");
        return client;
    }

    private void printArgs(String ftpAddress, String ftpUserName,
                           String inputPath, String outputPath,
                           String signServerUrl, String keySn, String signName,
                           String signType, String signPosition, int[] signPage) {
        logger.info("进入签章任务的执行方法");
        logger.info("ftp 服务器地址：{}，用户名：{}", ftpAddress, ftpUserName);
        logger.info("输入文件路径：{}，输出文件路径：{}", inputPath, outputPath);
        logger.info("签章服务器地址：{}，签章序号：{}，章名称：{}", signServerUrl, keySn, signName);
        logger.info("签章方式：{}，签章位置：{}，签章所在页：{}", signType, signPosition, CommonUtil.array2Str(signPage));
    }

    private void printArgs(String ftpAddress, String ftpUserName,
                           String inputPath, String outputPath) {
        logger.info("进入签章任务的执行方法");
        logger.info("ftp 服务器地址：{}，用户名：{}", ftpAddress, ftpUserName);
        try {
            logger.info("输入文件路径：{}，输出文件路径：{}", CharacterEncodingUtil.gbk2Utf8(inputPath), CharacterEncodingUtil.gbk2Utf8(outputPath));
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
            logger.info("输入文件路径：{}，输出文件路径：{}", inputPath, outputPath);
        }
    }

    @Autowired
    public void setElectronicsignatureInfoLogService(ElectronicsignatureInfoLogService electronicsignatureInfoLogService) {
        this.electronicsignatureInfoLogService = electronicsignatureInfoLogService;
    }

    @Autowired
    public void setSignatureManager(SignatureManager signatureManager) {
        this.signatureManager = signatureManager;
    }
}
