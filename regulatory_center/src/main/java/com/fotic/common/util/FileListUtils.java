package com.fotic.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileListUtils {

	private String path = "";

	public FileListUtils(String path) {
		this.path = path;
	}

	/**
	 * 获取所有文件列表
	 * 
	 * @return
	 */
	public List<File> getfList() {
		List<File> fileList = new ArrayList<File>();
		File file = new File(path);
		File[] tempList = file.listFiles();
		Arrays.sort(tempList, new FileListUtils.FileListToArray());  
		for (int i = 0; i < tempList.length; i++) {
			fileList.add(tempList[i]);
		}
		return fileList;
	}

	/**
	 * 获取文件夹列表
	 * 
	 * @return
	 */
	public List<File> getdList() {
		List<File> fileList = new ArrayList<File>();
		File file = new File(path);
		File[] tempList = file.listFiles();
		Arrays.sort(tempList, new FileListUtils.FileListToArray());  
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				fileList.add(tempList[i]);
			}
		}
		return fileList;
	}
	
	
	/**
	 * 进行文件排序时间
	 * 
	 * @author 谈情
	 */
	public static class FileListToArray implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f2.lastModified() - f1.lastModified();
			if (diff > 0)
				return 1;
			else if (diff == 0)
				return 0;
			else
				return -1;
		}
	}
}
