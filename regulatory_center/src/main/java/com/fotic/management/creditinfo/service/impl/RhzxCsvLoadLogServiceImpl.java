package com.fotic.management.creditinfo.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.CsvFileCheckUtils;
import com.fotic.common.util.PubMethod;
import com.fotic.common.util.SqlldrUtil;
import com.fotic.management.creditinfo.dao.RhzxCsvLoadLogMapper;
import com.fotic.management.creditinfo.entity.RhzxCsvLoadLog;
import com.fotic.management.creditinfo.service.IRhzxCsvLoadLogService;

@Service
public class RhzxCsvLoadLogServiceImpl implements IRhzxCsvLoadLogService {

	@Autowired
	private RhzxCsvLoadLogMapper rhzxCsvLoadLogMapper;

	private static String DATA_STATUS = "1";
	private static String DATA_SRC = "2";

	@Override
	public void insert(RhzxCsvLoadLog entity) {

		rhzxCsvLoadLogMapper.insert(entity);
	}

	/**
	 * 封装sqlloder方法
	 * 
	 * @param resultMsg
	 *            返回信息
	 * @param fileType
	 *            文件类型
	 * @param tableName
	 *            表名
	 * @param fileFullPath
	 *            文件全路径
	 * @return
	 */
	@Override
	public Map<String, Object> useSqlLoder(File file,StringBuffer sb, int successCount, int errorCount, String resultMsg,
			String fileType, String tableName, String fileFullPath) {
		Map<String, Object> map = new HashMap<>();
		if (!PubMethod.isEmpty(resultMsg)) {
			sb.append(file.getName() + "\t" + resultMsg + "\n");
			errorCount++;
			map.put("status", "false");
		} else {
			// 动态拼接固定列值
			String fileCols = getOrgAndDateAndBatch(getFileType(fileType));
			if(!PubMethod.isEmpty(fileCols)) {
				// 改用sqlldr插入数据
				String importState = SqlldrUtil.importDataToSpecTable(fileCols, tableName, fileFullPath);
				if (!Objects.isNull(importState) && importState.contains("logical record count")) {
					successCount++;
					map.put("status", "success");
				} else {
					sb.append(file.getName() + "\t" + "导入数据失败" + "\n");
					errorCount++;
					map.put("status", "false");
				}
			}else {
				map.put("status", "false");
				return map;
			}
		}
		map.put("successCount", successCount);
		map.put("errorCount", errorCount);
		return map;

	}

	/**
	 * 动态拼接固定列值
	 * 
	 * @param fixedFileds
	 * @return
	 */
	private String getOrgAndDateAndBatch(String fixedFileds) {
		StringBuilder orgAndDateAndBatch = new StringBuilder(fixedFileds);
		orgAndDateAndBatch.append(",DATA_STATUS constant '");
		orgAndDateAndBatch.append(DATA_STATUS);
		orgAndDateAndBatch.append("', BUSS_DATE sysdate ");
		orgAndDateAndBatch.append(", INSERT_DTTM sysdate ");
		orgAndDateAndBatch.append(", DATA_SRC constant '");
		orgAndDateAndBatch.append(DATA_SRC);
		orgAndDateAndBatch.append("')");
		return orgAndDateAndBatch.toString();
	}

	/**
	 * 通过文件类型，获取文件校验头
	 * 
	 * @param fileType
	 * @return
	 */
	private String getFileType(String fileType) {
		String fileColoums = null;
		switch (fileType) {
		case "spetrade":
			fileColoums = CsvFileCheckUtils.spetrade_sqlldr;
			break;

		case "trade":
			fileColoums = CsvFileCheckUtils.trade_sqlldr;
			break;
			
		case "person":
			fileColoums = CsvFileCheckUtils.person_sqlldr;
			break;
			
		case "emp":
			fileColoums = CsvFileCheckUtils.emp_sqlldr;
			break;
			
		case "address":
			fileColoums = CsvFileCheckUtils.address_sqlldr;
			break;
		}
		return fileColoums;
	}

	@Override
	public List<RhzxCsvLoadLog> queryRhzxCsvLoadLogList(String loadDate) {
		StringBuffer sb = new StringBuffer(" where 1=1");
		if(!PubMethod.isEmpty(loadDate)){
			sb.append(" and r.load_date=to_date('").append(loadDate).append("', 'yyyy-mm-dd')");
		}
		List<RhzxCsvLoadLog> rhzxCsvLoadLogs = rhzxCsvLoadLogMapper.queryRhzxCsvLoadLog(sb.toString());
		return rhzxCsvLoadLogs;
	}

}
