package com.fotic.management.creditinfo.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.fotic.management.creditinfo.entity.RhzxCsvLoadLog;

public interface IRhzxCsvLoadLogService {

	void insert(RhzxCsvLoadLog entity);

	Map<String, Object> useSqlLoder(File file,StringBuffer sb, int successCount, int errorCount, String resultMsg, String fileType,
			String tableName, String fileFullPath);
	
	List<RhzxCsvLoadLog> queryRhzxCsvLoadLogList(String loadDate);
}
