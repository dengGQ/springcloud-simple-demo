package com.dgq.crs.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import com.dgq.crs.entity.DataEditLog;

/*
* @Description: public class CommonProvider{ }
* @author dgq 
* @date 2019年3月28日
*/
public class CommonProvider {
	
	public static String dynamicSQL(Map<String, Object> params) {
		try {
			@SuppressWarnings("unchecked")
			List<Object> objects = (List<Object>) params.get("list");
			
			Class<? extends Object> clazz = objects.get(0).getClass();
			
			Annotation annotype = clazz.getDeclaredAnnotation(Table.class);
			
			Method method = annotype.annotationType().getDeclaredMethod("name");
			
			if(!method.isAccessible()){  
				method.setAccessible(true);  
			}
	    	String tabName = method.invoke(annotype).toString();
	    	StringBuffer insertField = new StringBuffer("insert into "+tabName+"(");
			
			
			Field fieldOfId = clazz.getDeclaredField("id");
			if(Objects.nonNull(fieldOfId)) {
				insertField.append("id,");
			}
			
			StringBuffer insertValue = new StringBuffer("select ");
			Field[] fields = clazz.getDeclaredFields();
			Arrays.asList(fields).parallelStream().forEach(field->{
				if(!field.isAccessible()){  
					field.setAccessible(true);
                }
				String fieldName = field.getName();
				Class<?> class1 = field.getType();
				String name = class1.getName();
				try {
					if(!fieldName.equals("id")) {
						insertField.append(humpToUnderline(fieldName)+",");
						insertValue.append("#'{'list[{0}]."+fieldName+", javaType="+name+",jdbcType="+javaTypeToJdbcType(name)+"'}' as "+fieldName+",");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			if(Objects.nonNull(fieldOfId)) {
				GeneratedValue generatedValue = fieldOfId.getDeclaredAnnotation(GeneratedValue.class);
				String generator = generatedValue.getClass()
										.getDeclaredMethod("generator")
										.invoke(generatedValue).toString();
				
				insertField.replace(insertField.toString().length()-1, insertField.toString().length(), ") ("+generator.split("from")[0]+", cd.* from (");
			}else {
				insertField.replace(insertField.toString().length()-1, insertField.toString().length(), ") (select cd.* from (");
			}
			MessageFormat mf = new MessageFormat(insertValue.toString().substring(0, insertValue.toString().length()-1)+" from dual");
			for (int i = 0; i < objects.size(); i++) {
				insertField.append(mf.format(new Object[]{i}));
	            if(i < objects.size()-1){
	            	insertField.append(" union all ");
	            }
			}
			
			String sql = insertField.append(") cd)").toString();
			System.out.println(sql);
			return sql;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 驼峰转下划线
	 * @param para
	 * @return
	 */
	private static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        for(int i=0;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toUpperCase(); 
	}
	
	/**
	 * 目前仅支持Long/Integer/Date/Timestamp/String这五种类型
	 * @param javaType
	 * @return
	 */
	private static String javaTypeToJdbcType(String javaType) {
		switch(javaType) {
			case "long":
			case "java.lang.Long":
				return "LONGVARCHAR";
			case "int":
			case "java.lang.Integer":
				return "INTEGER";
			case "java.util.Date":
				return "DATE";
			case "java.sql.Timestamp":
				return "TIMESTAMP";
			default:
				return "VARCHAR";
			
		}
	}
	
	public static void main(String[] args) {
		 List<DataEditLog> list = new ArrayList<>();
		DataEditLog log = new DataEditLog();
		log.setAccountNum("123456");
		log.setEditAfterValue("newValue");
		log.setEditBeforeValue("oldValue");
		log.setEditFiled("dgq");
		log.setOperatorname("习近平");
		log.setUserid("1");
//		log.setUpdateDate(new Timestamp(new Date().getTime()));
		
		DataEditLog log1 = new DataEditLog();
		log1.setAccountNum("1234567");
		log1.setEditAfterValue("newValue1");
		log1.setEditBeforeValue("oldValue1");
		log1.setEditFiled("dgq1");
		log1.setOperatorname("习近平1");
		log1.setUserid("11");
		log1.setUpdateDate(new Timestamp(new Date().getTime()));
		
		DataEditLog log2 = new DataEditLog();
		log2.setAccountNum("12345678");
		log2.setEditAfterValue("newValue2");
		log2.setEditBeforeValue("oldValue2");
		log2.setEditFiled("dgq2");
		log2.setOperatorname("习近平2");
		log2.setUserid("12");
		log2.setUpdateDate(new Timestamp(new Date().getTime()));
		
		list.add(log);
		list.add(log1);
		list.add(log2);
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		//for(int i = 0; i < 1000; i++) {
			dynamicSQL(map);
		//}
	}
}
