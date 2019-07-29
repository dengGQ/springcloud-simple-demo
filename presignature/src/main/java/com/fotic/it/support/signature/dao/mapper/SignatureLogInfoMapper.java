package com.fotic.it.support.signature.dao.mapper;

import com.fotic.it.support.signature.dao.entity.SignatureLogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SignatureLogInfo)表数据库访问层对应的Mapper类
 *
 * @author makejava-word2pdf
 * @since 2019-06-17 10:35:01
 */
@Mapper
@Component
public interface SignatureLogInfoMapper {

    /**
     * 通过Id查询全匹配信息
     *@return实例对象
     * @param id 主键
     */
    List<SignatureLogInfo> findAllById(Long id);

    /**
    * 查询指定行数据
    *@param offset 查询起始位置
    *@param limit 查询条数
    * */
    List<SignatureLogInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
    * 通过实体作为筛选条件查询
    * */
    List<SignatureLogInfo> queryAll(SignatureLogInfo ur);

    /**
     * 获取max ID
     */
    String findMax();

    /**
    * 新增所有列
    * */
    int insert(SignatureLogInfo ur);

    /**
    * 通过主键修改数据
    * */
    int update(SignatureLogInfo ur);

    /**
    * 通过主键删除
    * */
    int deleteById(int id);


}