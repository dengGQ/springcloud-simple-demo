package com.fotic.management.datacheck.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.constant.FileConstant;
import com.fotic.management.datacheck.dao.DataCheckMapper;
import com.fotic.management.datacheck.entity.SelfCheckExprt;
import com.fotic.management.datacheck.service.IDataCheckService;

@Service
public class DataCheckServiceImpl implements IDataCheckService {

	@Autowired
	private DataCheckMapper dataCheckMapper;

	/**
	 * @see IDataCheckService#queryDataCheck(String)
	 * 根据季度查询自检数据信息
	 */
	@Override
	public List<SelfCheckExprt> queryDataCheck(String year) {
		return dataCheckMapper.queryDataCheck(year);
	}

	/**
	 * @see IDataCheckService#exportSelfCheckDataFileBySeq(String)
	 * 根据序号导出文件，需要根据序号从新查询一下文件路径。防止在前端直接获取的文件路径被篡改，造成权限问题。
	 */
	@Override
	public List<File> exportSelfCheckDataFileByQuatr(String quatr) {
		List<String> fileNameList = dataCheckMapper.queryFileNamesByQuatr(quatr);
		if(null!=fileNameList&&fileNameList.size()>0){
			List<File> fileList = getFilesByPath(fileNameList);
			return fileList;
		}
		return null;
	}

	/**
	 * 根据路径获取文件
	 * @param fileNameList
	 * @return 文件列表
	 */
	public List<File> getFilesByPath(List<String> fileNameList) {
//		String rootPath = getDataCheckFileRootPath();
		List<File> fileList = new ArrayList<File>();
		for(String filePathAndName : fileNameList){
			File file = new File(filePathAndName);
			if(file.exists()){
				fileList.add(file);
			}
		}
		return fileList;
	}
	
	/**
	 * 获取自检文件跟路径
	 * @return
	 */
	public String getDataCheckFileRootPath(){
		return FileConstant.DATA_CHECK_ROOT_PATH;
	}
	
}
