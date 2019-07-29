package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SignSystemRelation;
import java.util.List;

/**
 * (SignSystemRelation)表服务接口
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 16:58:46
 */
public interface SignSystemRelationService {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    List<SignSystemRelation> queryById(Integer sid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SignSystemRelation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param signSystemRelation 实例对象
     * @return 实例对象
     */
    List<SignSystemRelation> insert(SignSystemRelation signSystemRelation);

    /**
     * 修改数据
     *
     * @param signSystemRelation 实例对象
     * @return 实例对象
     */
    List<SignSystemRelation> update(SignSystemRelation signSystemRelation);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer sid);

}