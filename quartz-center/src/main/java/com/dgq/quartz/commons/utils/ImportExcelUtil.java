package com.dgq.quartz.commons.utils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/*
* @Description: excel导出工具类
* @author dgq
* @date 2019年5月16日
*/
public class ImportExcelUtil {
	
	private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
    
    /**
     * excel导入，所有sheet页
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelMultiSheet(MultipartFile file,  Class<T> clazz, Object[][] field) throws Exception {
    	return importExcelMultiSheet(file, clazz, field, null, null);
    }
    
    /**
     * excel导入，指定sheet页
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @param sheetNums  要导入的sheet页码集合;
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelMultiSheet(MultipartFile file,  Class<T> clazz, Object[][] field, Collection<Integer> sheetNums) throws Exception {
    	return importExcelMultiSheet(file, clazz, field, sheetNums, null);
    }
    
    /**
     * excel导入 指定sheet页
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @param sheetNums 要导入的sheet页码集合;
     * @param startColumnNum  起始列
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelMultiSheet(MultipartFile file,  Class<T> clazz, Object[][] field, Collection<Integer> sheetNums, Integer startColumnNum) throws Exception {
    	List<T> result = new ArrayList<T>();
    	// 创建Excel工作薄
        Workbook work = getWorkbook(file.getInputStream(), file.getOriginalFilename());
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }

        //未指定要导入的sheet页，默认导入所有sheet页
        sheetNums = Objects.nonNull(sheetNums)&sheetNums.size()>0 ? sheetNums : IntStream.range(0, work.getNumberOfSheets()).boxed().collect(Collectors.toList());
        //未指定起始列，默认起始列为0，即第一列
        startColumnNum = Objects.nonNull(startColumnNum) ? startColumnNum : 0;
    	for (Integer sheetNum : sheetNums) {
			result.addAll(sheetToObj(work.getSheetAt(sheetNum), clazz, field, startColumnNum));
    	}
    	return result;
    }
    
    /**
     * 
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelSingleSheet(MultipartFile file,  Class<T> clazz, Object[][] field) throws Exception {
    	return importExcelSingleSheet(file, clazz, field, null, null);
    }
    
    /**
     * 
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @param sheetNum  要导入的sheet页码集合;
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelSingleSheet(MultipartFile file,  Class<T> clazz, Object[][] field, Integer sheetNum) throws Exception {
    	return importExcelSingleSheet(file, clazz, field, sheetNum, null);
    }
    
    /**
     * 
     * @param <T>
     * @param file
     * @param clazz
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型;
     * @param sheetNum  要导入的sheet页码集合;
     * @param startColumnNum  起始列
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelSingleSheet(MultipartFile file,  Class<T> clazz, Object[][] field, Integer sheetNum, Integer startColumnNum) throws Exception {
    	// 创建Excel工作薄
        Workbook work = getWorkbook(file.getInputStream(), file.getOriginalFilename());
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        //未指定sheet，默认导入第一个sheet
        sheetNum = Objects.nonNull(sheetNum) ? sheetNum : 0;
        //未指定开始列，默认起始列为0，即第一列
        startColumnNum = Objects.nonNull(startColumnNum) ? startColumnNum : 0;
    	return sheetToObj(work.getSheetAt(sheetNum), clazz, field, startColumnNum);
    }
    
    /**
     * 将sheet页中的数据序列化成Java对象，取值列的长度 == field的长度
     * @param <T>
     * @param sheet  待序列化sheet
     * @param clazz  
     * @param field  二维数组，shape-1: 接收值的字段; shape-2: 字段类型
     * @return
     * @throws Exception
     */
    private static <T> List<T> sheetToObj(Sheet sheet, Class<T> clazz, Object[][] field, int startColumnNum) throws Exception {
    	List<T> list = new ArrayList<T>();
    	if(Objects.isNull(sheet)) 
    		return list;
    	Row row = null;
    	//行
        for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            //跳过空行或标题行
            if (row == null || row.getFirstCellNum() == j) {
                continue;
            }
            //列
            T o = clazz.newInstance();
            for (int y = startColumnNum; y < field.length+startColumnNum; y++) {
                Object value = getCellValue(row.getCell(y));
                String setMethod = "set"+field[y-startColumnNum][0];
                Method method = clazz.getMethod(setMethod, (Class<?>)field[y-startColumnNum][1]);
                method.invoke(o, value);
                
            }
            list.add(o);
        }
        
        return list;
    }
    
    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }
    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = null;
                break;
            default:
                break;
        }
        return value;
    }

}
