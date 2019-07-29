package com.fotic.management.sms.vo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
* @Description: easyUI-Tress node
* @author dgq 
* @date 2018年3月23日
*/
public class EasyUITreeNodeUtil {
	
	public static class EasyUITreeNode{
		private Object id;
		private Object text;
		private Integer attributes;
		private String state;
		
		public EasyUITreeNode(){}
		
		public EasyUITreeNode(Object id, Object text, Integer attributes, String state) {
			super();
			this.id = id;
			this.text = text;
			this.attributes = attributes;
			this.state = state;
		}
		public Object getId() {
			return id;
		}
		public void setId(Object id) {
			this.id = id;
		}
		public Object getText() {
			return text;
		}
		public void setText(Object text) {
			this.text = text;
		}
		public Integer getAttributes() {
			return attributes;
		}
		public void setAttributes(Integer attributes) {
			this.attributes = attributes;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
	
	/**
	 * 
	 * @param datas
	 * @param c
	 * @param fileds 最多不超过两个,0:id 1: text
	 * @return
	 * @throws Exception 
	 */
	public static List<EasyUITreeNode> convertTreeNode(List<?> datas, String idField, String textField, String levelField, String state) throws Exception{
		
		ArrayList<EasyUITreeNode> nodes = new ArrayList<>();
		for (Object t : datas) {
			EasyUITreeNode node = new EasyUITreeNode();
				
			Object id = reflectCallMethod(idField, t);
			Object text = reflectCallMethod(textField, t);
			Integer level = (Integer)reflectCallMethod(levelField, t);
			node.setId(id);
			node.setText(text);
			node.setAttributes(level);
			node.setState(state);
			
			nodes.add(node);
		}
		return nodes;
	}
	
	public static List<EasyUITreeNode> convertTreeNode(String idField, String textField, Integer levelField, String state){
		EasyUITreeNode node = new EasyUITreeNode();
		node.setId(idField);
		node.setText(textField);
		node.setAttributes(levelField);
		node.setState(state);
		ArrayList<EasyUITreeNode> nodes = new ArrayList<>();
		nodes.add(node);
		
		return nodes;
	}
	
	public static Object reflectCallMethod(String filedName, Object o) throws Exception{
		Method method = o.getClass().getMethod("get"+filedName.substring(0, 1).toUpperCase().concat(filedName.substring(1)));
		Object value = method.invoke(o);
		return value;
	}
}
