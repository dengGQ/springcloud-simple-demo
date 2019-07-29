package com.fotic.common.util;

import java.util.List;
/**
 * 文件校验工具类
 */
public class CsvFileCheckUtils {
	
	/**
	 * 居住信息
	 */
	private static String address = "NAME|30|Y,CERTTYPE|1|Y,CERTNO|18|Y,DEPTCODE|14|Y,RESIDENCE|60|Y,RESZIP|6|Y,RESCONDITION|1|Y";
	
	public static String address_sqlldr = "(NAME char(4000),CERTTYPE char(4000),CERTNO char(4000),DEPTCODE char(4000),RESIDENCE char(4000),RESZIP char(4000),RESCONDITION char(4000)";
	/**
	 * 交易信息
	 */
	private static String trade = "NAME|30|Y,CERTTYPE|1|Y,CERTNO|18|Y,DEPTCODE|14|Y,GENERALTYPE|1|Y,TYPE|2|Y,ACCOUNT|40|Y,TRADEID|100|Y,AREACODE|6|Y,DATEOPENED|8|Y,DATECLOSED|8|Y,CURRENCY|3|Y,CREDITLIMIT|10|Y,SHAREACCOUNT|10|Y,MAXDEBT|10|Y,GUARANTEEWAY|1|Y,TERMSFREQ|2|Y,MONTHDURATION|3|Y,MONTHUNPAID|3|Y,BILLINGDATE|8|Y,RECENTPAYDATE|8|Y,SCHEDULEDAMOUNT|10|Y,ACTUALPAYAMOUNT|10|Y,BALANCE|10|Y,CURTERMSPASTDUE|2|Y,AMOUNTPASTDUE|10|Y,AMOUNTPASTDUE30|10|Y,AMOUNTPASTDUE60|10|Y,AMOUNTPASTDUE90|10|Y,APASTDUE180|10|Y,TERMSPASTDUE|3|Y,MAXTERMSPASTDUE|2|Y,CLASS5STAT|1|Y,ACCOUNTSTAT|1|Y,PAYSTAT24MONTH|1|Y";
	
	public static String trade_sqlldr = "(NAME char(4000),CERTTYPE char(4000),CERTNO char(4000),DEPTCODE char(4000),GENERALTYPE char(4000),TYPE char(4000),ACCOUNT char(4000),TRADEID char(4000),AREACODE char(4000),DATEOPENED DATE 'yyyyMMdd',DATECLOSED DATE 'yyyyMMdd',CURRENCY char(4000),CREDITLIMIT DECIMAL EXTERNAL,SHAREACCOUNT DECIMAL EXTERNAL,MAXDEBT DECIMAL EXTERNAL,GUARANTEEWAY DECIMAL EXTERNAL,TERMSFREQ char(4000),MONTHDURATION DECIMAL EXTERNAL,MONTHUNPAID DECIMAL EXTERNAL,BILLINGDATE DATE 'yyyyMMdd',RECENTPAYDATE DATE 'yyyyMMdd',SCHEDULEDAMOUNT DECIMAL EXTERNAL,ACTUALPAYAMOUNT DECIMAL EXTERNAL,BALANCE DECIMAL EXTERNAL,CURTERMSPASTDUE DECIMAL EXTERNAL,AMOUNTPASTDUE DECIMAL EXTERNAL,AMOUNTPASTDUE30 DECIMAL EXTERNAL,AMOUNTPASTDUE60 DECIMAL EXTERNAL,AMOUNTPASTDUE90 DECIMAL EXTERNAL,APASTDUE180 DECIMAL EXTERNAL,TERMSPASTDUE DECIMAL EXTERNAL,MAXTERMSPASTDUE DECIMAL EXTERNAL,CLASS5STAT DECIMAL EXTERNAL,ACCOUNTSTAT DECIMAL EXTERNAL,PAYSTAT24MONTH char(4000)";
	/**
	 * 就业信息
	 */
	private static String emp = "NAME|30|Y,CERTTYPE|1|Y,CERTNO|18|Y,DEPTCODE|14|Y,OCCUPATION|1|Y,COMPANY|60|Y,INDUSTRY|1|Y,OCCADDRESS|60|N,OCCZIP|6|N,STARTYEAR|4|N,DUTY|1|Y,CASTE|1|Y,ANNUALINCOME|10|N";
	
	public static String emp_sqlldr = "(NAME char(4000),CERTTYPE char(4000),CERTNO char(4000),DEPTCODE char(4000),OCCUPATION char(4000),COMPANY char(4000),INDUSTRY char(4000),OCCADDRESS char(4000),OCCZIP char(4000),STARTYEAR DECIMAL EXTERNAL,DUTY DECIMAL EXTERNAL,CASTE DECIMAL EXTERNAL,ANNUALINCOME DECIMAL EXTERNAL";
	/**
	 * 人员自然信息
	 */
	private static String person = "NAME|30|Y,CERTTYPE|1|Y,CERTNO|18|Y,DEPTCODE|14|Y,GENDER|1|Y,BIRTHDAY|8|Y,MARRIAGE|2|Y,EDULEVEL|2|Y,EDUDEGREE|1|Y,SPOUSE_NAME|30|N,SPOUSE_CERTNO|18|N,SPOUSE_CERTTYPE|1|N,SPOUSE_OFFICE|60|N,SPOUSE_TEL|25|N,HOMETEL|25|N,MOBILETEL|11|N,OFFICETEL|25|N,EMAIL|30|N,ADDRESS|60|Y,ZIP|6|Y,RESIDENCE|60|N";
	
	public static String person_sqlldr = "(NAME char(4000),CERTTYPE char(4000),CERTNO char(4000),DEPTCODE char(4000),GENDER char(4000),BIRTHDAY DATE 'yyyyMMdd',MARRIAGE DECIMAL EXTERNAL,EDULEVEL DECIMAL EXTERNAL,EDUDEGREE DECIMAL EXTERNAL,SPOUSE_NAME char(4000),SPOUSE_CERTNO char(4000),SPOUSE_CERTTYPE char(4000),SPOUSE_OFFICE char(4000),SPOUSE_TEL char(4000),HOMETEL char(4000),MOBILETEL char(4000),OFFICETEL char(4000),EMAIL char(4000),ADDRESS char(4000),ZIP char(4000),RESIDENCE char(4000)";
	/**
	 * 特殊信息
	 */
	private static String spetrade = "ACCOUNT|40|Y,TRADEID|100|Y,DEPTCODE|14|Y,SPECTYPE|1|Y,SPECDATE|8|Y,SPECMONTH|4|Y,SPECMONEY|10|Y,SPECDETAIL|200|N";
	
	public static String spetrade_sqlldr = "(ACCOUNT char(4000),TRADEID char(4000),DEPTCODE char(4000),SPECTYPE DECIMAL EXTERNAL,SPECDATE DATE 'yyyyMMdd',SPECMONTH DECIMAL EXTERNAL,SPECMONEY DECIMAL EXTERNAL,SPECDETAIL char(4000)";
	
	/**
	 * 校验文件列数、字段长度以及是否为必输项
	 * @param list
	 * @param type
	 * @return
	 */
	public static String checkFile(List<String[]> list,String type) {
		String errorMsg = null;
		String fileType = getFileType(type);
		String[] row = fileType.split(",");
		check:for (int i =0;i<list.size();i++) {
			String[] allRows = list.get(i);
			if(allRows.length < row.length) {
				errorMsg = "文件列数不符合要求";
				System.out.println(errorMsg);
				break;
			}
			
			if(PubMethod.isEmpty(errorMsg)) {
				for(int j=0 ; j<row.length ; j++){
					if(PubMethod.isEmpty(allRows[j])) {
						String mustFlag = row[j].split("\\|")[2];
						if("Y".equals(mustFlag)) {//判断是否为必输项
							errorMsg = row[j].split("\\|")[0]+"字段不可为空";
							System.out.println(j+":"+i+errorMsg);
							break check;
						}
					}
					
					if(!PubMethod.isEmpty(allRows[j])) {
						if(allRows[j].length()>Long.parseLong(row[j].split("\\|")[1])){
							errorMsg = row[j].split("\\|")[0]+"字段长度超长";
							System.out.println(j+":"+errorMsg);
							break check;
						}
					}
    			}
			}
		}
		if(!PubMethod.isEmpty(errorMsg)) {
			errorMsg = "文件错误";
			return errorMsg;
		}
		return errorMsg;
	}

	
	
	/**
	 * 文件接口属性
	 * @param type
	 */
	private static String getFileType(String type) {
		String fileType = null;
		switch (type) {
		case "address":
			fileType = address;
			break;
		case "trade":
			fileType = trade;
			break;
		case "emp":
			fileType = emp;
			break;
		case "person":
			fileType = person;
			break;
		case "spetrade":
			fileType = spetrade;
			break;
		}
		return fileType;
	}

}
