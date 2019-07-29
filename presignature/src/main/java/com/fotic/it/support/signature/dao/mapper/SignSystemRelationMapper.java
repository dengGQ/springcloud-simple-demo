package com.fotic.it.support.signature.dao.mapper;

import com.fotic.it.support.signature.dao.entity.SignSystemRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SignSystemRelation)表数据库访问层对应的Mapper类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 16:58:47
 */
@Mapper
@Component
public interface SignSystemRelationMapper {

    /**
     * 通过Id查询全匹配信息
     *@param sid 主键
     *@return实例对象
     */
    List<SignSystemRelation> findAllById(int sid);

    /**
    * 查询指定行数据
    *@param offset 查询起始位置
    *@param limit 查询条数
    * */
    List<SignSystemRelation> queryAllByLimit(@Param("offset") int offset,@Param("limit") int limit);

    /**
    * 通过实体作为筛选条件查询
    * */
    List<SignSystemRelation> queryAll(SignSystemRelation ur);

    /**
    * 新增所有列
    * */
    int insert(SignSystemRelation ur);

    /**
    * 通过主键修改数据
    * */
    int update(SignSystemRelation ur);

    /**
    * 通过主键删除
    * */
    int deleteById(int sid);


}