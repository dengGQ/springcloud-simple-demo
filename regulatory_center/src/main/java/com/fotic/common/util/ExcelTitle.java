package com.fotic.common.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelTitle {

	public static String[] unreportedInfoTitle = {"业务编号","证件号码","合同编号","所属机构", "应报日期", "数据来源"};
	
	public static String[] errorInfoTitle = {"校验编号","检查日期","检查目标类型","数据来源","机构代码","合同号","业务号","证件类型","证件号码","检查表名","检查字段","检查逻辑字段","检查规则描述"};
	
	public static String[] smsSendDetailTitle = {"日期","合作机构","项目名称","产品名称","客户姓名","证件号码","手机号码","业务号","短信状态","类型","原因说明"};
	
	public static String[] smsSendDetailField = {"sendDate","coOrgName","projName","prodName","custName","credNo","phone","iouNo","sendStatus","smsModuleTypeName","smsCodeDetail"};
	
	public static String[] smsSendStatisticalTitle = {"日期","合作机构","项目名称","产品名称","发送总条数","发送成功条数","发送失败条数"};
	
	public static String[] smsSendStatisticalField = {"sendMonthStr","coOrgName","projName","prodName","sendTotalNum","sendSuccessNum","sendFailNum"};
	
	/**
	 * 导出短信发送详情的标题和字段
	 * @return
	 */
	public static List<Object> smsSendDetailTitleAndField(){
		Map<String,String[]> map = new LinkedHashMap<String,String[]>();
		Map<String,String[]> mapField = new LinkedHashMap<String,String[]>();
		map.put("短信发送详细信息", ExcelTitle.smsSendDetailTitle);
		mapField.put("短信发送详细信息", ExcelTitle.smsSendDetailField);
		List<Object> titleList = new ArrayList<>();
		titleList.add(map);
		titleList.add(mapField);
		return titleList;
	}
	
	/**
	 * 导出短信发送统计台账的标题和字段
	 * @return
	 */
	public static List<Object> smsSendStatisticalTitleAndField(){
		Map<String,String[]> map = new LinkedHashMap<String,String[]>();
		Map<String,String[]> mapField = new LinkedHashMap<String,String[]>();
		map.put("短信发送统计台账", ExcelTitle.smsSendStatisticalTitle);
		mapField.put("短信发送统计台账", ExcelTitle.smsSendStatisticalField);
		List<Object> titleList = new ArrayList<>();
		titleList.add(map);
		titleList.add(mapField);
		return titleList;
	}
	
	

	
}
