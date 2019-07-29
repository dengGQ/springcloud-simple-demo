package com.fotic.it.support.signature.manager;

import com.KGitextpdf.text.DocumentException;
import com.fotic.it.support.signature.config.CommonsConfig;
import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.model.SignatureContainer;
import com.fotic.it.support.signature.util.CharacterEncodingUtil;
import com.fotic.it.support.signature.util.CommonUtil;
import com.kinggrid.kgcore.enmu.KGServerTypeEnum;
import com.kinggrid.pdf.KGPdfHummer;
import com.kinggrid.pdf.executes.PdfSignature4KG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.security.*;
import java.util.List;
import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-06-24 16:07
 **/
@Component
public class SignatureManager {
    @Resource
    private CommonsConfig commonsConfig;
    private static final Logger logger = LoggerFactory.getLogger(SignatureManager.class);

    public void signature(InputStream in, OutputStream out,
                          String inputPath, String outputPath,
                          String signServerUrl, String keySn, String pwd, String signName,
                          String signType, String signPosition, int[] signPage) throws IOException, DocumentException, GeneralSecurityException {
        KGPdfHummer signature = this.createKGPdfHummer(in, out);
        logger.info("设置签章证书，证书路径为：{}", commonsConfig.getSignatureCertificatePath());
        signature.setCertificate(SignatureManager.class.getResourceAsStream(commonsConfig.getSignatureCertificatePath()), pwd, pwd);
        logger.info("构造 PdfSignature4KG 对象，签章服务器地址：{}，签章序号：{}，签章密码：{}，章名称：{}", signServerUrl, keySn, pwd, signName);
        PdfSignature4KG pdfSignature4KG = new PdfSignature4KG(signServerUrl, KGServerTypeEnum.NET, keySn, pwd, signName);
        logger.info("PdfSignature4KG 对象构造完成");
        setSignaturePosition(signType, signPosition, pdfSignature4KG);
        setSignaturePages(signPage, pdfSignature4KG, signature);
        signature.setPdfSignature(pdfSignature4KG);
        logger.info("{} 开始签章", inputPath);
        signature.doSignature();
        logger.info("{} 签章完毕, 文件 {} 已生成", inputPath, outputPath);
        closeSignature(signature);
    }

    public void signature(InputStream in, OutputStream out,
                          String inputPath, String outputPath,
                          String signServerUrl,
                          List<SignatureContainer> signatureContainers) throws IOException, DocumentException, GeneralSecurityException {
        String transCodedInputPath;
        String transCodedOutPath;
        try {
            transCodedInputPath = CharacterEncodingUtil.gbk2Utf8(inputPath);
            transCodedOutPath = CharacterEncodingUtil.gbk2Utf8(outputPath);
        } catch (UnsupportedEncodingException e) {
            logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
            transCodedInputPath = inputPath;
            transCodedOutPath = outputPath;
        }
        KGPdfHummer signature = this.createKGPdfHummer(in, out);
        logger.info("设置签章证书，证书路径为：{}", commonsConfig.getSignatureCertificatePath());
        /* 签章密码 */
        String pwd = signatureContainers.get(0).getKeyPwd();
        logger.info("设置签章证书，证书路径为：{}", commonsConfig.getSignatureCertificatePath());
        signature.setCertificate(SignatureManager.class.getResourceAsStream(commonsConfig.getSignatureCertificatePath()), pwd, pwd);
        logger.info("{} 开始签章", transCodedInputPath);
        setPdfSignature4KGBatch(signature, signServerUrl, signatureContainers);
        signature.doSignature();
        logger.info("{} 签章完毕, 文件 {} 已生成", transCodedInputPath, transCodedOutPath);
        closeSignature(signature);
    }

    private void closeSignature(KGPdfHummer signature) {
        if (Objects.nonNull(signature)) {
            signature.close();
        }
    }

    /**
     * 批量设置章，用于处理一个文件盖多个章的场景
     *
     * @param signature           签章对象（金格api）
     * @param signServerUrl       签章服务器地址
     * @param signatureContainers 封装了签章规则和签章信息，适用于多个章
     */
    private void setPdfSignature4KGBatch(KGPdfHummer signature, String signServerUrl, List<SignatureContainer> signatureContainers) {
        signatureContainers.forEach(signatureContainer -> {
            PdfSignature4KG pdfSignature4KG = new PdfSignature4KG(
                    signServerUrl,
                    KGServerTypeEnum.NET,
                    signatureContainer.getKeyNumber(), signatureContainer.getKeyPwd(), signatureContainer.getSignatureName());
            setSignaturePosition(signatureContainer.getSealType(), signatureContainer.getSealPositionByText(), pdfSignature4KG);
            setSignaturePages(signatureContainer.getSealPositionPagesArray(), pdfSignature4KG, signature);
            signature.setPdfSignature(pdfSignature4KG);
            signature.addExecute(pdfSignature4KG);
        });
    }

    /**
     * 设置章要盖的位置
     *
     * @param signType          签章方式
     * @param signPosition      签章位置
     * @param pdfSignature4KG   被签章的 pdf 对象（金格api）
     */
    private void setSignaturePosition(String signType, String signPosition, PdfSignature4KG pdfSignature4KG) {
        if (Constant.SEAL_TYPE_POSITION_TEXT.equals(signType)) {
            logger.info("签章方式为文字定位");
            pdfSignature4KG.setText(signPosition);
        } else {
            logger.info("签章方式为像素定位");
            String[] positions = signPosition.split(",");
            pdfSignature4KG.setXY(Float.parseFloat(positions[0]), Float.parseFloat(positions[1]));
        }
    }

    private void setSignaturePages(int[] signPage, PdfSignature4KG pdfSignature4KG, KGPdfHummer signature) {
        logger.info("设置签章所在页，页数：{}", CommonUtil.array2Str(signPage));
        if (signPage.length > 0) {
            if (signPage.length == 1) {
                int lastPage = signature.getNumberOfPages();
                if (signPage[0] == -1) {
                    pdfSignature4KG.setPagen(lastPage);
                } else {
                    if (signPage[0] == 0) {
                        int[] pageNums = new int[lastPage];
                        for (int i = 1; i <= lastPage; i++) {
                            pageNums[i - 1] = i;
                        }
                        pdfSignature4KG.setPagen(pageNums);
                    } else {
                        pdfSignature4KG.setPagen(signPage[0]);
                    }
                }
            } else {
                int lastNum = signPage[signPage.length - 1];
                if (lastNum == -1) {
                    signPage[signPage.length - 1] = signature.getNumberOfPages();
                }
                pdfSignature4KG.setPagen(signPage);
            }
        }
    }
    private KGPdfHummer createKGPdfHummer(InputStream fis, OutputStream fos) throws IOException, DocumentException {
        KGPdfHummer signature;
        logger.info("创建签章对象");
        signature = KGPdfHummer.createSignature(fis, null, fos, null, true);
        logger.info("创建签章对象完成");
        return signature;
    }
}
