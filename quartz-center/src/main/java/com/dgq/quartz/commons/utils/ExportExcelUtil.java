package com.dgq.quartz.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportExcelUtil {
	
	/**
	 * 导出功能(组装excel)分页查询,一个表的数据一个sheet页
	 * 
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int assemblyExcelMoreSheetByPaging(String[] excelTitleArr, String[] excelFieldArr, List<?> listDataPaging,SXSSFWorkbook workbook,HashMap<String, CellStyle> map_CellStyle,String sheetName,int dataBatchNo,int rowNum) {
		Sheet sheet = workbook.getSheet(sheetName);
		// 工作簿行号
		int indexRowNum = rowNum;
		if(dataBatchNo==1){
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth(25);
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (int i = 0; i < excelTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelTitleArr[i]);
				cell.setCellStyle(map_CellStyle.get("TITLE"));
			}
		}
		// 遍历集合数据，产生数据行
		Iterator<?> it = listDataPaging.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while (it.hasNext()) {
			indexRowNum++;
			Row rowFieldValue = sheet.createRow(indexRowNum);
			Object t = (Object) it.next();
			Class tCls = t.getClass();
			for (int i = 0; i < excelFieldArr.length; i++) {
				Cell cell = rowFieldValue.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				String fieldName = excelFieldArr[i];
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value == null){
						cell.setCellValue("");
						cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
					}else if (value instanceof Date) {
						Date date = (Date) value;
						textValue = sdf.format(date);
						cell.setCellValue(textValue);
						cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
					}else{
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_RIGHT"));
						} else {
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return indexRowNum;
	}
	
	/**
	 * 导出功能(组装excel)多sheet页
	 * 
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static SXSSFWorkbook assemblyExcelMuliSheet(Map<String,String[]> titleAndFieldMap, Map<String, List<?>> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		//报表样式
		HashMap<String, CellStyle> map_CellStyle = initCssStyle(workbook);
		
		for (Object sheetNameObject : dataSet.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth(25);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) dataSet.get(sheetName);
			String[] mapTitleArr = (String[]) titleAndFieldMap.get(sheetName);
			String[] mapFieldArr = (String[]) titleAndFieldMap.get(sheetName+"field");
			// 工作簿行号
			int indexRowNum = 0;
			// 报表名称
			Row rowTitle = sheet.createRow(indexRowNum);
			Cell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellType(SXSSFCell.CELL_TYPE_STRING);
			cellTitle.setCellValue(sheetName);
			cellTitle.setCellStyle(map_CellStyle.get("TITLE"));
			indexRowNum++;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (int i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mapTitleArr[i]);
				cell.setCellStyle(map_CellStyle.get("TITLE"));
			}
			// 遍历集合数据，产生数据行
			Iterator<Object> it = dataList.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (it.hasNext()) {
				indexRowNum++;
				Row rowFieldValue = sheet.createRow(indexRowNum);
				Object t = (Object) it.next();
				Class tCls = t.getClass();
				for (int i = 0; i < mapFieldArr.length; i++) {
					Cell cell = rowFieldValue.createCell(i);
					cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
					String fieldName = mapFieldArr[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value == null){
							cell.setCellValue("");
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else if (value instanceof Date) {
							Date date = (Date) value;
							textValue = sdf.format(date);
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else{
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_RIGHT"));
							} else {
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return workbook;
	}

	/**
	 * 组装poi设置的千分位报表
	 * @param titleAndFieldMap 标题集合
	 * @param dataSet 内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static SXSSFWorkbook assemblyThousandsExcelSheet(Map<String,String[]> titleAndFieldMap, Map<String, List<?>> dataSet,String formula) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();

		//报表样式
		HashMap<String, CellStyle> map_CellStyle = initCssFormulaStyle(workbook,formula);

		for (Object sheetNameObject : dataSet.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth(25);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) dataSet.get(sheetName);
			String[] mapTitleArr = (String[]) titleAndFieldMap.get(sheetName);
			String[] mapFieldArr = (String[]) titleAndFieldMap.get(sheetName+"field");
			// 工作簿行号
			int indexRowNum = 0;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (int i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mapTitleArr[i]);
				cell.setCellStyle(map_CellStyle.get("TITLE"));
			}
			// 遍历集合数据，产生数据行
			Iterator<Object> it = dataList.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (it.hasNext()) {
				indexRowNum++;
				Row rowFieldValue = sheet.createRow(indexRowNum);
				Object t = (Object) it.next();
				Class tCls = t.getClass();
				for (int i = 0; i < mapFieldArr.length; i++) {
					Cell cell = rowFieldValue.createCell(i);
					cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
					String fieldName = mapFieldArr[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value == null){
							cell.setCellValue("");
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else if (value instanceof Date) {
							Date date = (Date) value;
							textValue = sdf.format(date);
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else{
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.valueOf(textValue));
								cell.setCellStyle(map_CellStyle.get("BODY_RIGHT"));
							} else {
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return workbook;
	}
	

	/**
	 * 导出功能(组装excel) 左上角无名称
	 *
	 * @param titleAndFieldMap 标题集合
	 * @param dataSet 内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static SXSSFWorkbook assemblyExcelSheet(Map<String,String[]> titleAndFieldMap, Map<String, List<?>> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();

		//报表样式
		HashMap<String, CellStyle> map_CellStyle = initCssStyle(workbook);

		for (Object sheetNameObject : dataSet.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth(25);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) dataSet.get(sheetName);
			String[] mapTitleArr = (String[]) titleAndFieldMap.get(sheetName);
			String[] mapFieldArr = (String[]) titleAndFieldMap.get(sheetName+"field");
			// 工作簿行号
			int indexRowNum = 0;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (int i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mapTitleArr[i]);
				cell.setCellStyle(map_CellStyle.get("TITLE"));
			}
			// 遍历集合数据，产生数据行
			Iterator<Object> it = dataList.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (it.hasNext()) {
				indexRowNum++;
				Row rowFieldValue = sheet.createRow(indexRowNum);
				Object t = (Object) it.next();
				Class tCls = t.getClass();
				for (int i = 0; i < mapFieldArr.length; i++) {
					Cell cell = rowFieldValue.createCell(i);
					cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
					String fieldName = mapFieldArr[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value == null){
							cell.setCellValue("");
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else if (value instanceof Date) {
							Date date = (Date) value;
							textValue = sdf.format(date);
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else{
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_RIGHT"));
							} else {
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return workbook;
	}
	
	/**
	 * 导出功能(安装excel模版组装excel)
	 * 
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Workbook assemblyExcelByModel(InputStream excelModelStream, List<String[]>  fieldSet, List<List<?>> dataSet) {
        //start-组装excel标题
		Workbook wbModel = null;
		try {
			wbModel = new XSSFWorkbook(excelModelStream);
			excelModelStream.close();
		} catch (Exception ep) {
			if(excelModelStream!=null){
				try {
					excelModelStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ep.printStackTrace();
		} 
		//end-组装excel标题
		// 工作簿行号
		for(int sheetnum = 0; sheetnum < fieldSet.size(); sheetnum++) {
			int indexRowNum = 0;
			//获取excel工作簿
			Sheet sheetModel = wbModel.getSheetAt(sheetnum);
			//获取excel标题所占行数
			int titleRowNum = sheetModel.getLastRowNum();
			// 工作簿行号
			indexRowNum = titleRowNum;
			//报表样式
			HashMap<String, CellStyle> map_CellStyle = initCssStyle(wbModel);
			// 设置表格默认列宽度为25个字节
			sheetModel.setDefaultColumnWidth(25);
			// 报表数据集合
			List<?> dataList = dataSet.get(sheetnum);
			//报表属性集合
			String[] fieldArr = fieldSet.get(sheetnum);
			// 遍历集合数据，产生数据行
			Iterator<?> it = dataList.iterator();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (it.hasNext()) {
				Row rowFieldValue = sheetModel.createRow(++indexRowNum);
				Object t = it.next();
				Class tCls = t.getClass();
				for (int i = 0; i < fieldArr.length; i++) {
					Cell cell = rowFieldValue.createCell(i);
					cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
					String fieldName = fieldArr[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value == null){
							cell.setCellValue("");
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else if (value instanceof Date) {
							Date date = (Date) value;
							textValue = sdf.format(date);
							cell.setCellValue(textValue);
							cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
						}else{
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_RIGHT"));
							} else {
								cell.setCellValue(textValue);
								cell.setCellStyle(map_CellStyle.get("BODY_CENTER"));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return wbModel;
	}
	
	/**
	 * 导出excel集合成zip压缩包功能
	 * @param fileName
	 * @param tempZipPath
	 * @param response
	 * @return
	 */
	public static void exportExcelZip(String fileName, File tempZip, HttpServletResponse response) {
		OutputStream out = null;
		InputStream inputStream = null;
		try {
			/** 第一步初始化表头信息----------------- */
			response.reset();
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName+".zip");
			response.setCharacterEncoding("utf-8");
			out = response.getOutputStream();
			//读取zip文件
	        inputStream = new FileInputStream(tempZip); 
			byte[] buffer = new byte[1024];
			int i = -1;
			//写入文件,弹出保存提示框
			while ((i = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
			//out.flush();
			if (out != null) {
				out.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if(tempZip.exists()){
				//删除临时文件
				tempZip.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
        	if(e.getMessage().indexOf("APR error")==-1)e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(tempZip.exists()){
				//删除临时文件
				tempZip.delete();
			}
			System.gc();
		}
	}
	
	/**
	 * 删除临时文件
	 * @param filePath
	 * @param file
	 */
	public static void deleteTempFile(Map<String,String> filePath, File file) {
		if(filePath!=null){
			File delFile = null;
			for(String key : filePath.keySet()){
				delFile = new File(filePath.get(key));
				if(delFile.exists() && delFile.isFile()){
					delFile.delete();
				}
			}
		}
		if(file!=null){
			if(file.exists() && file.isFile()){
				file.delete();
			}
		}
	}
	
	/**
	 * 删除临时文件
	 * @param filePath
	 * @param file
	 */
	public static void deleteTempFileOrFolder(Map<String,Map<String,String>> filePath, File file) {
		if(filePath!=null){
			File delFile = null;
			String folderPath = "";
			for(String folderNamekey : filePath.keySet()){
				Map<String,String> map = filePath.get(folderNamekey);
				for(String fileName : map.keySet()){
					delFile = new File(map.get(fileName));
					if(delFile.exists() && delFile.isFile()){
						delFile.delete();
					}
					folderPath = map.get(fileName);
				}
				delFile = new File(folderPath.substring(0, folderPath.indexOf(folderNamekey)));
				if(delFile.exists() && delFile.isDirectory()){
					delFile.delete();
				}
			}
		}
		if(file!=null){
			if(file.exists() && file.isFile()){
				file.delete();
			}
		}
	}
	
	/**
	 * 上传excel
	 * @param filePath
	 * @param workbook
	 * @return
	 */
	public static void uploadExcel(String filePath, Workbook workbook) {
		// 设置excel文件内容
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(filePath), false);
			workbook.write(out);
			out.flush();
			if (out != null) {
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.gc();
		}
	}

	/**
	 * 导出功能(导出excel)
	 * @param fileName
	 * @param workbook
	 * @param response
	 * @return
	 */
	public static void exportExcel(String fileName, Workbook workbook, HttpServletResponse response) {
		// 设置excel文件内容
		OutputStream out = null;
		try {
			/** 第一步初始化表头信息----------------- */
			response.reset();
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			//excel2003
			if(workbook instanceof HSSFWorkbook){
				response.setContentType("application/vnd.ms-excel; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
				//excel2007
			}else{
				response.setContentType("application/msexcel");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			}
			response.setCharacterEncoding("utf-8");
			out = response.getOutputStream();
			workbook.write(out);
			//out.flush();
			if (out != null) {
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
        	if(e.getMessage().indexOf("APR error")==-1)e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.gc();
		}
	}
	
	
	
	/**
	 * 导出功能(导出file)
	 * @param fileName
	 * @param filePath
	 * @param response
	 * @return
	 */
	
	public static void exportFile(HttpServletResponse response, String filePath, String fileName)
			throws FileNotFoundException, UnsupportedEncodingException, IOException {
		InputStream fileInputStream = null;
		InputStream fis = null;
		OutputStream toClient = null;
		try{
			fileInputStream = new FileInputStream(filePath);
			fis = new BufferedInputStream(fileInputStream);
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			response.setContentType("mutilpart/form-data");
			toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				toClient.write(buffer, 0, len);
			}
			toClient.flush();
			if (toClient != null) {
				toClient.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}catch (IOException e) {
        	if(e.getMessage().indexOf("APR error")==-1)e.printStackTrace();
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (toClient != null) {
				toClient.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}
	
	/**
	 * Excel样式
	 * @param SXSSFWorkbook
	 * @return
	 */
	public static HashMap<String, CellStyle> initCssStyle(
			Workbook workbook) {
		HashMap<String, CellStyle> map_CellStyle = new HashMap<String, CellStyle>();

		// 1.0 数据样式左对齐
		CellStyle style_bodyLeft = cssStyleCell(workbook);
		style_bodyLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 设置左右
		style_bodyLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyLeft = workbook.createFont();// 创建XSSFFont对象
		font_bodyLeft.setFontName("宋体");// 设置字体
		font_bodyLeft.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyLeft.setFontHeight((short) 220);// 字体大小
		style_bodyLeft.setFont(font_bodyLeft);

		map_CellStyle.put("BODY_LEFT", style_bodyLeft);
		
		// 1.0 数据样式左对齐
		CellStyle style_bodyCenter = cssStyleCell(workbook);
		style_bodyCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_bodyCenter.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyCenter = workbook.createFont();// 创建XSSFFont对象
		font_bodyCenter.setFontName("宋体");// 设置字体
		font_bodyCenter.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyCenter.setFontHeight((short) 220);// 字体大小
		style_bodyCenter.setFont(font_bodyCenter);

		map_CellStyle.put("BODY_CENTER", style_bodyCenter);

		CellStyle style_bodyRight = cssStyleCell(workbook);
		style_bodyRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 设置左右
		style_bodyRight.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_bodyRight = workbook.createFont();// 创建XSSFFont对象
		font_bodyRight.setFontName("宋体");// 设置字体
		font_bodyRight.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyRight.setFontHeight((short) 220);// 字体大小
		style_bodyRight.setFont(font_bodyRight);
		map_CellStyle.put("BODY_RIGHT", style_bodyRight);
		
		
		CellStyle style_title = cssStyleCell(workbook);
		style_title.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_title = workbook.createFont();// 创建XSSFFont对象
		font_title.setFontName("宋体");// 设置字体
		font_title.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font_title.setColor(Font.COLOR_NORMAL);// 着色
		font_title.setFontHeight((short) 220);// 字体大小
		style_title.setFont(font_title);
		style_title.setFillBackgroundColor(Font.COLOR_NORMAL);
		style_title.setFillForegroundColor(Font.COLOR_NORMAL);

		map_CellStyle.put("TITLE", style_title);

		return map_CellStyle;
	}
	
	/**
	 * Excel样式
	 * @param SXSSFWorkbook
	 * @return
	 */
	public static HashMap<String, CellStyle> initCssFormulaStyle(
			Workbook workbook,String formula) {
		HashMap<String, CellStyle> map_CellStyle = new HashMap<String, CellStyle>();

		// 1.0 数据样式左对齐
		CellStyle style_bodyLeft = cssStyleCell(workbook);
		style_bodyLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 设置左右
		style_bodyLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyLeft = workbook.createFont();// 创建XSSFFont对象
		font_bodyLeft.setFontName("宋体");// 设置字体
		font_bodyLeft.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyLeft.setFontHeight((short) 220);// 字体大小
		style_bodyLeft.setFont(font_bodyLeft);

		map_CellStyle.put("BODY_LEFT", style_bodyLeft);
		
		// 1.0 数据样式左对齐
		CellStyle style_bodyCenter = cssStyleCell(workbook);
		style_bodyCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_bodyCenter.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyCenter = workbook.createFont();// 创建XSSFFont对象
		font_bodyCenter.setFontName("宋体");// 设置字体
		font_bodyCenter.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyCenter.setFontHeight((short) 220);// 字体大小
		style_bodyCenter.setFont(font_bodyCenter);

		map_CellStyle.put("BODY_CENTER", style_bodyCenter);

		CellStyle style_bodyRight = cssStyleCell(workbook);
		style_bodyRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 设置左右
		style_bodyRight.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_bodyRight = workbook.createFont();// 创建XSSFFont对象
		font_bodyRight.setFontName("宋体");// 设置字体
		font_bodyRight.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyRight.setFontHeight((short) 220);// 字体大小
		style_bodyRight.setFont(font_bodyRight);
		DataFormat moneyFormat = workbook.createDataFormat();
		style_bodyRight.setDataFormat(moneyFormat.getFormat(formula));
		map_CellStyle.put("BODY_RIGHT", style_bodyRight);
		
		
		CellStyle style_title = cssStyleCell(workbook);
		style_title.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_title = workbook.createFont();// 创建XSSFFont对象
		font_title.setFontName("宋体");// 设置字体
		font_title.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font_title.setColor(Font.COLOR_NORMAL);// 着色
		font_title.setFontHeight((short) 220);// 字体大小
		style_title.setFont(font_title);
		style_title.setFillBackgroundColor(Font.COLOR_NORMAL);
		style_title.setFillForegroundColor(Font.COLOR_NORMAL);

		map_CellStyle.put("TITLE", style_title);

		return map_CellStyle;
	}
	
	/**
	 * Excel样式
	 * @param SXSSFWorkbook
	 * @return
	 */
	public static HashMap<String, CellStyle> initSpecialCssStyle(
			Workbook workbook) {
		HashMap<String, CellStyle> map_CellStyle = new HashMap<String, CellStyle>();

		// 1.0 数据样式左对齐
		CellStyle style_bodyLeft = cssStyleCell(workbook);
		style_bodyLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 设置左右
		style_bodyLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyLeft = workbook.createFont();// 创建XSSFFont对象
		font_bodyLeft.setFontName("宋体");// 设置字体
		font_bodyLeft.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyLeft.setFontHeight((short) 220);// 字体大小
		style_bodyLeft.setFont(font_bodyLeft);

		map_CellStyle.put("BODY_LEFT", style_bodyLeft);
		
		// 1.0 数据样式左对齐
		CellStyle style_bodyCenter = cssStyleCell(workbook);
		style_bodyCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_bodyCenter.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下

		Font font_bodyCenter = workbook.createFont();// 创建XSSFFont对象
		font_bodyCenter.setFontName("宋体");// 设置字体
		font_bodyCenter.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyCenter.setFontHeight((short) 220);// 字体大小
		style_bodyCenter.setFont(font_bodyCenter);

		map_CellStyle.put("BODY_CENTER", style_bodyCenter);

		CellStyle style_bodyRight = cssStyleCell(workbook);
		style_bodyRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 设置左右
		style_bodyRight.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_bodyRight = workbook.createFont();// 创建XSSFFont对象
		font_bodyRight.setFontName("宋体");// 设置字体
		font_bodyRight.setColor(Font.COLOR_NORMAL);// 着色
		font_bodyRight.setFontHeight((short) 220);// 字体大小
		style_bodyRight.setFont(font_bodyRight);
		map_CellStyle.put("BODY_RIGHT", style_bodyRight);
		
		CellStyle style_title = cssStyleCell(workbook);
		style_title.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 设置左右
		style_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_title = workbook.createFont();// 创建XSSFFont对象
		font_title.setFontName("宋体");// 设置字体
		font_title.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font_title.setColor(Font.COLOR_NORMAL);// 着色
		font_title.setFontHeight((short) 220);// 字体大小
		style_title.setFont(font_title);
		style_title.setFillBackgroundColor(Font.COLOR_NORMAL);
		style_title.setFillForegroundColor(Font.COLOR_NORMAL);

		map_CellStyle.put("TITLE", style_title);

		return map_CellStyle;
	}
	
	
	public static CellStyle cssStyleCell(Workbook workbook) {
		CellStyle xssfCellStyle = workbook.createCellStyle();
		xssfCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		xssfCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		xssfCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		xssfCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return xssfCellStyle;
	}
	
	/**
	 * 导出excel集成方法，导出的excel的名称是报表名称+时间（yyyyMMdd）
	 * 
	 * @param response
	 * @param request
	 * @param reportName 报表名称
	 * @param results  结果集
	 * @param excelModelPath  excel模板
	 * @param excelTitle excel 表头
	 */
	public static void exportExcelCommonFunc(HttpServletResponse response,HttpServletRequest request, String reportName, List<List<?>> results, String excelModelPath, List<String[]> excelTitle){
		//excel模板所在路径
		InputStream excelModelStream = request.getSession().getServletContext().getResourceAsStream(excelModelPath);
		Workbook workbook = assemblyExcelByModel(excelModelStream, excelTitle, results);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		exportExcel(reportName+time, workbook, response);
	}
	
	public static Workbook createWorkBook(HttpServletResponse response,HttpServletRequest request, List<List<?>> results, String excelModelPath, List<String[]> excelTitle) {
		InputStream excelModelStream = request.getSession().getServletContext().getResourceAsStream(excelModelPath);
		return assemblyExcelByModel(excelModelStream, excelTitle, results);
	}
	
	/**
	 * 删除临时文件
	 * @param filePath
	 */
	public static void deleteTempFile(String filePath) {
		if(filePath!=null){
			File delFile = new File(filePath);
			if(delFile.exists() && delFile.isFile()){
				delFile.delete();
			}
		}
	}
	
	/**
	 * 删除文件或者文件夹
	 * @param filePath
	 */
	public static void deleteDir(String dirPath) {
		File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            int dirLength = children.length;
            if(dirLength>0){
            	for (int i=0; i<dirLength; i++) {
            		deleteDir(children[i]);
            	}
            	dir.delete();
            }else{
            	dir.delete();
            }
        }else{
        	dir.delete();
        }
	 }
	
	/**
	 * 删除文件
	 * @param filePath
	 */
	public static void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            int dirLength = children.length;
            if(dirLength>0){
            	for (int i=0; i<dirLength; i++) {
            		deleteDir(children[i]);
            	}
            }else{
            	file.delete();
            }
        }else{
        	file.delete();
        }
	 }
	
	public static String getUUIDString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
		//return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}
	
	/**
	 * 保存文件到服务器
	 * @param filePath
	 * @param fis
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void saveFileToServer(String filePath,InputStream fis)
			throws FileNotFoundException, UnsupportedEncodingException, IOException {
		InputStream fileInputStream = null;
		OutputStream toClient = null;
		OutputStream fileOutStream = null;
		try{
			fileOutStream = new FileOutputStream(new File(filePath));
			fileInputStream = new BufferedInputStream(fis);
			toClient = new BufferedOutputStream(fileOutStream);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				toClient.write(buffer, 0, len);
			}
			toClient.flush();
			if (toClient != null) {
				toClient.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (fileOutStream != null) {
				fileOutStream.close();
			}
			if (fis != null) {
				fis.close();
			}
		}catch (IOException e) {
        	if(e.getMessage().indexOf("APR error")==-1)e.printStackTrace();
        }catch (Exception e) {
			throw e;
		}finally {
			if (toClient != null) {
				toClient.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (fileOutStream != null) {
				fileOutStream.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}
}
