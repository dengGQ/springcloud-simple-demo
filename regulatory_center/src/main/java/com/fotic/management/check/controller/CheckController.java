package com.fotic.management.check.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.base.controller.BaseController;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.FastJsonUtils;
import com.fotic.management.reported.entity.SubmitPerTrade;
import com.fotic.management.reported.service.ISubmitPerTradeService;

/**
 * 报送数据校验
 * 
 * @author WangTao
 *
 */
@Controller
@RequestMapping("check")
public class CheckController extends BaseController {

	@Autowired
	private ISubmitPerTradeService submitTradeService;
	
	/**
	 * 信贷信息管理-重新生成跳转页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/reBuild")
	public String reBuild(Model model) {
		// 获取当前日期前一天
		Date date = DateUtils.getDayBefore(DateUtils.getCurrentDate());
		model.addAttribute("dayBefore", date);
		return "/rebuild/creditRebuild";
	}

	/**
	 * 信贷信息管理(厂检)-重新生成跳转页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/creditInspectionRebuild")
	public String factoryInspectionRebuild() {
		return "/rebuild/creditInspectionRebuild";
	}
	
	/**
	 * 重新生成-机构查询，支持多选
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "orgRebuild" })
	public String orgRebuild(HttpServletRequest request, HttpServletResponse response) {
		return "/rebuild/orgRebuild";
	}

	/**
	 * 执行重新生成存储过程
	 * 
	 * @param reBuildDate
	 *            日期
	 * @return 返回
	 */
	@RequestMapping("/creditRebuild")
	@ResponseBody
	public Map<String, Object> creditRebuild(@RequestParam(required = false) String reBuildDate,@RequestParam(required = false) String dataSrc) {
		String result = this.procedureInvokeRetString("PKG_RHZX_PER_MAIN.MAIN", reBuildDate,dataSrc);
		return FastJsonUtils.stringToCollect(result);
	}

	/**
	 * 执行重新生成存储过程(厂检)
	 * 
	 * @param reBuildStratDate
	 *            开始日期
	 * @param reBuildEndDate
	 *            结束日期
	 * @param coOrgCode
	 *            机构号
	 * @return
	 */
	@RequestMapping("/factoryInspectionRebuild")
	@ResponseBody
	public Map<String, Object> factoryInspectionRebuild(@RequestParam(required = false) String reBuildStratDate,
			@RequestParam(required = false) String reBuildEndDate, @RequestParam(required = false) String coOrgCode) {
		String code = coOrgCode;
		if(coOrgCode.indexOf(",")!=-1){
			String[] orgStr = coOrgCode.split(",");
			String orgCode = "";
			for(int i =0;i<orgStr.length;i++){
				orgCode += ","+orgStr[i]+"";
			}
			code = orgCode.substring(1);
		}
		String result = this.procedureInvokeRetString("PKG_RHZX_PER_JDTEST.MAIN_MANUAL",reBuildStratDate,reBuildEndDate,code);
		return FastJsonUtils.stringToCollect(result);
	}

	/**
	 * 执行预处理存储过程
	 * @param src 数据来源 
	 * @return 返回
	 */
	@RequestMapping("/preCheck")
	@ResponseBody
	public Map<String,Object> precheck(@RequestParam String dataSrc_formSys) {
		Map<String,Object> result = new HashMap<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHH");
			String date = sdf.format(new Date());
			String date1 = sdf1.format(new Date());
			System.out.println("调用PKG_RHZX_PRE_CHECK.MAIN存储过程,入参："+date1+" "+date+" "+dataSrc_formSys);
			this.procedureInvokeRetString("PKG_RHZX_PRE_CHECK.MAIN", date1,date,dataSrc_formSys);
			List<SubmitPerTrade> list = submitTradeService.findAll(Integer.parseInt(dataSrc_formSys));
			SubmitPerTrade submitPerTrade = list.get(0);
			Integer allNum = submitPerTrade.getAllNum();
			Integer successNum =submitPerTrade.getSuccessNum();
			Integer failNum =submitPerTrade.getFailNum();
			result.put("success", true);
			result.put("msg","提交校验:"+allNum+"条" + "<br>校验通过:"+successNum+"条" + "<br>校验未通过:"+failNum +"条" );
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("msg","预校验失败，请联系管理员" );
		}
		return result;
	}
	
}
