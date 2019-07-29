package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SignatureLogInfo;
import java.util.List;

/**
 * (SignatureLogInfo)表服务接口
 *
 * @author makejava-word2pdf
 * @since 2019-06-17 10:35:01
 */
public interface SignatureLogInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<SignatureLogInfo> queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignatureLogInfo> queryAllByLimit(int offset, int limit);

    /**
     * 获取max ID
     */
    String queryMax();

    /**
     * 新增数据
     *
     * @param signatureLogInfo 实例对象
     * @return 实例对象
     */
    List<SignatureLogInfo> insert(SignatureLogInfo signatureLogInfo);

    /**
     * 修改数据
     *
     * @param signatureLogInfo 实例对象
     * @return 实例对象
     */
    List<SignatureLogInfo> update(SignatureLogInfo signatureLogInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(int id);

}