package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.model.SignatureContainer;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: mfh
 * @Date: 2019-06-10 9:45
 **/
public interface SignatureTaskService {
    /**
     * 执行签章任务（单个章）
     * @param ftpAddress        ftp地址
     * @param ftpUserName       ftp登录用户名
     * @param ftpPwd            ftp登录密码
     * @param inputPath         输入路径，被签章的文件的路径
     * @param outputPath        输出路径，签完章后的文件的存放路径
     * @param signServerUrl     签章服务器的地址
     * @param keySn             签章功能使用的序列号
     * @param pwd               签章功能使用的密码
     * @param signName          章名称
     * @param signType          签章类型，0-文字定位；1-像素定位
     * @param signPosition      签章的位置，如果 signType 等于 0，则直接根据此参数在相应的文字处盖章；如果 signType 等于 -1，则根据此参数生成数据，即,在对应的像素点盖章
     * @param signPages         签章所在页
     * @param sealIndex         签章编号
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return                  签章结果（码值）
     */
    Future<Integer> executeSignature(String ftpAddress, String ftpUserName, String ftpPwd,
                                     String inputPath, String outputPath,
                                     String signServerUrl, String keySn, String pwd, String signName,
                                     String signType, String signPosition, int[] signPages, String sealIndex, String sysName, String keyPwd);

    /**
     * 执行签章任务（多个章）
     * @param address               ftp地址
     * @param name                  ftp登录用户名
     * @param pwd                   ftp登录密码
     * @param inputFilePath         输入路径，被签章的文件的路径
     * @param outputFilePath        输出路径，签完章后的文件的存放路径
     * @param signServerUrl         签章服务器的地址
     * @param signatureContainers   封装了签章规则与签章信息，适用于多个章
     * @param sysName               系统来源
     * @param keyPwd                安全码
     * @return                      签章结果（码值）
     */
    Future<Integer> executeSignature(String address, String name, String pwd, String inputFilePath, String outputFilePath, String signServerUrl, List<SignatureContainer> signatureContainers, String sysName, String keyPwd);
}
