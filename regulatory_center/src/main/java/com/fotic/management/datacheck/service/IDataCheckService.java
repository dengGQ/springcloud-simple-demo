package com.fotic.management.datacheck.service;

import java.io.File;
import java.util.List;

import com.fotic.management.datacheck.entity.SelfCheckExprt;

public interface IDataCheckService {
	
	/**
	 * 人行征信自检数据列表数据查询
	 * @return List<SelfCheckExprt> 自检数据列表
	 */
	 public List<SelfCheckExprt> queryDataCheck(String year);
	
	 /**
	  * 根据序号导出自检数据文件
	  * @param quatr 季度
	  * @return 打包好的文件
	  */
	 public List<File> exportSelfCheckDataFileByQuatr(String quatr);
}
