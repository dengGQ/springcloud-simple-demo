package com.fotic.webproject.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Test {
	
	public static List<Map<String, Object>> initData(){
		Map<String,Object> map1 = new HashMap<>();
		map1.put("a_id",1);
		map1.put("in_num",10);
		Map<String,Object> map2 = new HashMap<>();
		map2.put("a_id",3);
		map2.put("in_num",10);
		Map<String,Object> map3 = new HashMap<>();
		map3.put("a_id",4);
		map3.put("in_num",10);
		List<Map<String,Object>> list1 = new ArrayList<>();
		list1.add(map1);
		list1.add(map2);
		list1.add(map3);
		return list1;
	}
	
	public static List<Map<String, Object>> initData2(){

		Map<String,Object> map4 = new HashMap<>();
		map4.put("a_id",1);
		map4.put("out_num",20);
		Map<String,Object> map5 = new HashMap<>();
		map5.put("a_id",2);
		map5.put("out_num",20);
		Map<String,Object> map6 = new HashMap<>();
		map6.put("a_id",3);
		map6.put("out_num",20);
		List<Map<String,Object>> list2 = new ArrayList<>();
		list2.add(map4);
		list2.add(map5);
		list2.add(map6);
		
		return list2;
	}
	
	public static void main(String args[]) {
		List<Map<String, Object>> data = initData();
		List<Map<String, Object>> data2 = initData2();
		data.addAll(data2);
		
		
		List<Map<String,Object>> list = data.stream().collect(Collectors.groupingBy(o->{
			Object object = o.get("a_id");
			return object;
		})).entrySet().stream().map(o->{
			return o.getValue().stream().flatMap(m->m.entrySet().stream())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->b));
		}).collect(Collectors.toList());
		
		System.out.print(list);
	}
}
