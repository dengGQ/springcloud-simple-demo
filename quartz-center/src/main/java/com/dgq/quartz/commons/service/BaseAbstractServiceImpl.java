package com.dgq.quartz.commons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dgq.quartz.commons.Exception.BusinessException;
import com.dgq.quartz.commons.mapper.CommonMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 通用抽象service
 *
 * @param <T>
 * @param <D>
 * @author dgq
 */
public abstract class BaseAbstractServiceImpl<T, D extends CommonMapper<T>> implements BaseService<T> {

    @Autowired
    private D mapper;
    
    @Override
    public T get(Object key) {
    	return mapper.selectByPrimaryKey(key);
    }
    
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public T insetEntity(T t)  {
        try {
            mapper.insert(t);
            return t;
        } catch (Exception e) {
            throw new BusinessException("新增异常！" + t.toString(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public T insetEntitySelective(T t)  {
        try {
            mapper.insertSelective(t);
            return t;
        } catch (Exception e) {
            throw new BusinessException("新增异常！" + t.toString(), e);
        }
    }

    @Override
    public List<T> batchInsertSelective(List<T> ts)  {
        ts.forEach(t -> {
            mapper.insertSelective(t);
        });
        return ts;
    }

    @Override
    public T queryEntity(T t)  {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> queryEntityList(T t)  {
        try {
            return mapper.queryEntityList(t);
        } catch (Exception e) {
            throw new BusinessException("查询异常！" + t.toString(), e);
        }
    }

    @Override
    public List<T> queryEntityList(T t, String orderby)  {
        try {
            return mapper.queryEntityList(t, orderby);
        } catch (Exception e) {
            throw new BusinessException("查询异常！" + t.toString(), e);
        }
    }

    @Override
    public PageInfo<T> queryEntityListForPage(T t, int pageNum, int pageSize)  {

        try {
            return this.queryEntityListForPage(t, pageNum, pageSize, null);
        } catch (Exception e) {
            throw new BusinessException("分页查询异常！" + t.toString(), e);
        }
    }

    @Override
    public PageInfo<T> queryEntityListForPage(T t, int pageNum, int pageSize, String orderby)  {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<T> list = mapper.queryEntityList(t, orderby);
            return new PageInfo<>(list);
        } catch (Exception e) {
            throw new BusinessException("分页查询异常！" + t.toString(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public T updateEntityByPrimaryKey(T t)  {
        try {
            mapper.updateByPrimaryKey(t);
        } catch (Exception e) {
            throw new BusinessException("修改失败！" + t.toString(), e);
        }
        return t;
    }

    @Override
    public T updateEntityByPrimaryKeySelective(T t)  {
        try {
            mapper.updateByPrimaryKeySelective(t);
        } catch (Exception e) {
            throw new BusinessException("修改失败！" + t.toString(), e);
        }
        return t;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void softDelEntityByKey(String tabName, Object id)  {
        try {
            mapper.softDelEntityByKey(tabName, id);
        } catch (Exception e) {
            throw new BusinessException("逻辑删除失败！tabName=" + tabName + "&id=" + id, e);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void batchUpdateSelective(List<T> ts)  {
        try {
            ts.forEach(t -> {
                mapper.updateByPrimaryKeySelective(t);
            });
        } catch (Exception e) {
            throw new BusinessException("批量修改异常！" + ts, e);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void delEntityByKey(Object id)  {
        try {
            mapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new BusinessException("根据主键删除异常!id=" + id, e);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void batchDelEntity(List<T> ts)  {
        try {
            ts.parallelStream().forEach(o -> {
                mapper.delete(o);
            });
        } catch (Exception e) {
            throw new BusinessException("批量删除失败!:" + ts, e);
        }

    }
    
    @Override
    public void checkExistsDataPrimaryKey(Long id) {
        T t = mapper.selectByPrimaryKey(id);
        if (t == null) {
            throw new IllegalArgumentException("参数错误！");
        }
    }

}


