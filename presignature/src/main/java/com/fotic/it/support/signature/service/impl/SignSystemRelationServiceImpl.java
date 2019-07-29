package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.dao.entity.SignSystemRelation;
import com.fotic.it.support.signature.dao.mapper.SignSystemRelationMapper;
import com.fotic.it.support.signature.service.SignSystemRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SignSystemRelation)表服务实现类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 16:58:46
 */
@Service("signSystemRelationService")
public class SignSystemRelationServiceImpl implements SignSystemRelationService {
    @Resource
    private SignSystemRelationMapper signSystemRelationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    @Override
    public List<SignSystemRelation> queryById(Integer sid) {
        return signSystemRelationMapper.findAllById(sid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SignSystemRelation> queryAllByLimit(int offset, int limit) {
        return signSystemRelationMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param signSystemRelation 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignSystemRelation> insert(SignSystemRelation signSystemRelation) {
        signSystemRelationMapper.insert(signSystemRelation);
        return this.queryById(signSystemRelation.getSid());
    }

    /**
     * 修改数据
     *
     * @param signSystemRelation 实例对象
     * @return 实例对象
     */
    @Override
    public List<SignSystemRelation> update(SignSystemRelation signSystemRelation) {
        signSystemRelationMapper.update(signSystemRelation);
        return this.queryById(signSystemRelation.getSid());
    }

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer sid) {
        return signSystemRelationMapper.deleteById(sid) > 0;
    }
}