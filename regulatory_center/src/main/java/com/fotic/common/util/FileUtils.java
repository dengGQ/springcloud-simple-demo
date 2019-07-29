package com.fotic.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
* @Description: public class FileUtils{ }
* @author 邓国泉 
* @date 2017年11月21日
*/
public class FileUtils {
	
	/**
	 * 创建文件
	 * @param destFileName  创建文件全路径
	 * @return
	 */
	public static boolean createFile(String destFileName){
		File file = new File(destFileName);
		 
		try {
			if(!file.exists()){
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 创建带内容的文件
	 * @param destFileName  文件全路径
	 * @param content  内容
	 * @return
	 */
	public static boolean createFile(String destFileName, String content){
		FileWriter fw = null;
		try {
			if(createFile(destFileName)){
				fw = new FileWriter(destFileName);
				fw.write(content);
				return true;
			};
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return false;
	}
}
