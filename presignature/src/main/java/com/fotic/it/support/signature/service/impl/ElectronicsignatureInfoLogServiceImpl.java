package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.constant.Constant;
import com.fotic.it.support.signature.dao.entity.ElectronicsignatureInfoLog;
import com.fotic.it.support.signature.dao.mapper.ElectronicsignatureInfoLogMapper;
import com.fotic.it.support.signature.model.SignatureContainer;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.fotic.it.support.signature.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 重构后电子签章日志(ElectronicsignatureInfoLog)表服务实现类
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-04 15:55:12
 */
@Service("electronicsignatureInfoLogService")
public class ElectronicsignatureInfoLogServiceImpl implements ElectronicsignatureInfoLogService {
    @Autowired
    private ElectronicsignatureInfoLogMapper electronicsignatureInfoLogMapper;
    private static Logger logger = LoggerFactory.getLogger(ElectronicsignatureInfoLogServiceImpl.class);

    /**
     * 获取全部数据
     * @param pageNum
     * @param pageSize
     * @param fileName
     * @param code
     * @return
     */
    @Override
    public PageInfo<ElectronicsignatureInfoLog> findAll(int pageNum, int pageSize,
                                             String fileName,
                                             String code,
                                             String createDateStart,
                                             String createDateEnd,
                                             String info,
                                             String inputPath,
                                             String outputPath){
        ElectronicsignatureInfoLog electronicsignatureInfoLog = new ElectronicsignatureInfoLog();
        if (fileName != null && !"".equals(fileName)){
            electronicsignatureInfoLog.setFilename(fileName);
        }
        if (code != null && !"".equals(code)){
            electronicsignatureInfoLog.setCode(code);
        }
        if(createDateStart != null && !"".equals(createDateStart)){
            electronicsignatureInfoLog.setCreateDateStartStr(createDateStart);
        }
        if(createDateEnd != null && !"".equals(createDateEnd)){
            electronicsignatureInfoLog.setCreateDateEndStr(createDateEnd);
        }
        if(info != null && !"".equals(info)){
            electronicsignatureInfoLog.setInfo(info);
        }
        if(inputPath != null && !"".equals(inputPath)){
            electronicsignatureInfoLog.setInputPath(inputPath);
        }
        if(outputPath != null && !"".equals(outputPath)){
            electronicsignatureInfoLog.setOutputPath(outputPath);
        }
        PageHelper.startPage(Integer.valueOf(pageNum),Integer.valueOf(pageSize));
        List<ElectronicsignatureInfoLog> electronicsignatureInfoLogs = electronicsignatureInfoLogMapper.queryAll(electronicsignatureInfoLog);
        PageInfo<ElectronicsignatureInfoLog> pageInfoLog = new PageInfo(electronicsignatureInfoLogs);
        return pageInfoLog;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<ElectronicsignatureInfoLog> queryById(String id) {
        return electronicsignatureInfoLogMapper.findAllById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ElectronicsignatureInfoLog> queryAllByLimit(int offset, int limit) {
        return electronicsignatureInfoLogMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param electronicsignatureInfoLog 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(ElectronicsignatureInfoLog electronicsignatureInfoLog) {
        logger.info("执行insert动作");
        int resultcount = electronicsignatureInfoLogMapper.insert(electronicsignatureInfoLog);
        return resultcount;
    }

    /**
     * 修改数据
     *
     * @param electronicsignatureInfoLog 实例对象
     * @return 实例对象
     */
    @Override
    public List<ElectronicsignatureInfoLog> update(ElectronicsignatureInfoLog electronicsignatureInfoLog) {
        electronicsignatureInfoLogMapper.update(electronicsignatureInfoLog);
        return queryById(electronicsignatureInfoLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return electronicsignatureInfoLogMapper.deleteById(id) > 0;
    }

    @Override
    public int recordLog(int statusCode, String simpleInfo,
                         String fileName, String inputPath, String outputPath,
                         String sysName, String keyPwd, String signType, String signPosition, String sealIndex, String signPage) {
        ElectronicsignatureInfoLog log = new ElectronicsignatureInfoLog();
        log.setId(UUID.randomUUID().toString());
        log.setCode(String.valueOf(statusCode));
        log.setInfo(simpleInfo);
        try {
            log.setFilename(new String(fileName.getBytes(Constant.CHARACTER_ENCODING_GBK), StandardCharsets.UTF_8));
            log.setInputPath(new String(inputPath.getBytes(Constant.CHARACTER_ENCODING_GBK), StandardCharsets.UTF_8));
            log.setOutputPath(new String(outputPath.getBytes(Constant.CHARACTER_ENCODING_GBK), StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            log.setFilename("字符集不支持异常");
            log.setInputPath("字符集不支持异常");
            log.setOutputPath("字符集不支持异常");
        }
        log.setSysName(sysName);
        log.setKeypwd(keyPwd);
        log.setSealtype(signType);
        log.setSealpositionbytext(signPosition);
        log.setSealindex(sealIndex);
        log.setSealpositionpage(signPage);
        log.setCreatetime(new Date());
        return insert(log);
    }

    @Override
    public int recordLog(int statusCode, String simpleInfo, String fileName, String inputPath, String outputPath, String sysName, String keyPwd, List<SignatureContainer> signatureContainers) {
        return recordLog(statusCode, simpleInfo, fileName, inputPath, outputPath, sysName, keyPwd, this.getSealTypeBatch(signatureContainers), this.getSealPositionBatch(signatureContainers), this.getSealIndexBatch(signatureContainers), this.getSignPage(signatureContainers));
    }

    private String getSealTypeBatch(List<SignatureContainer> signatureContainers) {
        StringBuilder sb = new StringBuilder();
        for (SignatureContainer signatureContainer : signatureContainers) {
            sb.append(signatureContainer.getSealType()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getSealPositionBatch(List<SignatureContainer> signatureContainers) {
        StringBuilder sb = new StringBuilder();
        for (SignatureContainer signatureContainer : signatureContainers) {
            sb.append("[").append(signatureContainer.getSealPositionByText()).append("]").append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getSealIndexBatch(List<SignatureContainer> signatureContainers) {
        StringBuilder sb = new StringBuilder();
        for (SignatureContainer signatureContainer : signatureContainers) {
            sb.append(signatureContainer.getSealIndex()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getSignPage(List<SignatureContainer> signatureContainers) {
        StringBuilder sb = new StringBuilder();
        for (SignatureContainer signatureContainer : signatureContainers) {
            sb.append("[").append(CommonUtil.array2Str(signatureContainer.getSealPositionPagesArray())).append("]").append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}