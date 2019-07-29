package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.SystemInfo;
import java.util.List;

/**
 * (SystemInfo)表服务接口
 *
 * @author makejava-ElectronicSignature
 * @since 2019-06-04 16:51:36
 */
public interface SystemInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<SystemInfo> queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SystemInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param systemInfo 实例对象
     * @return 实例对象
     */
    List<SystemInfo> insert(SystemInfo systemInfo);

    /**
     * 修改数据
     *
     * @param systemInfo 实例对象
     * @return 实例对象
     */
    List<SystemInfo> update(SystemInfo systemInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}