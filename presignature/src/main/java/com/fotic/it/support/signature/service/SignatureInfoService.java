package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SignatureInfo;
import java.util.List;

/**
 * (SignatureInfo)表服务接口
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 17:05:06
 */
public interface SignatureInfoService {

    /**
     * 通过signid查询单条数据
     *
     * @param signid 主键
     * @return 实例对象
     */
    List<SignatureInfo> queryBySignId(Integer signid);

    /**
     * 通过id查询单条数据
     * @param id
     * @return
     */
    List<SignatureInfo> queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignatureInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param signatureInfo 实例对象
     * @return 实例对象
     */
    List<SignatureInfo> insert(SignatureInfo signatureInfo);

    /**
     * 修改数据
     *
     * @param signatureInfo 实例对象
     * @return 实例对象
     */
    List<SignatureInfo> update(SignatureInfo signatureInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}