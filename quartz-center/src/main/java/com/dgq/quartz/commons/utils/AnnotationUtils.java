package com.dgq.quartz.commons.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

public class AnnotationUtils {
	
	public static Object getAnnotationValueByAnnoAndMethodName(Annotation annotype, String methodName) 
		throws Exception{
		if(Objects.isNull(annotype))
			return null;
		Method methond = annotype.annotationType().getDeclaredMethod(methodName);  
		if(!methond.isAccessible()){  
			methond.setAccessible(true);  
		}
    	return methond.invoke(annotype);
	}
}
