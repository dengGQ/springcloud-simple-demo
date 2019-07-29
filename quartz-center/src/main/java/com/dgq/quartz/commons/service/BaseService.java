package com.dgq.quartz.commons.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用service
 *
 * @param <T>
 * @author dgq
 */
public interface BaseService<T> {
	
	/**
	 * 根据主键查询
	 * @param key
	 * @return
	 */
	T get(Object key);
	
    /**
     * 新增，忽略为空字段
     *
     * @param t
     * @return
     * @
     */
    T insetEntitySelective(T t) ;

    /**
     * 新增，有为空字段抛出异常
     *
     * @param t
     * @return
     * @
     */
    T insetEntity(T t) ;

    /**
     * 批量添加
     *
     * @return
     * @
     */
    List<T> batchInsertSelective(List<T> ts) ;

    /**
     * 参数【t】的所有有值且有@QueryType注解的字段都将作为过滤条件,
     * 过滤条件需保证查询结果数量不大于1
     *
     * @param t
     * @return
     * @
     */
    T queryEntity(T t) ;

    /**
     * 参数【t】的所有有值且有@QueryType注解的字段都将作为过滤条件
     *
     * @param t
     * @return
     * @
     */
    List<T> queryEntityList(T t) ;

    /**
     * 参数【t】的所有有值且有@QueryType注解的字段都将作为过滤条件
     *
     * @param t
     * @param orderby 排序
     * @return
     * @
     */
    List<T> queryEntityList(T t, String orderby) ;

    /**
     * <p>分页查询</p>
     * 参数【t】的所有有值且有@QueryType注解的字段都将作为过滤条件
     *
     * @param t
     * @param pageNum
     * @param pageSize
     * @return
     * @
     */
    PageInfo<T> queryEntityListForPage(T t, int pageNum, int pageSize) ;

    /**
     * <p>分页查询</p>
     * 参数【t】的所有有值且有@QueryType注解的字段都将作为过滤条件
     *
     * @param t
     * @param pageNum
     * @param pageSize
     * @param orderby
     * @return
     * @
     */
    PageInfo<T> queryEntityListForPage(T t, int pageNum, int pageSize, String orderby) ;

    /**
     * 根据主键修改实体，修改为空字段
     *
     * @param t
     * @return
     * @
     */
    T updateEntityByPrimaryKey(T t) ;

    /**
     * 根据主键修改实体，忽略为空字段
     *
     * @param t
     * @return
     * @
     */
    T updateEntityByPrimaryKeySelective(T t) ;

    /**
     * 批量修改
     *
     * @param ts
     * @
     */
    void batchUpdateSelective(List<T> ts) ;

    /**
     * 根据主键删除(逻辑删除)
     *
     * @param t
     * @
     */
    void softDelEntityByKey(String tabName, Object id) ;


    /**
     * 根据主键删除(物理删除)
     *
     * @param id
     * @
     */
    void delEntityByKey(Object id) ;

    /**
     * 批量删除
     *
     * @param ts
     * @
     */
    void batchDelEntity(List<T> ts) ;
    
    /**
     * 根据主键查询
     * @param id
     */
    void checkExistsDataPrimaryKey(Long id);
}
