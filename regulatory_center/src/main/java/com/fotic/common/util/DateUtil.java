package com.fotic.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String yyyy_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static String yyyy_MM_dd = "yyyy-MM-dd";
	
	public static String yyyyMMdd = "yyyyMMdd";
	
	public static String getDateConvertStr(Date date,String format){
		String dateStr = null;
		if(date!=null){
			DateFormat simpleDateFormat = new SimpleDateFormat(format);
			dateStr = simpleDateFormat.format(date);
		}
		return dateStr;
	}
	
	 /** 
     * 格式化String时间 
     * @param time String类型时间 
     * @param timeFromat String类型格式 
     * @return 格式化后的Date日期 
     */  
    public static Date parseStrToDate(String time, String timeFromat) {  
        if (time == null || time.equals("")) {  
            return null;  
        }  
        Date date=null;  
        try{  
            DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
            date=dateFormat.parse(time);  
        }catch(Exception e){  
              
        }  
        return date;  
    }  
	
	public static Integer getStrConvertInt(String str){
		Integer ret = null;
		if(str!=null && !"".equals(str.trim())){
			ret = Integer.valueOf(str);
		}
		return ret;
	}
}
