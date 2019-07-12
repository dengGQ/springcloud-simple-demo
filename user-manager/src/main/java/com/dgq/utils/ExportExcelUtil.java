package com.dgq.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExportExcelUtil {
	
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
	 * @Description: 导出Excel 单一sheet, 使用默认sheetName
	 * @param @param <T>
	 * @param @param titleAndFieldMap
	 * @param @param data
	 * @param @return    参数
	 * @return SXSSFWorkbook    返回类型
	 * @throws
	 */
	public static <T> SXSSFWorkbook createSingleSheetOfWorkbook(Map<String, String[]> titleAndFieldMap, List<T> data) {
		return createSingleSheetOfWorkbook(titleAndFieldMap, data, null);
	}
	/**
	 * @Description: 导出Excel 单一sheet, 指定sheet名
	 * @param @param <T>
	 * @param @param titleAndFieldMap  以sheetName为key的标题数组，和以sheetName+"filed"为key的储值字段
	 * @param @param data  导出数据
	 * @param @param sheetName sheetName
	 * @param @return    参数
	 * @return SXSSFWorkbook    返回类型
	 * @throws
	 */
	public static <T> SXSSFWorkbook createSingleSheetOfWorkbook(Map<String, String[]> titleAndFieldMap, List<T> data, String sheetName) {
		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		HashMap<String, CellStyle> cellStyle = initCssStyle(workbook);
		createSheet(workbook, titleAndFieldMap, data, sheetName, cellStyle);
		return workbook;
	}
	
	/**
	 * 导出Excel 多sheet, 
	 * dataSet的key作为Sheet的名称
	 * 参数titleAndFieldMap的中key为【SheetName】的数据是当前sheet的标题,
	 * key为【SheetName+"field"】的数据是当前sheet页的储值字段
	 *
	 * @param titleAndFieldMap <sheetName, [title]>,<sheetName+"field", [field]>
	 * @param dataSet 内容集合 <sheetName, [object]>
	 * @return
	 */
	public static <T> SXSSFWorkbook createMultiSheetOfWorkbook(Map<String,String[]> titleAndFieldMap, Map<String, List<T>> dataSet) {
		// 创建工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		HashMap<String, CellStyle> cellStyle = initCssStyle(workbook);
		for (String sheetName : dataSet.keySet()) {
			List<T> data = dataSet.get(sheetName);
			createSheet(workbook, titleAndFieldMap, data, sheetName, cellStyle);
		}
		return workbook;
	}
	
	public static <T> Sheet createSheet(SXSSFWorkbook workbook, Map<String, String[]> titleAndFieldMap, List<? extends Object> data, String sheetName, Map<String, CellStyle> cellStyle) {
		// 按报表类别生成一个sheet页
		Sheet sheet = Objects.isNull(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);
		//sheet title
		String[] title = titleAndFieldMap.get(sheetName);
		// 储值字段
		String[] fields = titleAndFieldMap.get(sheetName+"field");
		// 设置表格默认列宽度为25个字节
		sheet.setDefaultColumnWidth(25);
		// 工作簿行号
		int indexRowNum = 0;
		// 报表标题
		Row rowField = sheet.createRow(indexRowNum);
		for (int i = 0; i < title.length; i++) {
			Cell cell = rowField.createCell(i);
			cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title[i]);
			cell.setCellStyle(cellStyle.get("TITLE"));
		}
		// 遍历集合数据，产生数据行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Object t: data) {
			indexRowNum++;
			Row rowFieldValue = sheet.createRow(indexRowNum);
			Class<? extends Object> tCls = t.getClass();
			for (int i = 0; i < fields.length; i++) {
				Cell cell = rowFieldValue.createCell(i);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				String fieldName = fields[i];
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value == null){
						cell.setCellValue("");
						cell.setCellStyle(cellStyle.get("BODY_CENTER"));
					}else if (value instanceof Date) {
						Date date = (Date) value;
						textValue = sdf.format(date);
						cell.setCellValue(textValue);
						cell.setCellStyle(cellStyle.get("BODY_CENTER"));
					}else{
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						Pattern p = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(textValue);
							cell.setCellStyle(cellStyle.get("BODY_RIGHT"));
						} else {
							cell.setCellValue(textValue);
							cell.setCellStyle(cellStyle.get("BODY_CENTER"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return sheet;
	}
	
	/**
	 * Excel样式
	 * @param SXSSFWorkbook
	 * @return
	 */
	public static HashMap<String, CellStyle> initCssStyle(Workbook workbook) {
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
		
		
		CellStyle style_title = ExportExcelUtil.cssStyleCell(workbook);
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
}
