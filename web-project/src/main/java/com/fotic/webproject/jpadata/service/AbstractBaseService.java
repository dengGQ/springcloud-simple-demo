package com.fotic.webproject.jpadata.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fotic.webproject.jpadata.domain.BaseEntity;
import com.fotic.webproject.jpadata.repository.BaseRepository;

/**
 * 基础service接口的一个抽象实现类，此类实现一些公用的方法
 * @author dgq
 *
 * @param <T>
 */
@Transactional
public abstract class AbstractBaseService<T extends BaseEntity, R extends BaseRepository<T, Long>> implements BaseService<T>{
	
	@Autowired
	private R r;
	
	@Override
	public Optional<T> findById(Long id) {
		return r.findById(id);
	}
	
	@Override
	public Optional<T> findOne(T t) {
		return r.findOne(t); 
	}
	
	@Override
	public List<T> findAll() {
		return r.findAll();
	}
	
	@Override
	public List<T> findAll(T t) {
		return r.queryAll(t);
	}
	
	@Override
	public Page<T> findAll(Pageable page) {
		return r.findAll(page);
	}
	
	@Override
	public Page<T> findAll(T t, Pageable page) {
		return r.queryAll(t, page);
	}
	
	@Override
	public List<T> findAll(Sort sort) {
		return r.findAll(sort);
	}
	
	@Override
	public List<T> findAll(T t, Sort sort) {
		return r.queryAll(t, sort);
	}
	
	@Override
	public T insert(T t) {
		t.setCreateTime(new Date());
		return r.save(t);
	}
	
	@Override
	public Iterable<T> insertBatch(Iterable<T> ts) {
		return r.saveAll(ts);
	}
	
	@Override
	public T update(T t) {
		return r.save(t);
	}
	
	@Override
	public void delete(T t) {
		r.delete(t);
	}
	
	@Override
	public void deleteById(Long id) {
		r.deleteById(id);
	}
	
	@Override
	public void deleteInBatch(Iterable<T> ts) {
		r.deleteAll(ts);
	}
	
	@Override
	public void deleteAll() {
		r.deleteAll();
	}
}
