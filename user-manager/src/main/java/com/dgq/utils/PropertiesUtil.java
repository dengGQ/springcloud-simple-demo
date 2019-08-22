package com.dgq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * properties文件工具类
 * @author zhaoqh
 */
public class PropertiesUtil {
	private static Properties properties = new Properties();
	
	static{
		//加载配置文件
		loadConfig("/conf/roleAuth.properties");
		loadConfig("/conf/dataCenterFilePath.properties");
	}
	
	/**
	 * 加载配置文件
	 * @param fileName
	 */
	private static void loadConfig(String fileName) {
		InputStream in =  PropertiesUtil.class.getResourceAsStream(fileName);
		try {
			properties.load(new InputStreamReader(in, "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据key获取配置文件value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return properties.getProperty(key);
	}
	
	
}
