package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.dao.entity.SignatureConfiguration;
import com.fotic.it.support.signature.dao.mapper.SignatureConfigurationMapper;
import com.fotic.it.support.signature.service.SignatureConfigurationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SignatureConfiguration)表服务实现类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:09:32
 */
@Service("signatureConfigurationService")
public class SignatureConfigurationServiceImpl implements SignatureConfigurationService {
    @Resource
    private SignatureConfigurationMapper signatureConfigurationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<SignatureConfiguration> queryById(Integer id) {
        return signatureConfigurationMapper.findAllById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SignatureConfiguration> queryAllByLimit(int offset, int limit) {
        return signatureConfigurationMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param signatureConfiguration 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureConfiguration> insert(SignatureConfiguration signatureConfiguration) {
        signatureConfigurationMapper.insert(signatureConfiguration);
        return this.queryById(signatureConfiguration.getId());
    }

    /**
     * 修改数据
     *
     * @param signatureConfiguration 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignatureConfiguration> update(SignatureConfiguration signatureConfiguration) {
        signatureConfigurationMapper.update(signatureConfiguration);
        return this.queryById(signatureConfiguration.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return signatureConfigurationMapper.deleteById(id) > 0;
    }
}