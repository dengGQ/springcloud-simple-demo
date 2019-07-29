package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.constant.ResultStatusCodeEnum;
import com.fotic.it.support.signature.dao.entity.SignConfigurationInfoEntity;
import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.mapper.EosDictEntryMapper;
import com.fotic.it.support.signature.exception.commons.BusinessException;
import com.fotic.it.support.signature.model.ExInfo;
import com.fotic.it.support.signature.model.FtpInfo;
import com.fotic.it.support.signature.model.SignatureContainer;
import com.fotic.it.support.signature.model.SignatureRule;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.fotic.it.support.signature.service.SignatureInfoService;
import com.fotic.it.support.signature.service.SignatureService;
import com.fotic.it.support.signature.service.SignatureTaskService;
import com.fotic.it.support.signature.util.CharacterEncodingUtil;
import com.fotic.it.support.signature.util.CommonUtil;
import com.fotic.it.support.signature.util.FtpFileOperator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: mfh
 * @Date: 2019-06-17 14:10
 **/
@Service
public class SignatureServiceImpl implements SignatureService {
    private static final Logger logger = LoggerFactory.getLogger(SignatureServiceImpl.class);
    private EosDictEntryMapper eosDictEntryMapper;
    private SignatureTaskService signatureTaskService;
    private SignatureInfoService signatureInfoService;
    private FtpInfo ftpInfo;
    private FtpFileOperator ftpOperator = new FtpFileOperator();
    private String signServerUrl;
    private ElectronicsignatureInfoLogService electronicsignatureInfoLogService;

    public SignatureServiceImpl() {
    }

    @PostConstruct
    public void initialize() {
        SignConfigurationInfoEntity entity = eosDictEntryMapper.getSignConfigurationInfoEntity();
        initializeFtpInfo(entity);
        signServerUrl = eosDictEntryMapper.getDictName("signature_sealServePath", "sealServePath");
    }

    @Override
    public boolean signatureFolder(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo, String sysName, String keyPwd) {
        printArgs(inputFilePath, outputFilePath, rule, signatureInfo);
        boolean isExistFailure = false;
        boolean isFailure;
        if (checkOutputFilePathAndCreate(inputFilePath, outputFilePath, sysName, keyPwd)) {
            return true;
        }
        LinkedMultiValueMap<String, String> map = makeOutputPathDictionary(inputFilePath, outputFilePath);
        Collection<List<String>> values = map.values();
        for (List<String> absoluteInputFilePathList : values) {
            for (String absoluteInputPathFilePath : absoluteInputFilePathList) {
                if (!StringUtils.isEmpty(absoluteInputPathFilePath) && absoluteInputPathFilePath.endsWith(Constant.PDF_SUFFIX)) {
                    String absoluteOutputFilePath = absoluteInputPathFilePath.replace(inputFilePath, outputFilePath);
                    Future<Integer> signatureResult = this.signature(absoluteInputPathFilePath, absoluteOutputFilePath, rule, signatureInfo, sysName, keyPwd);
                    isFailure = this.getTaskResult(
                            signatureResult,
                            inputFilePath, outputFilePath,
                            sysName, keyPwd,
                            rule.getSealType(), rule.getSealPositionByText(), String.valueOf(signatureInfo.getSignid()), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
                    if (isFailure && !isExistFailure) {
                        isExistFailure = true;
                    }
                }
            }
        }
        deleteDirectoryByIsExistFailure(inputFilePath, outputFilePath, isExistFailure, map);
        return isExistFailure;
    }

    @Override
    public boolean signatureFolder(String inputFilePath, String outputFilePath, List<SignatureRule> rules, String sysName, String keyPwd) {
        boolean isExistFailure = false;
        boolean isFailure;
        if (checkOutputFilePathAndCreate(inputFilePath, outputFilePath, sysName, keyPwd)) {
            return true;
        }
        List<SignatureContainer> signatureContainers = this.findSignatureObjs(rules);

        LinkedMultiValueMap<String, String> map = makeOutputPathDictionary(inputFilePath, outputFilePath);
        Collection<List<String>> values = map.values();
        for (List<String> absoluteInputFilePathList : values) {
            for (String absoluteInputPathFilePath : absoluteInputFilePathList) {
                if (!StringUtils.isEmpty(absoluteInputPathFilePath) && absoluteInputPathFilePath.endsWith(Constant.PDF_SUFFIX)) {
                    String absoluteOutputFilePath = absoluteInputPathFilePath.replace(inputFilePath, outputFilePath);
                    Future<Integer> signatureResult = this.signature(absoluteInputPathFilePath, absoluteOutputFilePath, signatureContainers, sysName, keyPwd);
                    isFailure = this.getTaskResult(
                            signatureResult,
                            inputFilePath, outputFilePath,
                            sysName, keyPwd,
                            signatureContainers);
                    if (isFailure && !isExistFailure) {
                        isExistFailure = true;
                    }
                }
            }
        }
        deleteDirectoryByIsExistFailure(inputFilePath, outputFilePath, isExistFailure, map);
        return isExistFailure;
    }

    /**
     * 根据 isExistFailure{@link SignatureServiceImpl#signatureFolder} 标记决定是否删除文件夹
     * 如果标记为 true，说明存在失败的任务，则不删除文件；如果标记为 false，说明不存在失败的任务，则删除文件夹
     * @param inputFilePath     输入路径
     * @param outputFilePath    输出路径
     * @param isExistFailure    是否存在失败任务标记
     * @param map               被删除的文件夹路径
     */
    private void deleteDirectoryByIsExistFailure(String inputFilePath, String outputFilePath, boolean isExistFailure, LinkedMultiValueMap<String, String> map) {
        if (!isExistFailure) {
            /* 如果没有失败的任务，就删除文件夹 */
            //deleteDirectory(inputFilePath);
            deleteDirectory(map.keySet());
            logger.info("输入参数为：{}，输出参数为：{} ，签章文件夹执行成功", inputFilePath, outputFilePath);
        }
    }

    @Override
    public boolean signatureSingleFile(String inputFilePath, String outputFilePath, List<SignatureRule> rules, String sysName, String keyPwd) {
        boolean isFailure = true;
        if (checkOutputFilePathAndCreate(inputFilePath, outputFilePath, sysName, keyPwd)) {
            return true;
        }

        List<SignatureContainer> signatureContainers = this.findSignatureObjs(rules);
        if (isFileExist(inputFilePath)) {
            String path = StringUtils.cleanPath(inputFilePath);
            String filename = StringUtils.getFilename(path);
            String outputPath = outputFilePath.contains(Constant.PDF_SUFFIX) ? outputFilePath : outputFilePath + filename;
            Future<Integer> signature = this.signature(inputFilePath, outputPath, signatureContainers, sysName, keyPwd);
            isFailure = this.getTaskResult(
                    signature,
                    inputFilePath, outputPath,
                    sysName, keyPwd,
                    signatureContainers);
            if (!isFailure) {
                logger.info("输入参数为：{}，输出参数为：{} ，签章单文件执行成功", inputFilePath, outputPath);
            }
        } else {
            electronicsignatureInfoLogService.recordLog(
                    ResultStatusCodeEnum.NO_EXIST_FILE.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.NO_EXIST_FILE.getCode()),
                    inputFilePath, inputFilePath, outputFilePath,
                    sysName, keyPwd,
                    null, null, null, null);
            logger.error("被签章的单文件 {} 不存在", inputFilePath);
        }
        return isFailure;
    }

    @Override
    public boolean signatureSingleFile(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo, String sysName, String keyPwd) {
        boolean isFailure = true;
        if (checkOutputFilePathAndCreate(inputFilePath, outputFilePath, sysName, keyPwd)) {
            return true;
        }
        if (isFileExist(inputFilePath)) {
            String path = StringUtils.cleanPath(inputFilePath);
            String filename = StringUtils.getFilename(path);
            Future<Integer> signatureResult = this.signature(inputFilePath, outputFilePath + filename, rule, signatureInfo, sysName, keyPwd);
            isFailure = this.getTaskResult(
                    signatureResult,
                    inputFilePath, outputFilePath,
                    sysName, keyPwd,
                    rule.getSealType(), rule.getSealPositionByText(), String.valueOf(signatureInfo.getSignid()), CommonUtil.array2Str(rule.getSealPositionPagesArray()));

        } else {
            electronicsignatureInfoLogService.recordLog(
                    ResultStatusCodeEnum.NO_EXIST_FILE.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.NO_EXIST_FILE.getCode()),
                    inputFilePath, inputFilePath, outputFilePath,
                    sysName, keyPwd,
                    null, null, null, null);
            logger.error("被签章的单文件 {} 不存在", inputFilePath);
        }
        return isFailure;
    }


    private String checkOutputFilePath(String outputPath) {
        String replacedPath = StringUtils.cleanPath(outputPath);
        if (replacedPath.endsWith(Constant.PDF_SUFFIX)) {
            /* 如果路径下的文件名是 xxx/yyy/.pdf 则会抛出异常 */
            if (replacedPath.lastIndexOf(Constant.FORWARD_SLASH) + 1 == replacedPath.lastIndexOf(".")) {
                throw new BusinessException(replacedPath + " 路径下的文件名定义异常");
            }
            String substring = replacedPath.substring(0, replacedPath.lastIndexOf(Constant.FORWARD_SLASH));
            replacedPath = substring;
        }
        return replacedPath;
    }
    private boolean checkOutputFilePathAndCreate(String inputFilePath, String outputFilePath, String sysName, String keyPwd) {
        logger.info("检查输出路径 {} 是否存在", outputFilePath);
        boolean flag = true;
        FTPClient ftpClient = ftpOperator.getConnectionFTP(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd());
        ExInfo exInfo = new ExInfo(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
        try {
            String replacedPath = checkOutputFilePath(outputFilePath);
            if (!ftpClient.changeWorkingDirectory(replacedPath)) {
                StringTokenizer stringTokenizer = new StringTokenizer(replacedPath, "/");
                StringBuffer path = new StringBuffer();
                while (stringTokenizer.hasMoreElements()) {
                    ftpClient.mkd(String.valueOf(path.append("/").append(stringTokenizer.nextElement())));
                    logger.info("创建 {} 文件路径成功", path);
                }
            }
            flag = false;
        } catch (IOException e) {
            exInfo.setInfo("创建 " + outputFilePath + " 路径文件夹异常，请检查 FTP 服务是否连接正常");
            exInfo.setStatusCode(ResultStatusCodeEnum.ERROR_FTP_CONNECTION.getCode());
            exInfo.setEx(e);
        } catch (BusinessException e) {
            exInfo.setInfo(e.getMessage());
            exInfo.setStatusCode(ResultStatusCodeEnum.EMPTY_FILENAME.getCode());
            exInfo.setEx(e);
        } finally {
            ftpOperator.closeFTP(ftpClient);
        }

        if (flag) {
            exInfo.printStackTrace();
            logger.error(exInfo.getInfo());
            electronicsignatureInfoLogService.recordLog(
                    exInfo.getStatusCode(), exInfo.getInfo(),
                    inputFilePath, inputFilePath, outputFilePath,
                    sysName, keyPwd,
                    null, null, null, null);
        }
        return flag;
    }

    private boolean getTaskResult(Future<Integer> signatureResult,
                                  String inputFilePath, String outputFilePath,
                                  String sysName, String keyPwd,
                                  List<SignatureContainer> signatureContainers) {
        /* 签章任务是否失败 */
        boolean isExistFailureEveryTask = false;
        /* 签章任务是否捕获到异常*/
        boolean isCatchEx = true;
        ExInfo exInfo = new ExInfo(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
        try {
            isExistFailureEveryTask = (signatureResult.get(5, TimeUnit.MINUTES) != ResultStatusCodeEnum.SUCCESS.getCode());
            isCatchEx = false;
        } catch (TimeoutException e) {
            exInfo.setEx(e);
            exInfo.setStatusCode(ResultStatusCodeEnum.SIGNATURE_TASK_TIMEOUT.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SIGNATURE_TASK_TIMEOUT.getCode()));
        } catch (InterruptedException e) {
            exInfo.setEx(e);
            exInfo.setStatusCode(ResultStatusCodeEnum.SIGNATURE_TASK_INTERRUPTED.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SIGNATURE_TASK_INTERRUPTED.getCode()));
        }  catch (Exception e) {
            exInfo.setInfo(e.getMessage());
            exInfo.setEx(e);
        } finally {
            if (isCatchEx) {
                exInfo.printStackTrace();
                logger.error(exInfo.getMessage());
                electronicsignatureInfoLogService.recordLog(
                        exInfo.getStatusCode(), exInfo.getMessage(),
                        inputFilePath, inputFilePath, outputFilePath,
                        sysName, keyPwd,
                        signatureContainers);
            } else {
                /* 如果没有捕获到异常，只是单纯任务失败，这里就不进行日志记录了，因为在回调中已经记录过了，这里只是记录超时任务异常 */
            }
        }
        return isExistFailureEveryTask;
    }

    private boolean getTaskResult(Future<Integer> signatureResult,
                                  String inputFilePath, String outputFilePath,
                                  String sysName, String keyPwd,
                                  String sealType, String sealPosition, String sealIndex, String sealPage) {
        /* 签章任务是否失败 */
        boolean isExistFailureEveryTask = false;
        /* 签章任务是否捕获到异常*/
        boolean isCatchEx = true;
        ExInfo exInfo = new ExInfo(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode(), ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.REQUEST_EXCEPTION.getCode()));
        try {
            isExistFailureEveryTask = (signatureResult.get(3, TimeUnit.MINUTES) != ResultStatusCodeEnum.SUCCESS.getCode());
            isCatchEx = false;
        } catch (TimeoutException e) {
            exInfo.setEx(e);
            exInfo.setStatusCode(ResultStatusCodeEnum.SIGNATURE_TASK_TIMEOUT.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SIGNATURE_TASK_TIMEOUT.getCode()));
        } catch (InterruptedException e) {
            exInfo.setEx(e);
            exInfo.setStatusCode(ResultStatusCodeEnum.SIGNATURE_TASK_INTERRUPTED.getCode());
            exInfo.setInfo(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.SIGNATURE_TASK_INTERRUPTED.getCode()));
        } catch (Exception e) {
            exInfo.setInfo(e.getMessage());
            exInfo.setEx(e);
        } finally {
            if (isCatchEx) {
                exInfo.printStackTrace();
                logger.error(exInfo.getMessage());
                electronicsignatureInfoLogService.recordLog(
                        exInfo.getStatusCode(), exInfo.getMessage(),
                        inputFilePath, inputFilePath, outputFilePath,
                        sysName, keyPwd,
                        sealType, sealPosition, sealIndex, sealPage);
            } else {
                /* 如果没有捕获到异常，只是单纯任务失败，这里就不进行日志记录了，因为在回调中已经记录过了，这里只是记录超时任务异常 */
            }
        }
        return isExistFailureEveryTask;
    }

    private List<SignatureContainer> findSignatureObjs(List<SignatureRule> rules) {
        List<SignatureContainer> signatureContainers = new ArrayList<>();
        for (SignatureRule rule : rules) {
            SignatureInfo signatureInfo = signatureInfoService.queryBySignId(rule.getSealIndex()).get(0);
            signatureContainers.add(new SignatureContainer(rule, signatureInfo));
        }
        return signatureContainers;
    }

    private LinkedMultiValueMap<String, String> makeOutputPathDictionary(String inputFilePath, String outputFilePath) {
        LinkedMultiValueMap<String, String> map = folderStructureConvert2Map(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd(), inputFilePath);
        Set<String> inputFolderPathSet = map.keySet();
        List<String> outputFolderPathSet = generateOutputPathByInputPath(inputFolderPathSet, inputFilePath, outputFilePath);
        doMakeOutputPathDictionary(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd(), outputFolderPathSet);
        return map;
    }

    private void deleteDirectory(String inputFilePath) {
        FTPClient ftpClient = ftpOperator.getConnectionFTP(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd());
        try {
            deleteDirectory(ftpClient, inputFilePath);
        } finally {
            ftpOperator.closeFTP(ftpClient);
        }
    }


    /**
     * 将给定的路径下的文件夹与文件的关系转换成 map 集合
     * @return LinkedMultiValueMap 的 key 为文件夹路径，value 为此文件夹下的子文件和子文件夹路径
     */
    private LinkedMultiValueMap<String, String> folderStructureConvert2Map(String address, Integer port, String username, String password, String inputPath) {
        logger.info("将 {} 目录中所有文件结构转化成 Map", inputPath);
        FtpFileOperator operator = new FtpFileOperator();
        FTPClient ftp = null;
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        try {
            ftp = operator.getConnectionFTP(address, port, username, password);
            logger.info("开始递归调用");
            this.getFileRecursive(map, ftp, inputPath);
            logger.info("结束递归调用");
            this.printMap(map);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.info("将 {} 目录结构转化成 map 结构出现异常", inputPath);
        } finally {
            try {
                if (Objects.nonNull(ftp)) {
                    ftp.logout();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                if (Objects.nonNull(ftp)) {
                    operator.closeFTP(ftp);
                }
            }
        }
        return map;
    }

    private void printMap(LinkedMultiValueMap<String, String> map) {
        logger.info("输入文件路径转换 map 结果");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            logger.info("父文件夹：{}", entry.getKey());
            List<String> value = entry.getValue();
            for (String val : value) {
                String transCodedVal;
                try {
                    transCodedVal = CharacterEncodingUtil.gbk2Utf8(val);
                } catch (UnsupportedEncodingException e) {
                    logger.error(ResultStatusCodeEnum.getDescription(ResultStatusCodeEnum.LOG_OUTPUT_TRANS_CODE.getCode()), e);
                    transCodedVal = val;
                }
                logger.info("{} 的子{}：{}", entry.getKey(), transCodedVal.toLowerCase().contains(Constant.PDF_SUFFIX) ? "文件" : "文件夹", "".equals(transCodedVal.trim()) ? "无" : transCodedVal);
            }
        }
    }

    /**
     * 生成输出文件夹路径。将集合中的每一个元素的 inputPath 部分替换成 outputPath，然后将每一个 outputPath 放进新的集合并按照升序排列
     *
     * @param subFolders 存储了 inputPath 的所有子文件夹
     * @param inputPath  输入路径
     * @param outputPath 输出路径
     * @return 输出文件夹路径集合
     */
    private List<String> generateOutputPathByInputPath(Set<String> subFolders, String inputPath, String outputPath) {
        logger.info("生成输出文件夹路径集合");
        Set<String> outputPathSubFolders = new HashSet<>();
        for (String folder : subFolders) {
            outputPathSubFolders.add(folder.replace(inputPath, outputPath));
        }
        List<String> folderList = new ArrayList<>(outputPathSubFolders);
        Collections.sort(folderList);
        logger.info("生成输出文件夹路径集合完毕，生成结果：");
        for (String folder : folderList) {
            logger.info("输出路径：{}", folder);
        }
        return folderList;
    }

    /**
     * 创建输出文件夹，用于存放签章后的文件
     * @param folders 文件夹路径集合
     */
    private void doMakeOutputPathDictionary(String address, Integer port, String username, String password, List<String> folders) {
        logger.info("将要在 {} 服务器创建输出文件夹", address);
        FtpFileOperator operator = new FtpFileOperator();
        FTPClient ftp;
        ftp = operator.getConnectionFTP(address, port, username, password);
        Iterator<String> iterator = folders.iterator();
        String folder = null;
        try {
            while (iterator.hasNext()) {
                folder = iterator.next();

                if (ftp.changeWorkingDirectory(folder)) {
                    logger.warn("{} 目录已存在，不再进行创建", folder);
                } else {
                    boolean result = ftp.makeDirectory(new String(folder.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
                    if (result) {
                        logger.info("{} 创建成功", folder);
                    } else {
                        logger.info("{} 创建失败", folder);
                    }
                }
            }
            logger.info("在 {} 服务器创建输出文件夹完毕", address);
        } catch (IOException e) {
            logger.error("在 {} 服务器创建输出文件夹出现异常，出现异常的路径：{}", address, folder);
            logger.error(e.getMessage());
        } finally {
            try {
                ftp.logout();
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                operator.closeFTP(ftp);
            }
        }
    }

    /***
     * 签章
     * @param inputFilePath     输入路径
     * @param outputFilePath    输出路径
     * @param rule              签章规则对象
     */
    private Future<Integer> signature(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo, String sysName, String keyPwd) {
        logger.info("准备进入签章任务执行方法");
        return signatureTaskService.executeSignature(
                this.ftpInfo.getAddress(), this.ftpInfo.getName(), this.ftpInfo.getPwd(),
                inputFilePath, outputFilePath,
                this.signServerUrl, signatureInfo.getKeynumber(), signatureInfo.getKeypwd(), signatureInfo.getSignaturename(),
                rule.getSealType(), rule.getSealPositionByText(), rule.getSealPositionPagesArray(), String.valueOf(signatureInfo.getSignid()),
                sysName, keyPwd);
    }

    /**
     * 签章
     * @param inputFilePath         输入路径
     * @param outputFilePath        输出路径
     * @param signatureContainers   封装了 SignatureRule 与 SignatureInfo
     * @return                      签章任务执行结果
     */
    private Future<Integer> signature(String inputFilePath, String outputFilePath, List<SignatureContainer> signatureContainers, String sysName, String keyPwd) {
        logger.info("准备进入签章任务执行方法");
        return signatureTaskService.executeSignature(
                this.ftpInfo.getAddress(), this.ftpInfo.getName(), this.ftpInfo.getPwd(),
                inputFilePath, outputFilePath, this.signServerUrl,
                signatureContainers, sysName, keyPwd);
    }

    private void deleteDirectory(Set<String> folderPath) {
        FTPClient ftpClient = ftpOperator.getConnectionFTP(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd());
        List<String> folderList = new ArrayList<>(folderPath);
        Collections.sort(folderList);
        Collections.reverse(folderList);
        for (String path : folderList) {
            try {
                FTPFile[] ftpFiles = ftpClient.listFiles(new String(path.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
                if (ftpFiles.length == 0) {
                    ftpClient.removeDirectory(new String(path.substring(0, path.lastIndexOf("/")).getBytes(Constant.CHARACTER_ENCODING_GBK), Constant
                            .CHARACTER_ENCODING_8859));
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        ftpOperator.closeFTP(ftpClient);
    }

    private void deleteDirectory(FTPClient ftp, String input) {
        try {
            FTPFile[] files = ftp.listFiles(new String(input.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
            if (files.length > 0) {
                for (FTPFile file : files) {
                    String filename = file.getName();
                    if (file.isDirectory()) {
                        deleteDirectory(ftp, input + filename + "/");
                    } else {
                        break;
                    }
                }
                FTPFile[] ftpFiles = ftp.listFiles(new String(input.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
                if (ftpFiles.length == 0) {
                    boolean b = ftp.removeDirectory(new String(input.substring(0, input.lastIndexOf("/")).getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
                    logger.info("删除：{}  {}", input, b ? "成功" : "失败");
                }
            } else {
                boolean b = ftp.removeDirectory(new String(input.substring(0, input.lastIndexOf("/")).getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
                logger.info("删除：{} {}", input, b ? "成功" : "失败");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void getFileRecursive(LinkedMultiValueMap<String, String> map, FTPClient client, String inputPath) throws IOException {
        FTPFile[] ftpFiles = client.listFiles(new String(inputPath.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859));
        if (ftpFiles.length == 0) {
            map.add(inputPath, "");
        } else {
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isDirectory()) {
                    map.add(inputPath, inputPath + ftpFile.getName());
                    this.getFileRecursive(map, client, inputPath + ftpFile.getName() + "/");
                } else if (CommonUtil.isPdf(ftpFile.getName())) {
                    map.add(inputPath, inputPath + ftpFile.getName());
                } else {
                    logger.info("{} 不是文件夹，也不是 pdf 类型的文件", ftpFile.getName());
                }
            }
        }
    }

    private void initializeFtpInfo(SignConfigurationInfoEntity entity) {
        this.ftpInfo = new FtpInfo(entity.getFtpAddress(), entity.getFtpUser(), entity.getFtpPwd(), entity.getFtpPort());
    }

    /**
     * 判断文件是否存在
     * @param inputPath     输入路径
     * @return              存在：true；不存在：false
     */
    private boolean isFileExist(String inputPath) {
        logger.info("检查输入文件路径 {} 是否存在", inputPath);
        String path = StringUtils.cleanPath(inputPath);
        FTPClient ftpClient = null;
        boolean isExist = false;
        try {
            ftpClient = ftpOperator.getConnectionFTP(this.ftpInfo.getAddress(), this.ftpInfo.getPort(), this.ftpInfo.getName(), this.ftpInfo.getPwd());
            if (ftpClient.retrieveFileStream(path) == null) {
                logger.error("{} 文件不存在", path);
            } else {
                isExist = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (Objects.nonNull(ftpClient)) {
                ftpOperator.closeFTP(ftpClient);
            }
        }
        return isExist;
    }

    private void printArgs(String inputFilePath, String outputFilePath, SignatureRule rule, SignatureInfo signatureInfo) {
        logger.info("输入文件路径：{}，输出文件路径：{}", inputFilePath, outputFilePath);
        logger.info("签章编号：{}，签章定位：{}，签章方式：{},签章所在页：{}", rule.getSealIndex(), rule.getSealPositionByText(), rule.getSealType(), CommonUtil.array2Str(rule.getSealPositionPagesArray()));
        logger.info("签章序号：{}，章名称：{}", signatureInfo.getKeynumber(), signatureInfo.getSignaturename());
    }

    @Autowired
    public void setEosDictEntryMapper(EosDictEntryMapper eosDictEntryMapper) {
        this.eosDictEntryMapper = eosDictEntryMapper;
    }

    @Autowired
    public void setSignatureTaskService(SignatureTaskService signatureTaskService) {
        this.signatureTaskService = signatureTaskService;
    }

    @Autowired
    public void setSignatureInfoService(SignatureInfoService signatureInfoService) {
        this.signatureInfoService = signatureInfoService;
    }

    @Autowired
    public void setElectronicsignatureInfoLogService(ElectronicsignatureInfoLogService electronicsignatureInfoLogService) {
        this.electronicsignatureInfoLogService = electronicsignatureInfoLogService;
    }
}
