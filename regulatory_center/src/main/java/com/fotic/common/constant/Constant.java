package com.fotic.common.constant;

public class Constant {
	
	
	/**
	 * 短信模板启用/停用状态码值
	 */
	public static final String SMS_MODULE_NO_OPERATR= "0";//未操作过
	public static final String SMS_MODULE_ENABLE = "1";//启用
	public static final String SMS_MODULE_DISABLE = "2";//停用
	
	public static final String SMS_MODELE_DICT_TYPE="SMS_MODULE_TYPE";//短信模版数据字典类别
	
	public static final String SMS_MODELE_TYPE="2";//短信模版模版类别码
	
	public static final String SMS_NORMAL_TYPE="1";//短信模版普通类别码
	
	public static final String SMS_PHONE_REG = "^1[3|4|5|7|8][0-9]{9}$";//校验手机号格式的正则表达式


}
