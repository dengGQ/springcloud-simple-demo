package com.fotic.it.support.signature.util;

import com.fotic.it.support.signature.constant.Constant;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Ftp 操作类
 */
public class FtpFileOperator {

    private static Logger logger = LoggerFactory.getLogger(FtpFileOperator.class);

    /**
     * 获得连接-FTP方式
     *
     * @param hostName FTP服务器地址
     * @param port     FTP服务器端口
     * @param userName FTP登录用户名
     * @param passWord FTP登录密码
     * @return FTPClient
     */
    public FTPClient getConnectionFTP(String hostName, int port, String userName, String passWord) {
        String localCharset = Constant.CHARACTER_ENCODING_GBK;
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(hostName, port);
            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                if (ftp.login(userName, passWord)) {
                    /*if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
                        localCharset = "UTF-8";
                    }*/
                    //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
                    ftp.setControlEncoding(localCharset);
                    /*FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
                    conf.setServerLanguageCode(Constant.LANGUAGE_CODE);*/
                } else {
                    throw new RuntimeException("连接 FTP 服务器异常，请检查");
                }
            }
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                logger.error("连接 {} 服务器失败", hostName);
            }
            logger.info("登陆 {} 服务器成功", hostName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return ftp;
    }

    public FTPClient connect(String host, String user, String pwd) throws IOException {
        FTPClient ftpclient = new FTPClient();
        ftpclient.setControlEncoding(Constant.CHARACTER_ENCODING_GBK);
        ftpclient.connect(host);
        ftpclient.login(user, pwd);
        ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpclient;
    }


    /**
     * 关闭连接-FTP方式
     *
     * @param ftp FTPClient对象
     * @return boolean
     */
    public boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                logger.info("FTP 连接关闭");
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 上传文件-FTP方式
     *
     * @param ftp         FTPClient对象
     * @param path        FTP服务器上传地址
     * @param fileName    本地文件路径
     * @param inputStream 输入流
     * @return boolean
     */
    public boolean uploadFile(FTPClient ftp, String path, String fileName, InputStream inputStream) {
        boolean success = false;
        try {
            //转移到指定FTP服务器目录
            ftp.changeWorkingDirectory(path);
            //得到目录的相应文件列表
            FTPFile[] fs = ftp.listFiles();
            fileName = FtpFileOperator.changeName(fileName, fs);
            fileName = new String(fileName.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            path = new String(path.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            //转到指定上传目录
            ftp.changeWorkingDirectory(path);
            //将上传文件存储到指定目录
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
            ftp.storeFile(fileName, inputStream);
            //关闭输入流
            inputStream.close();
            //退出ftp
            ftp.logout();
            //表示上传成功
            success = true;
            logger.info("{} 已成功上传到 {}", fileName, path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 删除文件-FTP方式
     *
     * @param ftp      FTPClient对象
     * @param path     FTP服务器上传地址
     * @param fileName FTP服务器上要删除的文件名
     * @return
     */
    public boolean deleteFile(FTPClient ftp, String path, String fileName) {
        boolean success = false;
        try {
            //转移到指定FTP服务器目录
            ftp.changeWorkingDirectory(path);
            fileName = new String(fileName.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            ftp.deleteFile(fileName);
            ftp.logout();
            logger.info("{} 文件被删除", fileName);
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 判断是否有重名文件
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static boolean isFileExist(String fileName, FTPFile[] fs) {
        for (int i = 0; i < fs.length; i++) {
            FTPFile ff = fs[i];
            if (ff.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据重名判断的结果 生成新的文件的名称
     * @param fileName
     * @param fs
     * @return
     */
    public static String changeName(String fileName, FTPFile[] fs) {
        int n = 0;
        while (isFileExist(fileName, fs)) {
            n++;
            String a = "[" + n + "]";
            int b = fileName.lastIndexOf(".");
            int c = fileName.lastIndexOf("[");
            if (c < 0) {
                c = b;
            }
            StringBuffer name = new StringBuffer(fileName.substring(0, c));
            StringBuffer suffix = new StringBuffer(fileName.substring(b + 1));
            fileName = name.append(a) + "." + suffix;
        }
        return fileName;
    }
}