package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.model.FtpInfo;
import com.fotic.it.support.signature.util.CharacterEncodingUtil;
import com.fotic.it.support.signature.util.FtpFileOperator;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-06-13 14:56
 **/
public class SignatureCallback implements ListenableFutureCallback<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(SignatureCallback.class);
    private FTPClient inputClient;
    private FTPClient outputClient;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String inputPath;
    private String errorFilePath;
    private FtpInfo ftpInfo;
    public SignatureCallback() {
    }

    public SignatureCallback(FTPClient inputClient, FTPClient outputClient, InputStream inputStream, OutputStream outputStream,
                             String inputPath, String errorFilePath,
                             FtpInfo ftpInfo) {
        this.inputClient = inputClient;
        this.outputClient = outputClient;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.inputPath = inputPath;
        this.errorFilePath = errorFilePath;
        this.ftpInfo = ftpInfo;
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.error(ex.getMessage());
        closeStream();
        closeFtpClient();
        deleteNoContentFile();
    }

    /**
     * 在签章过程中会存在不符合规则{@link com.fotic.it.support.signature.model.SignatureRule}的文件
     * 这些文件会在输出文件夹中生成，它们是没有任何内容的，但是业务并不需要这些文件，所以在签章结束后，将这些
     * 0 kb 的文件删除。
     */
    private void deleteNoContentFile() {
        FtpFileOperator operator = new FtpFileOperator();
        FTPClient client = operator.getConnectionFTP(ftpInfo.getAddress(), ftpInfo.getPort(), ftpInfo.getName(), ftpInfo.getPwd());
        try {
            boolean b = client.deleteFile(new String(this.errorFilePath.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
            if (b) {
                logger.info("删除失效文件 {} 成功", this.errorFilePath);
            } else {
                logger.info("删除失效文件 {} 失败", this.errorFilePath);
            }
            client.logout();
        } catch (IOException e) {
            logger.error("删除失效文件 {} 失败", errorFilePath);
            e.printStackTrace();
        }
        operator.closeFTP(client);
    }

    @Override
    public void onSuccess(Integer result) {
        closeStream();
        try {
            if (this.inputClient.completePendingCommand()) {
                String filename = StringUtils.getFilename(this.inputPath);
                String substring = this.inputPath.substring(0, this.inputPath.length() - filename.length());
                String transCodedInputPath = gbk2Utf8();
                if (this.inputClient.changeWorkingDirectory(substring)) {
                    boolean b = this.inputClient.deleteFile(this.inputPath);
                    if (b) {
                        logger.info("删除 {} 文件成功", transCodedInputPath);
                    } else {
                        logger.info("删除 {} 文件失败", transCodedInputPath);
                    }
                } else {
                    logger.info("{} 目录切换失败，无法删除 {} 路径下的文件", substring, transCodedInputPath);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            logger.error("删除 {} 文件失败", this.inputPath);
            e.printStackTrace();
        } finally {
            closeFtpClient();
        }
    }

    private String gbk2Utf8() {
        String transCodedInputPath;
        try {
            transCodedInputPath = CharacterEncodingUtil.gbk2Utf8(this.inputPath);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
            transCodedInputPath = this.inputPath;
        }
        return transCodedInputPath;
    }


    private void closeStream() {
        if (Objects.nonNull(this.inputStream)) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(this.outputStream)) {
            try {
                this.outputStream.flush();
                this.outputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                e.getMessage();
            }
        }
    }

    private void closeFtpClient() {
        if (this.inputClient.isConnected()) {
            try {
                this.inputClient.disconnect();
                logger.info("FTP inputClient 连接关闭");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        if (this.outputClient.isConnected()) {
            try {
                this.outputClient.disconnect();
                logger.info("FTP outputClient 连接关闭");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }
}
