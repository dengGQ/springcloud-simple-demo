package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import com.fotic.it.support.signature.dao.mapper.SignatureInfoMapper;
import com.fotic.it.support.signature.service.SignatureInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SignatureInfo)表服务实现类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:05:06
 */
@Service("signatureInfoService")
public class SignatureInfoServiceImpl implements SignatureInfoService {
    @Resource
    private SignatureInfoMapper signatureInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param signid 主键
     * @return 实例对象
     */
    @Override
    public List<SignatureInfo> queryBySignId(Integer signid) {
        return signatureInfoMapper.findAllBySignId(signid);
    }

    @Override
    public List<SignatureInfo> queryById(Integer id) {
        return signatureInfoMapper.findAllByID(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SignatureInfo> queryAllByLimit(int offset, int limit) {
        return signatureInfoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param signatureInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureInfo> insert(SignatureInfo signatureInfo) {
        signatureInfoMapper.insert(signatureInfo);
        return this.queryBySignId(signatureInfo.getId());
    }

    /**
     * 修改数据
     *
     * @param signatureInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureInfo> update(SignatureInfo signatureInfo) {
        signatureInfoMapper.update(signatureInfo);
        return this.queryBySignId(signatureInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return signatureInfoMapper.deleteById(id) > 0;
    }
}