package com.dgq.quartz.commons.utils;

public class StringUtils extends org.springframework.util.StringUtils{
	
	/**
	 * 驼峰转下划线
	 * @param para
	 * @return
	 */
	public static String HumpToUnderline(String para){
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


}
