package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SignatureConfiguration;
import java.util.List;

/**
 * (SignatureConfiguration)表服务接口
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:09:32
 */
public interface SignatureConfigurationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<SignatureConfiguration> queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignatureConfiguration> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param signatureConfiguration 实例对象
     * @return 实例对象
     */
    List<SignatureConfiguration> insert(SignatureConfiguration signatureConfiguration);

    /**
     * 修改数据
     *
     * @param signatureConfiguration 实例对象
     * @return 实例对象
     */
    List<SignatureConfiguration> update(SignatureConfiguration signatureConfiguration);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}