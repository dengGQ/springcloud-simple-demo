package com.dgq.quartz.commons.mapper;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Table;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dgq.quartz.commons.annotation.QueryType;
import com.dgq.quartz.commons.annotation.QueryTypeEnum;
import com.dgq.quartz.commons.utils.AnnotationUtils;
import com.dgq.quartz.commons.utils.StringUtils;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CommonMapper<T> extends Mapper<T>, InsertListMapper<T>{
	
	
	@Select("select * from ${tabNames} where ${condition} order by ${orderby}")
	List<T> queryEntityByConditionWithOrder(@Param("tabNames")String tabNames, @Param("condition")String condition, @Param("orderby")String orderby);
	
	@Select("select * from ${tabNames} where ${condition}")
	List<T> queryEntityByCondition(@Param("tabNames")String tabNames, @Param("condition")String condition);
	
	@Delete("delete from ${tabName} where ${fieldName} in (${ids})")
	void batchDelInIds(@Param("tabName")String tabName, @Param("fieldName")String fieldName, @Param("ids")String ids);
	/**
	 * 通用条件查询方法
	 * @param t
	 * @return
	 */
	default List<T> queryEntityList(T t) throws Exception{
		return this.queryEntityList(t, null);
	}
	
	/**
	 * 通用条件查询方法, 指定排序字段
	 * @param t
	 * @param orderby
	 * @return
	 */
	default List<T> queryEntityList(T t, String orderby) throws Exception{
		String tabName = String.valueOf(AnnotationUtils.getAnnotationValueByAnnoAndMethodName(t.getClass().getDeclaredAnnotation(Table.class), "name"));
		String condition = buildQueryConditionByEntityField(t, null);
		if(Objects.isNull(orderby)) {
			return this.queryEntityByCondition(tabName, condition);
		}else {
			return this.queryEntityByConditionWithOrder(tabName, condition, orderby);
		}
	}
	
	/**
	 * 通过对象字段构建通用查询条件
	 * 需要参与查询的字段加上@QueryType注解，值是枚举(QueryTypeEnum)，比较方式：= or like
	 * @param t
	 * @param tabAliases 
	 * @return
	 */
	default String buildQueryConditionByEntityField(T t, String tabAliases) {
		
		StringBuilder condition = new StringBuilder("1=1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
		
		Class<? extends Object> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Arrays.asList(fields).forEach(field->{
			try {
				if(!field.isAccessible()){  
					field.setAccessible(true);
                }
				//字段上如果有QueryType才会被作为查询项拼接到查询条件中
				if(Objects.nonNull(field.getAnnotation(QueryType.class))) {
					//取值
					String getMethod = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
					Object v = t.getClass().getDeclaredMethod(getMethod).invoke(t);
					
					//取当前字段查询符号
					QueryTypeEnum querytype = (QueryTypeEnum)AnnotationUtils.getAnnotationValueByAnnoAndMethodName(field.getAnnotation(QueryType.class), "value");
					
					//值为null|""不参与查询
					if(!StringUtils.isEmpty(v)) {
						//驼峰转换下划线
						String columnName = StringUtils.HumpToUnderline(field.getName());
						condition.append(" AND ");
						if(Objects.nonNull(tabAliases)) {
							condition.append(tabAliases).append(".");
						}
						if("like".equals(querytype.getValue().trim())) {
							condition.append(columnName).append(querytype.getValue()).append("'%").append(v).append("%'");
						}else { //=
							if(field.getType().equals(java.util.Date.class)) {
								v = sdf.format(v);
								condition.append(columnName).append(querytype.getValue()).append("to_date('").append(v).append("', 'yyyy-MM-dd')");
							}else if(field.getType().equals(Timestamp.class)) {
								v = sdftime.format(v);
								condition.append(columnName).append(querytype.getValue()).append("to_date('").append(v).append("', 'yyyy-MM-dd HH:mm:ss'");
							}else if(field.getType().equals(java.lang.String.class)){
								condition.append(columnName).append(querytype.getValue()).append("'").append(v).append("'");
							}else {
								condition.append(columnName).append(querytype.getValue()).append(v);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return condition.toString();
	}
	
	/**
	 * 逻辑删除
	 * @param tabName
	 * @param id
	 */
	@Update("update ${tabName} set DEL_FLAG = 1 where id = #{id}")
	void softDelEntityByKey(@Param("tabName")String tabName, @Param("id")Object id);
}
