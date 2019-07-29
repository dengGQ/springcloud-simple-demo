package com.fotic.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExcelUtil {

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
	public final static SXSSFWorkbook assemblyExcelMuliSheet(Map<String,String[]> titleAndFieldMap, Map<String, List<?>> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		//报表样式
		HashMap<String, CellStyle> map_CellStyle = ExcelUtil.initCssStyle(workbook);
		
		for (Object sheetNameObject : dataSet.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth((short) 25);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) dataSet.get(sheetName);
			String[] mapTitleArr = (String[]) titleAndFieldMap.get(sheetName);
			String[] mapFieldArr = (String[]) titleAndFieldMap.get(sheetName+"field");
			// 工作簿行号
			int indexRowNum = 0;
			// 报表名称
			Row rowTitle = sheet.createRow(indexRowNum);
			Cell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTitle.setCellValue(sheetName);
			cellTitle.setCellStyle(map_CellStyle.get("TITLE"));
			indexRowNum++;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (short i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
	 * 导出功能(组装excel)
	 * 
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static SXSSFWorkbook assemblyExcel(List<Object> titleSet, List<Object> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 创建sheet页面
		// 数据集
		Map map = (Map) dataSet.get(0);
		// 报表标题
		Map mapTitle = (Map) titleSet.get(0);
		// 报表标题属性
		Map mapField = (Map) titleSet.get(1);
		//报表样式
		HashMap<String, CellStyle> map_CellStyle = ExcelUtil.initCssStyle(workbook);
		
		for (Object sheetNameObject : map.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			sheet.setDefaultColumnWidth((short) 25);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) map.get(sheetName);
			String[] mapTitleArr = (String[]) mapTitle.get(sheetName);
			String[] mapFieldArr = (String[]) mapField.get(sheetName);
			// 工作簿行号
			int indexRowNum = 0;
			// 报表名称
			Row rowTitle = sheet.createRow(indexRowNum);
			Cell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTitle.setCellValue(sheetName);
			cellTitle.setCellStyle(map_CellStyle.get("TITLE"));
			indexRowNum++;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (short i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
	 * 导出功能(组装excel)
	 * 
	 * @param coOrgName
	 * @param sheetName
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public final static SXSSFWorkbook assemblyRepExcel(String coOrgName,String sheetName,List<Object> titleSet, List<Map<String, Object>> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 创建sheet页面
		// 报表标题
		Map mapTitle = (Map) titleSet.get(0);
		// 报表标题属性
		Map mapField = (Map) titleSet.get(1);
		//报表样式
		HashMap<String, CellStyle> map_CellStyle = ExcelUtil.initCssStyle(workbook);
		// 按报表类别生成一个sheet页
		Sheet sheet = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为25个字节
		sheet.setDefaultColumnWidth((short) 25);
		// 组装报表名称,标题及内容
		String[] mapTitleArr = (String[]) mapTitle.get(sheetName);
		String[] mapFieldArr = (String[]) mapField.get(sheetName);
		// 工作簿行号
		int indexRowNum = 0;
		// 报表名称
		Row rowTitle = sheet.createRow(indexRowNum);
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellTitle.setCellValue(sheetName);
		cellTitle.setCellStyle(map_CellStyle.get("TITLE"));
		indexRowNum++;
		// 报表机构
		Row rowCoOrg = sheet.createRow(indexRowNum);
		Cell cellCoOrg = rowCoOrg.createCell(0);
		cellCoOrg.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellCoOrg.setCellValue("合作机构:"+coOrgName);
		cellCoOrg.setCellStyle(map_CellStyle.get("TITLE"));
		
		indexRowNum++;
		// 报表标题
		Row rowField = sheet.createRow(indexRowNum);
		int cellTatal = mapTitleArr.length;
		for (short i = 0; i < cellTatal; i++) {
			Cell cell = rowField.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(mapTitleArr[i]);
			cell.setCellStyle(map_CellStyle.get("TITLE"));
		}
		
		// 遍历集合数据，产生数据行
		Iterator<Map<String, Object>> it = dataSet.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//报表内容起始行,合并单元格用
		int startRowNum = indexRowNum+1;
		while (it.hasNext()) {
			indexRowNum++;
			Row rowFieldValue = sheet.createRow(indexRowNum);
			Map<String,Object> dataMap = it.next();
			for (int i = 0; i < mapFieldArr.length; i++) {
				Cell cell = rowFieldValue.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String fieldName = mapFieldArr[i];
				try {
					Object value = dataMap.get(fieldName);
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
		//报表内容结束行,合并单元格用
		int endRowNum = indexRowNum;
		if(endRowNum>startRowNum){
			// 合并总重复率单元格
			CellRangeAddress cra =new CellRangeAddress(startRowNum, endRowNum, cellTatal-1, cellTatal-1); // 起始行, 终止行, 起始列, 终止列  
			sheet.addMergedRegion(cra);  
		}
		return workbook;
	}
	
	/**
	 * 导出功能(组装excel)
	 * 
	 * @param titleSet
	 *            标题集合
	 * @param dataset
	 *            内容集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static SXSSFWorkbook sampleSetLogAssemblyExcel(List<Object> titleSet, List<Object> dataSet) {

		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 创建sheet页面
		// 数据集
		Map map = (Map) dataSet.get(0);
		// 报表标题
		Map mapTitle = (Map) titleSet.get(0);
		// 报表标题属性
		Map mapField = (Map) titleSet.get(1);
		//报表样式
		HashMap<String, CellStyle> map_CellStyle = ExcelUtil.initCssStyle(workbook);
		
		for (Object sheetNameObject : map.keySet()) {
			String sheetName = String.valueOf(sheetNameObject);
			// 按报表类别生成一个sheet页
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置表格默认列宽度为25个字节
			//sheet.setDefaultColumnWidth((short) 25);
			sheet.setColumnWidth(0, 7680);
			sheet.setColumnWidth(1, 25600);
			// 组装报表名称,标题及内容
			List<Object> dataList = (List<Object>) map.get(sheetName);
			String[] mapTitleArr = (String[]) mapTitle.get(sheetName);
			String[] mapFieldArr = (String[]) mapField.get(sheetName);
			// 工作簿行号
			int indexRowNum = 0;
			// 报表名称
			Row rowTitle = sheet.createRow(indexRowNum);
			Cell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTitle.setCellValue(sheetName);
			cellTitle.setCellStyle(map_CellStyle.get("TITLE"));
			indexRowNum++;
			// 报表标题
			Row rowField = sheet.createRow(indexRowNum);
			for (short i = 0; i < mapTitleArr.length; i++) {
				Cell cell = rowField.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mapTitleArr[i]);
				cell.setCellStyle(map_CellStyle.get("TITLE"));
			}
			// 遍历集合数据，产生数据行
			Iterator<Object> it = dataList.iterator();
			while (it.hasNext()) {
				indexRowNum++;
				Row rowFieldValue = sheet.createRow(indexRowNum);
				Object t = (Object) it.next();
				Class tCls = t.getClass();
				for (int i = 0; i < mapFieldArr.length; i++) {
					//sheet.autoSizeColumn(i); 
					Cell cell = rowFieldValue.createCell(i);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					String fieldName = mapFieldArr[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 其它数据类型都当作字符串简单处理
						String textValue = value==null?"":value.toString();
						cell.setCellValue(textValue);
						String cssStyle = i==0?"BODY_CENTER":"BODY_LEFT";
						CellStyle style = map_CellStyle.get(cssStyle);
						style.setWrapText(true);
						cell.setCellStyle(style);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return workbook;
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
			out = new FileOutputStream(filePath);
			workbook.write(out);
			out.flush();
			out.close();
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
	 * 
	 * @param fileName
	 *            文件名
	 * @param SXSSFWorkbook
	 * @param response
	 * @return
	 */
	public static void exportExcel2007(String fileName, SXSSFWorkbook workbook, HttpServletResponse response) {
		// 设置excel文件内容
		OutputStream out = null;
		try {
			/** 第一步初始化表头信息----------------- */
			response.reset();
			response.setContentType("application/msexcel");
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			response.setCharacterEncoding("utf-8");
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
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
			out.flush();
			out.close();
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
	 * Excel样式
	 * @param SXSSFWorkbook
	 * @return
	 */
	public static HashMap<String, CellStyle> initCssStyle(
			SXSSFWorkbook workbook) {
		HashMap<String, CellStyle> map_CellStyle = new HashMap<String, CellStyle>();

		// 1.0 数据样式左对齐
		CellStyle style_bodyLeft = ExcelUtil.cssStyleCell(workbook);
		style_bodyLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 设置左右
		style_bodyLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		style_bodyLeft.setWrapText(true);//设置自动换行 

		Font font_bodyLeft = workbook.createFont();// 创建XSSFFont对象
		font_bodyLeft.setFontName("宋体");// 设置字体
		font_bodyLeft.setColor(HSSFColor.BLACK.index);// 着色
		font_bodyLeft.setFontHeight((short) 200);// 字体大小
		style_bodyLeft.setFont(font_bodyLeft);

		map_CellStyle.put("BODY_LEFT", style_bodyLeft);
		
		// 1.0 数据样式左对齐
		CellStyle style_bodyCenter = ExcelUtil.cssStyleCell(workbook);
		style_bodyCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_bodyCenter.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		style_bodyCenter.setWrapText(true);//设置自动换行 

		Font font_bodyCenter = workbook.createFont();// 创建XSSFFont对象
		font_bodyCenter.setFontName("宋体");// 设置字体
		font_bodyCenter.setColor(HSSFColor.BLACK.index);// 着色
		font_bodyCenter.setFontHeight((short) 200);// 字体大小
		style_bodyCenter.setFont(font_bodyCenter);

		map_CellStyle.put("BODY_CENTER", style_bodyCenter);

		CellStyle style_bodyRight = ExcelUtil.cssStyleCell(workbook);
		style_bodyRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 设置左右
		style_bodyRight.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_bodyRight = workbook.createFont();// 创建XSSFFont对象
		font_bodyRight.setFontName("宋体");// 设置字体
		font_bodyRight.setColor(HSSFColor.BLACK.index);// 着色
		font_bodyRight.setFontHeight((short) 200);// 字体大小
		style_bodyRight.setFont(font_bodyRight);
		style_bodyRight.setWrapText(true);//设置自动换行 
		map_CellStyle.put("BODY_RIGHT", style_bodyRight);
		
		CellStyle style_title = ExcelUtil.cssStyleCell(workbook);
		style_title.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置左右
		style_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 设置上下
		Font font_title = workbook.createFont();// 创建XSSFFont对象
		font_title.setFontName("宋体");// 设置字体
		font_title.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font_title.setColor(HSSFColor.BLACK.index);// 着色
		font_title.setFontHeight((short) 200);// 字体大小
		style_title.setFont(font_title);
		style_title.setFillBackgroundColor(HSSFColor.LIGHT_ORANGE.index);
		style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);

		map_CellStyle.put("TITLE", style_title);

		return map_CellStyle;
	}
	
	@SuppressWarnings("static-access")
	public static CellStyle cssStyleCell(Workbook workbook) {
		CellStyle XSSFCellStyle = workbook.createCellStyle();
		XSSFCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		XSSFCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		XSSFCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		XSSFCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		XSSFCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		XSSFCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		XSSFCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		XSSFCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return XSSFCellStyle;
	}
	
	public static String getUUIDString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
		//return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}
}
