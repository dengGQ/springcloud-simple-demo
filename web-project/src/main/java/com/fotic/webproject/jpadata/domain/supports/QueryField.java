package com.fotic.webproject.jpadata.domain.supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用在参与查询的字段上，指定查询类型QueryType
 * @author dgq
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {
	QueryType type();
	
	String name() default "";
}
