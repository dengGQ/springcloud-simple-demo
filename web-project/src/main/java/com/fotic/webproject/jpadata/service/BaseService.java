package com.fotic.webproject.jpadata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fotic.webproject.jpadata.domain.BaseEntity;

/**
 * 基础service接口各业务类extend此类，可以单表CRUD以及单表的动态条件查询
 * @author dgq
 *
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

	/**
	 * 根据Id查询
	 * @param id
	 * @return
	 */
	Optional<T> findById(Long id);
	
	/**
	 * 动态条件唯一查询。调用者需保证过滤条件唯一
	 * @param t
	 * @return
	 */
	Optional<T> findOne(T t);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> findAll();

	/**
	 * 无条件分页查询
	 * @param page
	 * @return
	 */
	Page<T> findAll(Pageable page);

	/**
	 * 无条件排序查询
	 * @param sort
	 * @return
	 */
	List<T> findAll(Sort sort);
	
	/**
	 * 动态条件查询
	 * @param t
	 * @return
	 */
	List<T> findAll(T t);
	
	/**
	 * 动态条件+分页 查询
	 * @param t
	 * @param page
	 * @return
	 */
	Page<T> findAll(T t, Pageable page);
	
	/**
	 * 动态条件+排序 查询
	 * @param t
	 * @param sort
	 * @return
	 */
	List<T> findAll(T t, Sort sort);
	
	/**
	 * 单一新增
	 * @param t
	 * @return
	 */
	T insert(T t);
	
	/**
	 * 批量添加
	 * @param ts
	 * @return
	 */
	Iterable<T> insertBatch(Iterable<T> ts);
	
	/**
	 * by id
	 * @param t
	 * @return
	 */
	T update(T t);
	
	/**
	 * 删除持久化实体
	 * @param t
	 */
	void delete(T t);
	
	/**
	 * 根据主键删除
	 * @param id
	 */
	void deleteById(Long id);
	
	/**
	 * 批量删除
	 * @param ts
	 */
	void deleteInBatch(Iterable<T> ts);
	
	/**
	 * 删除所有
	 */
	void deleteAll();
}
