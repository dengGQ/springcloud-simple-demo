package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.model.SignatureRule;

import java.util.List;
/**
 * @Author: mfh
 * @Date: 2019-06-17 14:05
 **/
public interface SignatureService {
    /**
     * 整文件夹签章（单章）
     * @param inputFilePath     输入路径（文件夹）
     * @param outputFilePath    输出路径（文件夹）
     * @param rule              签章规则
     * @param signatureInfo     签章信息
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return                  是否有失败任务，是：true; 否：false
     */
    boolean signatureFolder(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo, String sysName, String keyPwd);

    /**
     * 整文件夹签章（多章）
     * @param inputFilePath     输入路径（文件夹）
     * @param outputFilePath    输出路径（文件夹）
     * @param rules             签章规则（多个规则代表多个章）
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return                  是否有失败任务，是：true; 否：false
     */
    boolean signatureFolder(String inputFilePath, String outputFilePath, List<SignatureRule> rules, String sysName, String keyPwd);
    /**
     * 单文件处理（单章）
     * @param inputFilePath     输入路径（文件）
     * @param outputFilePath    输出路径
     * @param rule              签章规则
     * @param signatureInfo     签章信息
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return                  是否有失败任务，是：true; 否：false
     */
    boolean signatureSingleFile(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo, String sysName, String keyPwd);

    /**
     * 单文件处理（多章）
     * @param inputFilePath     输入路径（文件）
     * @param outputFilePath    输出路径
     * @param rules             签章规则（多个规则代表多个章）
     * @param sysName           系统来源
     * @param keyPwd            安全码
     * @return                  是否有失败任务，是：true; 否：false
     */
    boolean signatureSingleFile(String inputFilePath, String outputFilePath, List<SignatureRule> rules, String sysName, String keyPwd);
}
