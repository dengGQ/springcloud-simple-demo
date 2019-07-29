package com.fotic.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ch.ethz.ssh2.Connection;

/*
* @Description: sqlldr工具类
* @author 邓国泉 
* @date 2017年11月22日
*/
public class SqlldrUtil {
	
    private static final String CTLFILEPATH = PropertiesUtil.get("uploadFiles");
	
	private static final String IP = PropertiesUtil.get("sqlldr.client.ip");
	
	private static final int PORT = Integer.parseInt(PropertiesUtil.get("sqlldr.client.port"));
	
	private static final String USERNAME =PropertiesUtil.get("sqlldr.client.username");
	
	private static final String PASSWORD =PropertiesUtil.get("sqlldr.client.password");
	/**
	 * sqlldr脚本文件的路径
	 */
	private static final String SQLLDR_SCRIPT_FILE_PATH = PropertiesUtil.get("sqlldr.script.file.path");
	
	/**
	 * sqlldr脚本文件的名称
	 */
	private static final String SQLLDR_SCRIPT_FILE_NAME = PropertiesUtil.get("sqlldr.script.file.name");
	
	private static final String SQLLDR_LOG_FILE_PATH = PropertiesUtil.get("sqlldr.log.file.path");
	/**
	 * oracle目录
	 */
	private static final String ORACLE_HOME = PropertiesUtil.get("sqlldr.client.oracle_home");
	/**
	 * sqlldr TNS
	 */
	private static final String SQLLDR_TNS = PropertiesUtil.get("sqlldr.tns");
	
	private static final String BLANK_SPANCE = " ";
	
	private static final String FILE_SUFFIX_CTL = ".ctl";
	private static final String FILE_SUFFIX_LOG = ".log";
	
	/**
	 * 调用sqlldr导入数据到表
	 * @param fields  导入表字段列 格式：(字段名1 char(2000), 字段名2 char(2000))
	 * @param tab 表名
	 * @param dataFilePath  数据文件全路径及名称
	 */
	public static String importDataToSpecTable(String fields, String tab, String dataFilePath){
		Connection conn = null;
		String str = null;
		try {
			conn = ExecCmdUtil.getConnection(IP, PORT, USERNAME, PASSWORD);
			//数据文件名称
			//String dataFileName = new File(dataFilePath).getName();
			String dataFileName = dataFilePath.substring(dataFilePath.lastIndexOf("/")+1);
			//控制文件名称：【文件名称】.ctl
			String ctlFileName = dataFileName.replaceFirst("\\.\\w*", FILE_SUFFIX_CTL);
			//日志文件名称
			String logFileName = dataFileName.replaceFirst("\\.\\w*", FILE_SUFFIX_LOG);
			//创建控制文件
			String ctlFile = CTLFILEPATH+File.separator+ctlFileName;
			ctlFileWriter(ctlFile, dataFilePath, tab, fields);
			
			//日志文件
			String logFile = SQLLDR_LOG_FILE_PATH+File.separator+logFileName;
			FileUtils.createFile(logFile);
			ExecCmdUtil.exec("chmod 666 "+ctlFile);
			ExecCmdUtil.exec("chmod 666 "+logFile);
			String params = BLANK_SPANCE+ORACLE_HOME+BLANK_SPANCE+SQLLDR_TNS+BLANK_SPANCE+ctlFile+BLANK_SPANCE+logFile;
			
			str = ExecCmdUtil.exec(SQLLDR_SCRIPT_FILE_PATH+File.separator+SQLLDR_SCRIPT_FILE_NAME+params, conn);
			
			File file = new File(ctlFile);
			
			if(file.exists() && file.isFile()){
				file.delete();
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return str;
	}
	
	/**
	 * 创建控制文件（.ctl）
	 * @param filePath  文件路径
	 * @param ctlFileName  控制文件名称
	 * @param dataFileName 数据文件名称
	 * @param tabName    表名
	 * @param fieldName  字段名
	 */
	public static void ctlFileWriter(String ctlFile, String dataFile, String tabName, String fieldName){
		FileWriter fw = null;
		try {
			String strCtl = "OPTIONS (skip=0)"+
						" LOAD DATA CHARACTERSET ZHS16GBK INFILE '"+dataFile+"'"+
						" APPEND INTO TABLE "+tabName+
						" FIELDS TERMINATED BY ','"+
						" OPTIONALLY ENCLOSED BY '\"'" +
						" TRAILING NULLCOLS "+fieldName;
			fw = new FileWriter(ctlFile);
			fw.write(strCtl);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/*public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = ExecCmdUtil.getConnection("10.7.101.116", 22, "oracle", "oracle");
			String filePath = "E:\\中化信托文件夹\\sqlldrl\\";
			String fields = "(NAME char(4000),CERTTYPE char(4000),CERTNO char(4000),DEPTCODE char(4000),GENERALTYPE char(4000),TYPE char(4000),ACCOUNT char(4000),TRADEID char(4000),AREACODE char(4000),DATEOPENED DATE 'yyyyMMdd',DATECLOSED DATE 'yyyyMMdd',CURRENCY char(4000),CREDITLIMIT DECIMAL EXTERNAL,SHAREACCOUNT DECIMAL EXTERNAL,MAXDEBT DECIMAL EXTERNAL,GUARANTEEWAY DECIMAL EXTERNAL,TERMSFREQ char(4000),MONTHDURATION DECIMAL EXTERNAL,MONTHUNPAID DECIMAL EXTERNAL,BILLINGDATE DATE 'yyyyMMdd',RECENTPAYDATE DATE 'yyyyMMdd',SCHEDULEDAMOUNT DECIMAL EXTERNAL,ACTUALPAYAMOUNT DECIMAL EXTERNAL,BALANCE DECIMAL EXTERNAL,CURTERMSPASTDUE DECIMAL EXTERNAL,AMOUNTPASTDUE DECIMAL EXTERNAL,AMOUNTPASTDUE30 DECIMAL EXTERNAL,AMOUNTPASTDUE60 DECIMAL EXTERNAL,AMOUNTPASTDUE90 DECIMAL EXTERNAL,APASTDUE180 DECIMAL EXTERNAL,TERMSPASTDUE DECIMAL EXTERNAL,MAXTERMSPASTDUE DECIMAL EXTERNAL,CLASS5STAT DECIMAL EXTERNAL,ACCOUNTSTAT DECIMAL EXTERNAL,PAYSTAT24MONTH char(4000))";
			String ctrlFileName = "tiu_trade_20171128.ctl";
			String tableName = "RHZX_SUBMT_PER_TRADE";
			ctlFileWriter(filePath+ctrlFileName, "/GRZX/files/uploadFiles/batchFiles/tiu_trade_20171128.csv", tableName, fields);
			//scpFile(filePath+ctrlFileName, "/GRZX/files/uploadFiles/singleFile", conn);
//			String paramas = " /u01/app/oracle/product/11.2.0/dbhome_1/bin REG/reg@10.7.101.67:1521/dwdb /GRZX/files/uploadFiles/singleFile/tiu_trade_20171128.ctl /GRZX/files/uploadFiles/log/tiu_trade_20171128.log";
//			String cmd = "/home/oracle/sqlldr_rhzx.sh"+paramas;
//			String str = ExecCmdUtil.exec(cmd, conn);
//			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}*/
	
	/**
	 * 本地单个文件上传至远程服务器
	 * @param localfile
	 * @param remotefile
	 * @param conn
	 */
	/*public static void scpFile(String localfile, String remoteTargetDirectory, Connection conn){
		try {
			SCPClient scp = conn.createSCPClient();
			scp.put(localfile, remoteTargetDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
