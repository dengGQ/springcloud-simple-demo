package com.fotic.webproject.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ListUtils {
	
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
		map5.put("out_num",22);
		Map<String,Object> map6 = new HashMap<>();
		map6.put("a_id",3);
		map6.put("out_num",12);
		map6.put("name",45);
		List<Map<String,Object>> list2 = new ArrayList<>();
		list2.add(map4);
		list2.add(map5);
		list2.add(map6);
		
		return list2;
	}
	
	public static List<Map<String, Object>> merge(List<Map<String, Object>> m1, List<Map<String, Object>> m2){
		
		m1.addAll(m2);
		
		Set<String> set = new HashSet<>();
		
		return m1.stream()
				.collect(Collectors.groupingBy(o->{
					//暂存所有key
					set.addAll(o.keySet());
					//按a_id分组
					return o.get("a_id");
				})).entrySet().stream().map(o->{
					
					//合并
					Map<String, Object> map = o.getValue().stream().flatMap(m->{
						return m.entrySet().stream();
					}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->b));
					
					//为没有的key赋值0
					set.stream().forEach(k->{
						if(!k.equals("a_id") && !map.containsKey(k)) {
							map.put(k, 0);
						}
					});
					
					return map;
				}).collect(Collectors.toList());
	}
	
	public static void main(String args[]) {
		List<Map<String, Object>> data = initData();
		List<Map<String, Object>> data2 = initData2();
		
		List<Map<String,Object>> list = merge(data, data2);
		System.out.println(list);
	}
}
