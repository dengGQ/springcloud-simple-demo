package com.fotic.webproject.jpadata.repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.alibaba.druid.util.StringUtils;
import com.fotic.webproject.jpadata.domain.BaseEntity;
import com.fotic.webproject.jpadata.domain.supports.QueryBetween;
import com.fotic.webproject.jpadata.domain.supports.QueryField;
import com.fotic.webproject.jpadata.domain.supports.QueryType;

/**
 * JpaRepository的默认实现
 * @see BaseRepositoryFactoryBean
 * 该类为所有repository提供基本的动态查询能力
 * @author dgq
 *
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings("unused")
public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID>, JpaSpecificationExecutor<T>{
	
	private final EntityManager entityManager;
	private final Class<T> clazz;
	private final BaseRepositoryImpl<T, ID> exmple = this;
	
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
		this.clazz = domainClass;
	}

	@Override
	public Optional<T> findOne(T t) {
		return this.findOne(new Specification<T>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return exmple.getPredocate(root, query, criteriaBuilder, t);
			}
		});
	}
	
	@Override
	public List<T> queryAll(T t) {
		return this.findAll(new Specification<T>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return exmple.getPredocate(root, query, criteriaBuilder, t);
			}
		});
	}

	@Override
	public Page<T> queryAll(T t, Pageable page) {
		return this.findAll(new Specification<T>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return exmple.getPredocate(root, query, criteriaBuilder, t);
			}
		}, page);
	}

	@Override
	public List<T> queryAll(T t, Sort sort) {
		
		return this.findAll(new Specification<T>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return exmple.getPredocate(root, query, criteriaBuilder, t);
			}
		}, sort);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Predicate getPredocate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, BaseEntity dqo) {
		List<Predicate> predicates = new ArrayList<>();
		//获取查询对象的所有属性
		Field[] fields = dqo.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			if(field.getName().equals("serialVersionUID")) continue;
			String queryFiled = null;
			QueryType queryType = null;
			Object value = null;
			Predicate predicate = null;
			try {
				value = field.get(dqo);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			//获取属性的 自定义注解类型
			QueryField annotaion = field.getAnnotation(QueryField.class);
			//如果没有注解
			if(annotaion == null) {
				//执行equal查询
				queryFiled = field.getName();
				queryType = QueryType.EQUAL; //默认是equal查询
			}else {
				//如果注解中 name为空 则用字段名称作为属性名
				queryFiled = StringUtils.isEmpty(annotaion.name()) ? field.getName() : annotaion.name();
				queryType = annotaion.type();
			}
			
			//如果 值==null 而queryType不允许为空 跳出
			if(value == null && queryType.isNotCanBeNull()) {
				continue;
			}
			
			//判断注解中 的条件类型
			switch (queryType) {
				case EQUAL:
					Path<Object> equal = getRootByQueryFiled(queryFiled, root);
					predicate = cb.equal(equal, value);
					predicates.add(predicate);
					break;
				case BEWTEEN:
					Path<Comparable> between = getRootByQueryFiledComparable(queryFiled, root);
					QueryBetween queryBetween = null;
					if(value instanceof QueryBetween) 
						queryBetween = (QueryBetween) value;
					else 
						continue;
					predicate = cb.between(between, queryBetween.after, queryBetween.before);
					predicates.add(predicate);
					break;
				case LESS_THAN:
					Path<Comparable> lessThan = getRootByQueryFiledComparable(queryFiled, root);
					if(value instanceof QueryBetween) 
						queryBetween = (QueryBetween) value;
					else 
						continue;
					predicate = cb.lessThan(lessThan, queryBetween.after);
					predicates.add(predicate);
					break;
				case LESS_THAN_EQUAL:
					Path<Comparable> lessThanOrEqualTo = getRootByQueryFiledComparable(queryFiled, root);
					if(value instanceof QueryBetween) 
						queryBetween = (QueryBetween) value;
					else 
						continue;
					predicate = cb.lessThanOrEqualTo(lessThanOrEqualTo, queryBetween.after);
					predicates.add(predicate);
					break;
				case GREATEROR_THAN:
					Path<Comparable> greaterThan = getRootByQueryFiledComparable(queryFiled, root);
					if(value instanceof QueryBetween) 
						queryBetween = (QueryBetween) value;
					else 
						continue;
					predicate = cb.greaterThan(greaterThan, queryBetween.after);
					predicates.add(predicate);
					break;
				case GREATEROR_THAN_EQUAL:
					Path<Comparable> greaterThanOrEqualTo = getRootByQueryFiledComparable(queryFiled, root);
					if(value instanceof QueryBetween) 
						queryBetween = (QueryBetween) value;
					else 
						continue;
					predicate = cb.lessThanOrEqualTo(greaterThanOrEqualTo, queryBetween.after);
					predicates.add(predicate);
					break;
				case NOT_EQUAL:
					Path<Object> notEqual = getRootByQueryFiled(queryFiled, root);
					predicate = cb.notEqual(notEqual, value);
					predicates.add(predicate);
					break;
				case IS_NULL:
					Path<Object> isNull = getRootByQueryFiled(queryFiled, root);
					predicate = cb.isNull(isNull);
					predicates.add(predicate);
					break;
				case IS_NOT_NULL:
					Path<Object> isNotNull = getRootByQueryFiled(queryFiled, root);
					predicate = cb.isNotNull(isNotNull);
					predicates.add(predicate);
					break;
				case LEFT_LIKE:
					Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
					predicate = cb.like(leftLike, "%" + value.toString());
					predicates.add(predicate);
					break;
				case RIGHT_LIKE:
					Path<String> rightLike = getRootByQueryFiledString(queryFiled, root);
					predicate = cb.like(rightLike, value.toString() + "%");
					predicates.add(predicate);
					break;
				case FULL_LIKE:
					Path<String> fullLike = getRootByQueryFiledString(queryFiled, root);
					predicate = cb.like(fullLike, "%" + value.toString() + "%");
					predicates.add(predicate);
					break;
				case DEFAULT_LIKE:
					Path<String> like = getRootByQueryFiledString(queryFiled, root);
					predicate = cb.like(like, value.toString());
					predicates.add(predicate);
					break;
				case NOT_LIKE:
					Path<String> notLike = getRootByQueryFiledString(queryFiled, root);
					predicate = cb.like(notLike, value.toString());
					predicates.add(predicate);
					break;
				case IN:
					Path<Object> in = getRootByQueryFiled(queryFiled, root);
					In ins = cb.in(in);
					List inList = null;
					if(value instanceof List) {
						inList = (List) value;
					}
					for (Object object : inList) {
						ins.value(object);
					}
					predicates.add(ins);
					break;
				default:
					break;
			}
		}
		//如果 为空 代表  没有任何有效的条件
		if(predicates.size() == 0) {
			return cb.and();
		}
		@SuppressWarnings("unused")
		Object[] list = predicates.toArray();
		Predicate[] t = new Predicate[predicates.size()];
		Predicate[] result = predicates.toArray(t);
		return cb.and(result);
	}
	
	private Path<Object> getRootByQueryFiled(String queryFiled, Root<T> root) {
		if(queryFiled.indexOf(".") < 0) {
			return root.get(queryFiled);
		} else {
			return getRootByQueryFiled(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}

	private Path<Object> getRootByQueryFiled(String queryFiled, Path<Object> path) {
		if(queryFiled.indexOf(".") < 0) {
			return path.get(queryFiled);
		} else {
			return getRootByQueryFiled(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}
	
	private Path<String> getRootByQueryFiledString(String queryFiled, Root<T> root) {
		if(queryFiled.indexOf(".") < 0) {
			return root.get(queryFiled);
		} else {
			return getRootByQueryFiledString(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}

	private Path<String> getRootByQueryFiledString(String queryFiled, Path<Object> path) {
		if(queryFiled.indexOf(".") < 0) {
			return path.get(queryFiled);
		} else {
			return getRootByQueryFiledString(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Path<Comparable> getRootByQueryFiledComparable(String queryFiled, Root<T> root) {
		if(queryFiled.indexOf(".") < 0) {
			return root.get(queryFiled);
		} else {
			return getRootByQueryFiledComparable(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}

	@SuppressWarnings("rawtypes")
	private Path<Comparable> getRootByQueryFiledComparable(String queryFiled, Path<Object> path) {
		if(queryFiled.indexOf(".") < 0) {
			return path.get(queryFiled);
		} else {
			return getRootByQueryFiledComparable(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
		}
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> createSQLQuery(String sql, Class<E> resultClass) {
		return entityManager.createNativeQuery(sql, resultClass).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> createSQLQuery(String sql,  Class<E> resultClass, Object... params){
		Map<String, Object> map = getSql(sql);
		int count = (int) map.get("count");  
		Query query = entityManager.createNativeQuery((String) map.get("sql"), resultClass);
		setQueryParameters(count, query, params);
		return query.getResultList();
	}
	
	@Override
	public <E> List<E> createHQLQuery(String hql, Class<E> resultClass) {
		return entityManager.createQuery(hql, resultClass).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> createHQLQuery(String hql, Class<E> resultClass, Object... params){
		Map<String, Object> map = getHSql(hql);
		int count = (int) map.get("count");  
		Query query = entityManager.createQuery((String) map.get("sql"), resultClass);
		setQueryParameters(count, query, params);
		return query.getResultList();
	}
	
	private Map<String, Object> getSql(String sql) {
        sql += " ";
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("sql", sql);
        map.put("count", count);
        String[] datas = sql.split("\\?");
        int length = datas.length;
        if (length == 1)
            return map;
        for (int i = 0; i < length - 1; i++) {
            count++;
        }
        map.put("count", count);
        map.put("sql", sql);
        return map;
    }
	
	private Map<String, Object> getHSql(String sql) {
        sql += " ";
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("sql", sql);
        map.put("count", count);
        String[] datas = sql.split("\\?");
        int length = datas.length;
        if (length == 1)
            return map;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            count++;
            sb.append(datas[i]).append("?" + count).append(" ");
        }
        sb.append(datas[length-1]);
        map.put("count", count);
        map.put("sql", sb.toString());
        return map;
    }
	
	*//**
     * 为Query设置参数
     * @param count
     * @param query
     * @param params
     *//*
    private void setQueryParameters(Integer count, Query query, Object[] params) {
        if (params != null) {
            if (params.length != count) {
                throw new RuntimeException("sql 语句和参数数量不一致");
            }
            if (params.length > 0) {
                int length = params.length;
                for (int i = 0; i < length; i++) {
                    query.setParameter(i + 1, params[i]);
                }
            }
        }
        
    }*/
}
