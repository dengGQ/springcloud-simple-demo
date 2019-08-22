package com.dgq.crs.xml.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.dgq.crs.entity.CrsDataCheckResult;
import com.dgq.crs.xml.bean.AccountReport;
import com.dgq.crs.xml.bean.MessageHeader;
import com.dgq.utils.ExcelUtil;
import com.dgq.utils.PropertiesUtil;

/*
* @Description: public class DownLoadFileUtil{ }
* @author dgq 
* @date 2018年12月21日
*/
public class DownLoadFileUtil {
	
	public static void downloadCheckResult(HttpServletResponse response, HttpServletRequest request, 
			BuisnessChecker data, List<CrsDataCheckResult> dataCheckResult) {
		ZipOutputStream zos = null;
		List<File> files = new ArrayList<>();
		try {
			response.setContentType("APPLICATION/OCTET-STREAM");  
			response.setHeader("Content-Disposition","attachment; filename="+new Date().getTime()+".zip");
			zos = new ZipOutputStream(response.getOutputStream());
			
			List<String[]> fieldList = new ArrayList<>();
			fieldList.add(new String[] {"accountNumber", "desc"});
			fieldList.add(new String[] {"accountNumber", "errData", "colCname", "ruleTypeName", "qualDexDesc", "errCheckName"});
			
			List<List<?>> dataList = new ArrayList<>();
			dataList.add(data.getCheckResult());
			dataList.add(dataCheckResult);
			
			Workbook workbook = ExcelUtil.createWorkBook(response, request, dataList, PropertiesUtil.get("crsCheckResultExcelTemplate"), fieldList);
			File file2 = new File(PropertiesUtil.get("crsReportXmlPath")+"CRS校验失败信息"+new Date().getTime()+".xlsx");
			FileOutputStream fileOutputStream = new FileOutputStream(file2);
			workbook.write(fileOutputStream);

			
			for(Map.Entry<String, List<AccountReport>> entry:data.getAccountReports().entrySet()) {
				MessageHeader mh = data.getMh();
				//新账户或存量账户：N，P
				String messageRefId = data.getMessageRefId().replace("${}", entry.getKey());
				mh.setMessageRefId(messageRefId);
				File file = CreateXmlUitl.createXmlFile(mh, entry.getValue(), PropertiesUtil.get("crsReportXmlPath") +messageRefId+".xml");
				files.add(file);
			}
			files.add(file2);
			FileUtil.outputZipFile(files, zos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(zos != null) {
				try {
					zos.flush();
					zos.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			files.forEach(file->{
				if(file.exists()) {
					file.delete();
				};
			});
		}
	}
}
