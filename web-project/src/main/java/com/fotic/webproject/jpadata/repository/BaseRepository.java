package com.fotic.webproject.jpadata.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fotic.webproject.jpadata.domain.BaseEntity;

public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{
	
	Optional<T> findOne(T t);
	
	/**
	 * 动态条件查询所有
	 * @param t
	 * @return
	 */
	List<T> queryAll(T t);
	
	/**
	 * 动态条件-分页
	 * @param t
	 * @param page
	 * @return
	 */
	Page<T> queryAll(T t, Pageable page);
	
	/**
	 * 动态条件-排序
	 * @param t
	 * @param sort
	 * @return
	 */
	List<T> queryAll(T t, Sort sort);
}
