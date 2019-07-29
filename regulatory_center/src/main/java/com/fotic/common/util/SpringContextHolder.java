package com.fotic.common.util;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
* @Description: public class SpringContextHolder{ }
* @author dgq 
* @date 2018年4月25日
*/
public class SpringContextHolder implements ApplicationContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);
	
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		assertApplicationContext();
		return (T)applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> beanType){
		assertApplicationContext();
		return (T)applicationContext.getBean(beanType);
	}
	
	public static void assertApplicationContext(){
		 Validate.validState(applicationContext != null,  
	                "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}
}
