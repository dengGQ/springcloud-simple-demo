package com.fotic.it.support.signature.dao.mapper;

import com.fotic.it.support.signature.dao.entity.SystemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SystemInfo)表数据库访问层对应的Mapper类
 *
 * @author makejava-word2pdf
 * @since 2019-06-04 16:51:36
 */
@Mapper
@Component
public interface SystemInfoMapper {

    /**
     * 通过Id查询全匹配信息
     *@param id 主键
     *@return实例对象
     */
    List<SystemInfo> findAllById(int id);

    /**
    * 查询指定行数据
    *@param offset 查询起始位置
    *@param limit 查询条数
    * */
    List<SystemInfo> queryAllByLimit(@Param("offset") int offset,@Param("limit") int limit);

    /**
    * 通过实体作为筛选条件查询
    * */
    List<SystemInfo> queryAll(SystemInfo ur);

    /**
    * 新增所有列
    * */
    List<SystemInfo> insert(SystemInfo ur);

    /**
    * 通过主键修改数据
    * */
    int update(SystemInfo ur);

    /**
    * 通过主键删除
    * */
    int deleteById(int id);


}