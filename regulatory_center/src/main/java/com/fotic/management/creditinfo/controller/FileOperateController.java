package com.fotic.management.creditinfo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fotic.auth.entity.LoginUser;
import com.fotic.base.controller.BaseController;
import com.fotic.common.enums.CommonEnum.StatusEnum;
import com.fotic.common.ui.ztree.BuildTreeNodeUtils;
import com.fotic.common.ui.ztree.TreeNode;
import com.fotic.common.util.CsvFileCheckUtils;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.ExecCmdUtil;
import com.fotic.common.util.FastJsonUtils;
import com.fotic.common.util.PropertiesUtil;
import com.fotic.common.util.PubMethod;
import com.fotic.common.util.SessionUtil;
import com.fotic.management.creditinfo.entity.RhzxCsvLoadLog;
import com.fotic.management.creditinfo.service.IRhzxCsvLoadLogService;
import com.fotic.sms.SmsBody;
import com.fotic.sms.SmsBodyVO;
import com.fotic.sms.dao.SmsBodyMapper;
import com.fotic.sms.service.SmsSendService;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * CSV文件导入, 并调用DSJOB, 保存日志
 * 
 * @author zhaoqh
 */
@Controller
@RequestMapping("fileOperate")
public class FileOperateController extends BaseController {

	private static String RHZX_SUBMT_PER_SPETRADE = "RHZX_SUBMT_PER_SPETRADE";
	private static String RHZX_SUBMT_PER_TRADE = "RHZX_SUBMT_PER_TRADE";
	private static String RHZX_SUBMT_PER_PERSON = "RHZX_SUBMT_PER_PERSON";
	private static String RHZX_SUBMT_PER_EMP = "RHZX_SUBMT_PER_EMP";
	private static String RHZX_SUBMT_PER_ADDRESS = "RHZX_SUBMT_PER_ADDRESS";
	private static final String SMS_SERVER_URL = PropertiesUtil
			.get("sms_server_url");
	private static final String CALL_BACK_URL = PropertiesUtil
			.get("call_back_url");
	private static final String PLATFORM = PropertiesUtil.get("platform");
	@Autowired
	private IRhzxCsvLoadLogService rhzxCsvLoadLogService;
	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private SmsBodyMapper smsBodyMapper;

	/**
	 * 远程ftp映射到本机的
	 */
	String ftpmappingdir = PropertiesUtil.get("ftpdir");

	/**
	 * 跳转到CSV上传页面
	 */
	@RequestMapping("/rediretCvsUploadPage")
	public String rediretCvsUploadPage() {
		return "creditinfo/cvsUploadPage";
	}
	@RequestMapping("/rediretEclUploadPage")
	public String rediretEclUploadPage() {
		return "creditinfo/eclUploadPage";
	}

	/**
	 * 跳转到选择csv文件树形页面
	 * 
	 * @return
	 */
	@RequestMapping("/filestree")
	public String filestree() {
		return "creditinfo/filestree";
	}

	/**
	 * 配合前端ztree，获取目录数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/treedata")
	@ResponseBody
	public String treedata(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		String open = request.getParameter("open");
		String isParent = request.getParameter("isParent");
		String click = request.getParameter("click");
		String path = request.getParameter("path");
		String name = request.getParameter("name");
		String json = null;
		if (null == name) {
			BuildTreeNodeUtils bu = new BuildTreeNodeUtils(ftpmappingdir);
			json = bu.init().toString();
		} else {
			TreeNode tn = new TreeNode(id, pid, Boolean.parseBoolean(open), Boolean.parseBoolean(isParent), name, click,
					path);
			BuildTreeNodeUtils bu = new BuildTreeNodeUtils(tn, true);
			List<TreeNode> findChildNodes = bu.findChildNodes();
			json = FastJsonUtils.toJSONString(findChildNodes);
		}
		// System.out.println(json);
		return json;
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param file
	 *            文件
	 * @param fileNames
	 *            随机生成的文件名称
	 * @param status
	 *            是否导入成功
	 * @param ftpUrl
	 *            文件存储位置
	 * @param userCode
	 *            操作用户
	 */
	public void insetLog(MultipartFile[] file, String[] fileNames, boolean status, String ftpUrl, String userCode) {
		String originalFileName = null;
		String newFileName = null;
		RhzxCsvLoadLog entity = null;
		// TODO 日志入库
		for (int i = 0; i < file.length; i++) {
			originalFileName = file[i].getOriginalFilename();
			newFileName = fileNames[i];
			entity = new RhzxCsvLoadLog();
			entity.setFtpUrl(ftpUrl);
			entity.setFifldName(newFileName);
			entity.setOldFifldName(originalFileName);
			entity.setLoadDate(DateUtils.getCurrentDate());
			entity.setImportType("0");
			if (status) {
				entity.setState(String.valueOf(StatusEnum.SUCCESS.getStatus()));
			} else {
				entity.setState(String.valueOf(StatusEnum.FAIL.getStatus()));
			}

			entity.setUserCode(userCode);
			rhzxCsvLoadLogService.insert(entity);
		}
	}
	
	/**
	 * 批量导入日志记录
	 * @param file
	 * @param fileName
	 * @param status
	 * @param ftpUrl
	 * @param userCode
	 */
	public void batchInsertLog(String fileName,boolean status,String ftpUrl,String userCode) {
		// TODO 日志入库
		RhzxCsvLoadLog entity = new RhzxCsvLoadLog();
		entity.setFtpUrl(ftpUrl);
		entity.setFifldName(fileName);
		entity.setOldFifldName(fileName);
		entity.setLoadDate(DateUtils.getCurrentDate());
		entity.setImportType("1");
		if (status) {
			entity.setState(String.valueOf(StatusEnum.SUCCESS.getStatus()));
		} else {
			entity.setState(String.valueOf(StatusEnum.FAIL.getStatus()));
		}
		entity.setUserCode(userCode);
		rhzxCsvLoadLogService.insert(entity);
		
	}
	
	/**
	 * 批量上传，并导入CSV文件
	 * @param filePath
	 * @param fileSimple
	 * @param request
	 * @return
	 */
	@RequestMapping("/batchReadCsvFile")
	@ResponseBody
	public Map<String, Object> batchReadCsvFile(@RequestParam("filePath") String filePath, HttpServletRequest request) {
		LoginUser user = SessionUtil.getUserFromHttpRequest(request);
		// 返回结果
		Map<String, Object> retMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		int successCount = 0;
		int errorCount = 0;
		try {
			// 批量CSV导入
			if (!PubMethod.isEmpty(filePath)) {
				String pathSub = filePath.substring(0, filePath.length() - 1);
				String[] pathSp = pathSub.split(",");
				for (int i = 0; i < pathSp.length; i++) {
					String resultMsg = null;
					File file = new File(pathSp[i]);
					if (file.isFile()) {
						InputStream in = new FileInputStream(file);
						InputStreamReader reader = new InputStreamReader(in, "GBK");
						CsvParserSettings settings = new CsvParserSettings();
						settings.getFormat().setLineSeparator("\n");
						CsvParser parser = new CsvParser(settings);
						List<String[]> allRows = parser.parseAll(reader);
						if (file.getName().contains("spetrade")) {
							resultMsg = CsvFileCheckUtils.checkFile(allRows, "spetrade");
							map = rhzxCsvLoadLogService.useSqlLoder(file, sb, successCount, errorCount, resultMsg, "spetrade", RHZX_SUBMT_PER_SPETRADE, pathSp[i]);
							batchInsertLog(file.getName(), true, pathSp[i], user.getUsercode());
						} else if (file.getName().contains("trade")) {
							resultMsg = CsvFileCheckUtils.checkFile(allRows, "trade");
							map = rhzxCsvLoadLogService.useSqlLoder(file, sb, successCount, errorCount, resultMsg, "trade", RHZX_SUBMT_PER_TRADE, pathSp[i]);
							batchInsertLog(file.getName(), true, pathSp[i], user.getUsercode());
						} else if (file.getName().contains("person")) {
							resultMsg = CsvFileCheckUtils.checkFile(allRows, "person");
							map = rhzxCsvLoadLogService.useSqlLoder(file, sb, successCount, errorCount, resultMsg, "person", RHZX_SUBMT_PER_PERSON, pathSp[i]);
							batchInsertLog(file.getName(), true, pathSp[i], user.getUsercode());
						} else if (file.getName().contains("emp")) {
							resultMsg = CsvFileCheckUtils.checkFile(allRows, "emp");
							map = rhzxCsvLoadLogService.useSqlLoder(file, sb, successCount, errorCount, resultMsg, "emp", RHZX_SUBMT_PER_EMP, pathSp[i]);
							batchInsertLog(file.getName(), true, pathSp[i], user.getUsercode());
						} else if (file.getName().contains("address")) {
							resultMsg = CsvFileCheckUtils.checkFile(allRows, "address");
							map = rhzxCsvLoadLogService.useSqlLoder(file, sb, successCount, errorCount, resultMsg, "address", RHZX_SUBMT_PER_ADDRESS, pathSp[i]);
							batchInsertLog(file.getName(), true, pathSp[i], user.getUsercode());
						}
						String msg = getResultMsg(map);
						if(!PubMethod.isEmpty(msg)) {
							String[] split = msg.split(";");
							errorCount=Integer.parseInt(split[0]);
							successCount=Integer.parseInt(split[1]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "CSV导入失败");
			return retMap;
		}
		
		if(!PubMethod.isEmpty(map)) {
			if(errorCount>0) {
				retMap.put("msg", "成功" + successCount + "个文件，失败" + errorCount + "个文件，失败原因:" + sb.toString());
			}else {
				retMap.put("msg", "成功" + successCount + "个文件，失败" + errorCount + "个文件，失败原因:" + sb.toString());
			}
		}
		return retMap;
		
	}

	/**
	 * @param map
	 * @param sb
	 * @throws NumberFormatException
	 */
	private String getResultMsg(Map<String, Object> map) throws NumberFormatException {
		String resultMsg = "";
		int errorCount = 0;
		int successCount = 0;
		if(Integer.parseInt(map.get("errorCount").toString())>0) {
			errorCount += Integer.parseInt(map.get("errorCount").toString());
		}else {
			successCount += Integer.parseInt(map.get("successCount").toString());
		}
		resultMsg = errorCount+";"+successCount;
		return resultMsg;
	}
	

	/**
	 * 读取csv文件
	 * 
	 * @param filePath
	 * @param request
	 */
	@RequestMapping("/readCsvFile")
	@ResponseBody
	public Map<String, Object> readCsvFile(@RequestParam("file") MultipartFile[] fileSimple, HttpServletRequest request) {
		LoginUser user = SessionUtil.getUserFromHttpRequest(request);
		// 返回结果
		Map<String, Object> retMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<>();
		int successCount = 0;
		int errorCount = 0;
		try {
			if(fileSimple != null && fileSimple.length > 0) {
				String resultMsg = null;
				String[] fileName = new String[1];
				// 设置服务器中文件保存的根目录
				String basePath = PropertiesUtil.get("uploadFiles");
				for (MultipartFile file : fileSimple) {
					String oriFileName = file.getOriginalFilename();// 上传的文件名
					FileUtils.mkdir(new File(basePath), true);
					String fileFullPath = basePath + "/" + oriFileName;
					File files = new File(fileFullPath);
					// 上传文件
					FileOutputStream out = new FileOutputStream(fileFullPath);
					out.write(file.getBytes());
					out.close();
					//139服务器增加文件权限
					ExecCmdUtil.exec("chmod 666 "+fileFullPath);
					// 文件校验
					InputStream in = new FileInputStream(fileFullPath);
					InputStreamReader reader = new InputStreamReader(in, "GBK");
				//	Exc
					CsvParserSettings settings = new CsvParserSettings();
					settings.getFormat().setLineSeparator("\n");
					CsvParser parser = new CsvParser(settings);
					//ExcelExtractor excelExtractor =new ExcelExtractor(dir)
					List<String[]> allRows = parser.parseAll(reader);
					if (oriFileName.contains("spetrade")) {
						resultMsg = CsvFileCheckUtils.checkFile(allRows, "spetrade");
						map = rhzxCsvLoadLogService.useSqlLoder(files, sb, successCount, errorCount, resultMsg, "spetrade", RHZX_SUBMT_PER_SPETRADE, fileFullPath);
					} else if (oriFileName.contains("trade")) {
						resultMsg = CsvFileCheckUtils.checkFile(allRows, "trade");
						map = rhzxCsvLoadLogService.useSqlLoder(files, sb, successCount, errorCount, resultMsg, "trade", RHZX_SUBMT_PER_TRADE, fileFullPath);
					} else if (oriFileName.contains("person")) {
						resultMsg = CsvFileCheckUtils.checkFile(allRows, "person");
						map = rhzxCsvLoadLogService.useSqlLoder(files, sb, successCount, errorCount, resultMsg, "person", RHZX_SUBMT_PER_PERSON, fileFullPath);
					} else if (oriFileName.contains("emp")) {
						resultMsg = CsvFileCheckUtils.checkFile(allRows, "emp");
						map = rhzxCsvLoadLogService.useSqlLoder(files, sb, successCount, errorCount, resultMsg, "emp", RHZX_SUBMT_PER_EMP, fileFullPath);
					} else if (oriFileName.contains("address")) {
						resultMsg = CsvFileCheckUtils.checkFile(allRows, "address");
						map = rhzxCsvLoadLogService.useSqlLoder(files, sb, successCount, errorCount, resultMsg, "address", RHZX_SUBMT_PER_ADDRESS, fileFullPath);
					}
					fileName[0] = oriFileName;
				}
				// 导入日志表
				insetLog(fileSimple, fileName, true, basePath, user.getUsercode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "CSV导入失败");
			return retMap;
		}
		
		if("success".equals(map.get("status").toString())){
			retMap.put("status", "success");
			retMap.put("msg", "导入文件成功");
		}else if("false".equals(map.get("status").toString())){
			retMap.put("status", "false");
			if(!PubMethod.isEmpty(sb.toString())) {
				retMap.put("msg", sb.toString());
			}else {
				retMap.put("msg", "导入文件失败");
			}
		}
		return retMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/readExcelFile")
	@ResponseBody
	public Map<String, Object> readExcelFile(@RequestParam("file") MultipartFile[] fileSimple, HttpServletRequest request) throws IOException{
			
		LoginUser user = SessionUtil.getUserFromHttpRequest(request);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//返回结果
		Map<String, Object> retMaps = new HashMap<>();
		    Workbook wb =null;
	        Sheet sheet = null;
	        Row row = null;
	        List<Map<String,String>> list = null;
	        
	        List<SmsBody> stuGroupList2=new ArrayList<SmsBody>();
	        List<SmsBodyVO> sbs = null;

	        SmsBodyVO smsBody = new SmsBodyVO();
	        String cellData = null;
	        String fileFullPath =null;
	        String basePath = "D:"+PropertiesUtil.get("uploadFiles");
	        for (MultipartFile file : fileSimple) {
	        	String oriFileName = file.getOriginalFilename();// 上传的文件名
				FileUtils.mkdir(new File(basePath), true);
				fileFullPath = basePath + "/" + oriFileName;
				
				File files = new File(fileFullPath);
				// 上传文件
				FileOutputStream out = new FileOutputStream(fileFullPath);
				out.write(file.getBytes());
				out.close();
	        }
	        
	        
	       // String filePath = "D:\\test.xlsx";
	        String columns[] = {"account","projId","credNo","GENDER","custName","phone","coOrgCode"};
	        wb = readExcel(fileFullPath);
	        
	        if(wb != null){
	            //用来存放表中数据
	            list = new ArrayList<Map<String,String>>();
	            //获取第一个sheet
	            sheet = wb.getSheetAt(0);
	            //获取最大行数
	            int rownum = sheet.getPhysicalNumberOfRows();
	            //获取第一行
	            row = sheet.getRow(0);
	            //获取最大列数
	            int colnum = row.getPhysicalNumberOfCells();
	            for (int i = 1; i<rownum; i++) {
	            	Map<String,String> map = new LinkedHashMap<String, String>();
	                row = sheet.getRow(i);
	                if(row !=null){
	                    for (int j=0;j<colnum;j++){
	                        cellData = (String) getCellFormatValue(row.getCell(j));
	                        map.put(columns[j], cellData);
	                       
	                    }
	                   String smsString = smsBodyMapper.querySendSmsMontent((String) getCellFormatValue(row.getCell(1)));
	                   String custNameString = (String) getCellFormatValue(row.getCell(3));
	                   if (custNameString.equals("男")) {
	                	   custNameString = "先生";
					}else if (custNameString.equals("女")) {
						custNameString = "女士";
					}else {
						custNameString = "先生（女士）";
					}
	                   smsString = smsString.replace(
	   						"#客户姓名#",
							Objects.isNull((String) getCellFormatValue(row.getCell(4))) ? "【客户姓名】" : (String) getCellFormatValue(row.getCell(4)))
					.replace(
							"#称呼#",
							Objects.isNull(custNameString) ? "先生（女士）" : custNameString)
					.replace(
							"#合作机构#",
							Objects.isNull((String) getCellFormatValue(row.getCell(6))) ? "【合作机构】" : (String) getCellFormatValue(row.getCell(6)))
					;
	                    map.put("smsModuleType", "2");
	                    map.put("smsContent", smsString);
	                    map.put("callBackUrl", SMS_SERVER_URL);
	                    map.put("repeatRole", "1|2|3|4|7|8:Overlimit！|9|10|15|ERR");
	                    map.put("platform", PLATFORM);
	                    map.put("repeatSendCount", "1");
	                    map.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	                    map.put("sendDate", format.format(new Date()) );
	                    map.put("createTime", format.format(new Date()));
	                }else{
	                    break;
	                }
	                list.add(map);
	                //sbs.add((SmsBody) map);
	            }
	        }
	        EntityBean ent =new EntityBean();
	        sbs = ent.parse(list, smsBody.getClass());
	        for (SmsBodyVO sBodyVO : sbs) {
				System.out.println(sBodyVO.getAccount());
				SmsBody smsBodys = new SmsBody();
				smsBodys.setAccount(sBodyVO.getAccount());
				smsBodys.setCallBackUrl(sBodyVO.getCallBackUrl());
				smsBodys.setCoOrgCode(sBodyVO.getCoOrgCode());
				smsBodys.setCreateTime(DateUtils.formatDate(sBodyVO.getCreateTime(),DateUtils.DATETIME_DEFAULT_FORMAT));
				smsBodys.setCredNo(sBodyVO.getCredNo());
				smsBodys.setCustName(sBodyVO.getCustName());
				smsBodys.setPhone(sBodyVO.getPhone());
				smsBodys.setPlatform(sBodyVO.getPlatform());
				smsBodys.setProjId(sBodyVO.getProjId());
				smsBodys.setRepeatRole(sBodyVO.getRepeatRole());
				smsBodys.setRepeatSendCount(sBodyVO.getRepeatSendCount());
				smsBodys.setSendDate(DateUtils.formatDate(sBodyVO.getSendDate(),DateUtils.DATETIME_DEFAULT_FORMAT));
				smsBodys.setSmsContent(sBodyVO.getSmsContent());
				smsBodys.setSmsModuleType(sBodyVO.getSmsModuleType());
				smsBodys.setUuid(sBodyVO.getUuid());
				stuGroupList2.add(smsBodys);
			}
	        try {
				smsSendService.sendSms(stuGroupList2);
				retMaps.put("status", "success");
				retMaps.put("msg", "手动处理短信导入成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				retMaps.put("status", "false");
				retMaps.put("msg", "手动处理短信导入失败");
				e.printStackTrace();
			}
		return retMaps;
	}
	
	//public List<SmsBody> u = new ArrayList<SmsBody>();
	 public static Workbook readExcel(String filePath){
	        Workbook wb = null;
	        if(filePath==null){
	            return null;
	        }
	        String extString = filePath.substring(filePath.lastIndexOf("."));
	        InputStream is = null;
	        try {
	            is = new FileInputStream(filePath);
	            if(".xls".equals(extString)){
	                return wb = new HSSFWorkbook(is);
	            }else if(".xlsx".equals(extString)){
	                return wb = new XSSFWorkbook(is);
	            }else{
	                return wb = null;
	            }
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return wb;
	    }
	    public static Object getCellFormatValue(Cell cell){
	        Object cellValue = null;
	        if(cell!=null){
	            //判断cell类型
	            switch(cell.getCellType()){
	            case Cell.CELL_TYPE_NUMERIC :{
	            	cell.setCellType(cell.CELL_TYPE_STRING);
	                //cellValue = String.valueOf(cell.getNumericCellValue());
	            	cellValue = cell.getRichStringCellValue().getString();
	            	break;
	            }
	            case Cell.CELL_TYPE_FORMULA :{
	                //判断cell是否为日期格式
	                if(DateUtil.isCellDateFormatted(cell)){
	                    //转换为日期格式YYYY-mm-dd
	                    cellValue = cell.getDateCellValue();
	                }else{
	                    //数字
	                    cellValue = String.valueOf(cell.getNumericCellValue());
	                }
	                break;
	            }
	            case Cell.CELL_TYPE_STRING:{
	                cellValue = cell.getRichStringCellValue().getString();
	                break;
	            }
	            default:
	                cellValue = "";
	            }
	        }else{
	            cellValue = "";
	        }
	        return cellValue;
	    } 	

	    
	    EntityBean ent =new EntityBean();
//}  
}
