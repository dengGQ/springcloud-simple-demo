package com.fotic.it.support.signature.dao.mapper;

import com.fotic.it.support.signature.dao.entity.ElectronicsignatureInfoLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 重构后电子签章日志(ElectronicsignatureInfoLog)表数据库访问层对应的Mapper类
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-04 15:55:13
 */
@Mapper
@Component
public interface ElectronicsignatureInfoLogMapper {

    /**
     * 通过Id查询全匹配信息
     *@param id 主键
     *@return实例对象
     */
    List<ElectronicsignatureInfoLog> findAllById(String id);

    /**
     * 通过实体作为筛选条件查询
     * */
    List<ElectronicsignatureInfoLog> queryAll(ElectronicsignatureInfoLog ur);

    /**
    * 查询指定行数据
    *@param offset 查询起始位置
    *@param limit 查询条数
    * */
    List<ElectronicsignatureInfoLog> queryAllByLimit(@Param("offset") int offset,@Param("limit") int limit);

    /**
    * 新增所有列
    * */
    int insert(ElectronicsignatureInfoLog ur);

    /**
    * 通过主键修改数据
    * */
    int update(ElectronicsignatureInfoLog ur);

    /**
    * 通过主键删除
    * */
    int deleteById(String id);


}