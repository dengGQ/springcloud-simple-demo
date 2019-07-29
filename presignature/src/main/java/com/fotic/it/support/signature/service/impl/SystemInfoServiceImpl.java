package com.fotic.it.support.signature.service.impl;

import com.fotic.it.support.signature.dao.entity.SystemInfo;
import com.fotic.it.support.signature.dao.mapper.SystemInfoMapper;
import com.fotic.it.support.signature.service.SystemInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SystemInfo)表服务实现类
 *
 * @author makejava-ElectronicSignature
 * @since 2019-06-04 16:51:36
 */
@Service("systemInfoService")
public class SystemInfoServiceImpl implements SystemInfoService {
    @Resource
    private SystemInfoMapper systemInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<SystemInfo> queryById(Integer id) {
        return systemInfoMapper.findAllById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SystemInfo> queryAllByLimit(int offset, int limit) {
        return systemInfoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param systemInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SystemInfo> insert(SystemInfo systemInfo) {
        systemInfoMapper.insert(systemInfo);
        return this.queryById(systemInfo.getId());
    }

    /**
     * 修改数据
     *
     * @param systemInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<SystemInfo> update(SystemInfo systemInfo) {
        systemInfoMapper.update(systemInfo);
        return this.queryById(systemInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return systemInfoMapper.deleteById(id) > 0;
    }
}