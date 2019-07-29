package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.dao.entity.SignatureLogInfo;
import com.fotic.it.support.signature.dao.mapper.SignatureLogInfoMapper;
import com.fotic.it.support.signature.service.SignatureLogInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SignatureLogInfo)表服务实现类
 *
 * @author makejava-word2pdf
 * @since 2019-06-17 10:35:01
 */
@Service("signatureLogInfoService")
public class SignatureLogInfoServiceImpl implements SignatureLogInfoService {
    @Resource
    private SignatureLogInfoMapper signatureLogInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<SignatureLogInfo> queryById(Long id) {
        return signatureLogInfoMapper.findAllById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SignatureLogInfo> queryAllByLimit(int offset, int limit) {
        return signatureLogInfoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 获取max ID
     */
    @Override
    public String queryMax(){
        return signatureLogInfoMapper.findMax();
    }

    /**
     * 新增数据
     *
     * @param signatureLogInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureLogInfo> insert(SignatureLogInfo signatureLogInfo) {
        signatureLogInfo.setId(Long.valueOf(signatureLogInfoMapper.findMax()) + 1);
        signatureLogInfoMapper.insert(signatureLogInfo);
        return this.queryById(signatureLogInfo.getId());
    }

    /**
     * 修改数据
     *
     * @param signatureLogInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureLogInfo> update(SignatureLogInfo signatureLogInfo) {
        signatureLogInfoMapper.update(signatureLogInfo);
        return this.queryById(signatureLogInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(int id) {
        return signatureLogInfoMapper.deleteById(id) > 0;
    }
}