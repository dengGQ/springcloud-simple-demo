package com.fotic.management.creditinfo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.base.controller.BaseController;
import com.fotic.common.constant.Constant;
import com.fotic.common.util.PubMethod;
import com.fotic.management.creditinfo.entity.RhzxCsvLoadLog;
import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.creditinfo.service.ICreditAdmService;
import com.fotic.management.creditinfo.service.IRhzxCsvLoadLogService;
import com.fotic.management.publicquery.entity.RhzxDict;
import com.fotic.management.publicquery.service.PublicQueryService;
import com.fotic.management.reported.entity.SubmitPerTrade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 信贷信息管理
 * 
 * @author 顾亚玺
 * @create 2017-06-23
 *
 */
@Controller
@RequestMapping("creditinfo")
public class CreditAdmController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CreditAdmController.class);
	@Autowired
	private ICreditAdmService creditAdmService;
	
	@Autowired
	private IRhzxCsvLoadLogService iRhzxCsvLoadLogService;
	@Autowired
	private PublicQueryService publicQueryService;

	/**
	 * 进入列表页面。
	 * 
	 * @return
	 */
	@RequestMapping(value = { "index" })
	public String index(Model model) {
		logger.info("点击信贷信息管理链接");
		List<SubmitPerTrade> smsModuleTypeList = publicQueryService.queryOverdue();
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		return "/creditinfo/index";
	}
	
	/**
	 * 信息信息管理(厂检)-跳转页面
	 * @return
	 */
	@RequestMapping(value = { "factoryInspection" })
	public String factoryInspection() {
		logger.info("点击信贷信息管理(厂检)链接");
		return "/creditinfo/factoryInspection";
	}

	/**
	 * 查询_分页 
	 * @param pageNumber
	 * @param pageSize
	 * @param projName
	 * @param coOrgNo
	 * @param bussNo
	 * @param credNo
	 * @param checkResult
	 * @param startDate
	 * @param endDate
	 * @param dataSrc 1信贷 2CSV
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPage")
	public Map<String, Object> findPage(
			@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String coOrgCode,
			@RequestParam(required = false) String IOUNo, 
			@RequestParam(required = false) String credNo,
			@RequestParam(required = false) String checkResult, 
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String dataSrc,
			@RequestParam(required = false) String overdue
			) {
		PageHelper.startPage(pageNumber, pageSize);
		//根据查询条件查询、支持模糊查询
		List<RhzxSubmtPerTrade> list = creditAdmService.findList(projectId, coOrgCode, IOUNo, credNo, checkResult, startDate, endDate,dataSrc,overdue);
		PageInfo<RhzxSubmtPerTrade> p = new PageInfo<RhzxSubmtPerTrade>(list);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", list);
		map.put("total", p.getTotal());
		return map;
	}

	
	/**
	 * 修改信息_分页查询
	 * @param conNo 合同号
	 * @return
	 */
	@RequestMapping("queryEditInfoByConNo")
	@ResponseBody
	public Map<String, Object> queryEditInfoByConNo(@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true, defaultValue = "") String conNo) {
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<>();
		//根据合同号，获取实际还款表、计划还款表所需字段，存入list
		List<Map<String, Object>> actualAndRepayInfo = new ArrayList<Map<String, Object>>();
		if(!PubMethod.isEmpty(conNo)){
			actualAndRepayInfo = creditAdmService.getActualAndRepayInfo(conNo);
			if(!PubMethod.isEmpty(actualAndRepayInfo)){
				PageInfo<Map<String, Object>> p = new PageInfo<Map<String, Object>>(actualAndRepayInfo);
				map.put("rows", actualAndRepayInfo);
				map.put("total", p.getTotal());
			}
		}
		return map;
	}

	/**
	 * 跳转修改—页面
	 * 
	 * @param credNum
	 * @param bussNum
	 * @return
	 */
	@RequestMapping(value = { "edit" })
	public String edit(Model model,@RequestParam(required = true, defaultValue = "") String conNo) {
		model.addAttribute("conNo", conNo);
		return "/creditinfo/creditEdit";
	}

	/**
	 * 进入csv导入页面。
	 * 
	 * @return
	 */
	@RequestMapping(value = { "importCsv" })
	public String importCsv(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("点击CSV导入文件管理链接");
		return "/creditinfo/importCsv";
	}
	
	/**
	 * 机构信息导入日志分页数据
	 * @return
	 */
	@RequestMapping("importOrgLogList")
	@ResponseBody
	public Map<String, Object> importOrgLog(@RequestParam(defaultValue = "1") Integer pageNumber,@RequestParam(defaultValue = "10") Integer pageSize, String loadDate){
		PageHelper.startPage(pageNumber, pageSize);
		List<RhzxCsvLoadLog> rhzxCsvLoadLogs = iRhzxCsvLoadLogService.queryRhzxCsvLoadLogList(loadDate);
		PageInfo<RhzxCsvLoadLog> p = new PageInfo<RhzxCsvLoadLog>(rhzxCsvLoadLogs);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("rows", rhzxCsvLoadLogs);
		result.put("total", p.getTotal());
		return result;
	}
	
	/**
	 * 导入机构信息日志页面
	 * @return
	 */
	@RequestMapping("importOrgLogForPage")
	public String importOrgLog(){
		logger.info("进入组织机构导入页面...........");
		return "/creditinfo/importOrgLog";
	}
}