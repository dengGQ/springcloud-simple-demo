package com.fotic.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
public class Test {
	
	public static void main(String[] args) {  
		
		 
	    List<String> list = new ArrayList();  
	    int size = 1099;  
	    for (int i = 0; i < size; i++) {  
	        list.add("hello-" + i);  
	    }  
	    // 切割大集合到指定的长度：11  
	    List<List<String>> rsList = ListUtils.partition(list, 100);
	    
	    int i = 0;  
	    for (List<String> obj : rsList) {  
	        System.out.println(rsList.get(0));  
	    }  
	}  

}
