package com.fotic.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.fotic.management.unreported.entity.ViewUnreported;

public class CSVExportUtil {

	public static void createCSV(String[] head,List<ViewUnreported> list,HttpServletResponse response) {

		// 表格头
		List<Object> headList = Arrays.asList(head);

		// 数据
		List<List<ViewUnreported>> dataList = new ArrayList<List<ViewUnreported>>();
		dataList.add(list);

		String fileName = "testCSV.csv";// 文件名称

		File csvFile = null;
		BufferedWriter csvWtriter = null;
		try {
			csvFile = new File(fileName);
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// gbk使正确读取分隔符","
//			csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "gbk"), 1024);

			 //文件下载，使用如下代码
			 response.setContentType("application/csv;charset=gb18030");
			 response.setHeader("Content-disposition", "attachment; filename=" +
			 URLEncoder.encode(fileName, "UTF-8"));
			 ServletOutputStream out = response.getOutputStream();
			 csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "gbk"), 1024);

			int num = headList.size() / 2;
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < num; i++) {
				buffer.append(" ,");
			}
			csvWtriter.write(buffer.toString() + fileName + buffer.toString());
			csvWtriter.newLine();

			// 写入文件头部
			//writeRow(headList, csvWtriter);

			// 写入文件内容
			for (List<ViewUnreported> row : dataList) {
				writeRow(row, csvWtriter);
			}
			csvWtriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvWtriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写一行数据
	 * 
	 * @param row
	 *            数据列表
	 * @param csvWriter
	 * @throws IOException
	 */
	private static void writeRow(List<ViewUnreported> row, BufferedWriter csvWriter) throws IOException {
		for (ViewUnreported entity : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr = sb.append("\"").append(entity.getIouNo()).append("\",").toString();
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}

}
