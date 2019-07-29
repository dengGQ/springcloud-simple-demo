package com.fotic.management.sms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fotic.auth.entity.LoginUser;
import com.fotic.common.constant.Constant;
import com.fotic.common.enums.CommonEnum.StatusEnum;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.ExcelTitle;
import com.fotic.common.util.ExcelUtil;
import com.fotic.common.util.PubMethod;
import com.fotic.common.util.SessionUtil;
import com.fotic.common.util.SmsUtil;
import com.fotic.common.util.StringUtils;
import com.fotic.management.publicquery.entity.Project;
import com.fotic.management.publicquery.entity.RhzxDict;
import com.fotic.management.publicquery.entity.RhzxOrg;
import com.fotic.management.publicquery.entity.RhzxProdure;
import com.fotic.management.publicquery.service.PublicQueryService;
import com.fotic.management.sms.dao.SmsSendDetailMapper;
import com.fotic.management.sms.entity.SmsCode;
import com.fotic.management.sms.entity.SmsModule;
import com.fotic.management.sms.entity.SmsPrepareSendRecord;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.management.sms.entity.SmsSettingRule;
import com.fotic.management.sms.entity.SmsVariablesSetting;
import com.fotic.management.sms.service.ISmsService;
import com.fotic.management.sms.vo.EasyUITreeNodeUtil;
import com.fotic.management.sms.vo.EasyUITreeNodeUtil.EasyUITreeNode;
import com.fotic.sms.SmsBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
 * 和短信发送有关的Controller
 *
 */
@Controller
@RequestMapping("sms")
public class SmsController {
	
	private static Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private ISmsService smsService;
	@Autowired
	private PublicQueryService publicQueryService;
	@Autowired
	private SmsSendDetailMapper smsSendDetailMapper;
	
	/**
	 * 跳转到短信模板列表页面
	 * @return
	 */
	@RequestMapping(value="smsModuleList")
	public String smsModuleList(Model model){
		logger.info("正在跳转到短信配置功能-短信模板列表页面>>>>>>>>>>>>>>>>>>>>");
		queryOrgAndProject();
		List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		return "/sms/smsModuleList";
	}
	
	/**
	 * 跳转到短信发送明细查询页面
	 * @return
	 */
	@RequestMapping(value="smsSendDetailList")
	public String smsSendDetailList(Model model){
		logger.info("正在跳转到短信发送明细查询-短信发送明细查询页面>>>>>>>>>>>>>>>>>>>>");
		List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		return "/sms/smsSendDetailList";
	}
	
	/**
	 * 跳转到待短信管理查询页面
	 * @return
	 * tlh
	 */
	@RequestMapping(value="smsSendList")
	public String smsSendList(Model model){
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>正在跳转到待短信发送明细查询-短信发送明细查询页面>>>>>>>>>>>>>>>>>>>>");
		List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		return "/sms/smsSendList";
	}
	/**
	 * 跳转到短信发送统计台账页面
	 * @return
	 */
	@RequestMapping(value="smsSendStatistical")
	public String smsSendStatistical(){
		logger.info("跳转到短信发送统计台账页面>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return "/sms/smsSendStatistical";
	}
	
	@RequestMapping(value="index_x")
	public String index_x(){
		logger.info("跳转到短信发送统计台账页面>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return "/index_x";
	}
	/**
	 * 跳转到短信模板新增页面
	 * @return
	 */
	@RequestMapping(value="addSmsModulePage")
	public String addSmsModulePage(HttpServletRequest request,Model model) throws Exception{
		logger.info("正在跳转到短信配置功能-短信模板新增页面>>>>>>>>>>>>>>>>>>>>");
		//获取前登陆用户
		String operator = SessionUtil.getUserFromSession(request.getSession()).getUsername();
		//查询宏变量信息
		List<SmsVariablesSetting> variablesSettingList = smsService.querySmsVariablesSettingList();
		List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		model.addAttribute("operator", operator);
		model.addAttribute("variablesSettingList", variablesSettingList);
		model.addAttribute("flag", "add");
		return "/sms/addSmsModule";
	}
	
	/**
	 * 从短信发送详情页面跳转到编辑提交发送页面
	 * @return
	 */
	@RequestMapping(value="editAndSubmit")
	public String editAndSubmit(HttpServletRequest request,Model model,Integer detailId){
		logger.info("从短信发送详情页面跳转到编辑提交发送页面>>>>>>>>>>>>>>>>>>>>>>>>");
		/*List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);*/
		model.addAttribute("detailId", detailId);
		return "/sms/editAndSubmit";
	}
	
	/**
	 * 模板详情页，有两份数据用于回显： 模板和规则
	 * @param request
	 * @param model
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value="smsModuleDetailPage")
	public String moduleDetail(HttpServletRequest request, Model model, Integer moduleId){
		
		SmsModule smsModule = smsService.querySmsModuleById(moduleId);
		//指定模板下已有的规则设置项
//		List<SmsSettingRule> smsSettingRules = smsService.querySmsSettingRulesByModuleId(moduleId);
		
		model.addAttribute("smsModule", smsModule);
//		model.addAttribute("smsSettingRules", JSON.toJSONString(smsSettingRules));
		model.addAttribute("operator", smsModule.getOperator());
		model.addAttribute("createTime", new SimpleDateFormat("yyyy-MM-dd").format(smsModule.getCreateTime()));
		return "/sms/smsModuleDetailPage";
	}
	
	@RequestMapping(value="toModuleEditPage")
	public String moduleEdit(HttpServletRequest request, Model model, Integer moduleId){
		SmsModule smsModule = smsService.querySmsModuleById(moduleId);
		//查询宏变量信息
		List<SmsVariablesSetting> variablesSettingList = smsService.querySmsVariablesSettingList();
		
		//指定模板下已有的规则设置项
		List<SmsSettingRule> smsSettingRules = smsService.querySmsSettingRulesByModuleId(moduleId);
		List<RhzxDict> smsModuleTypeList = publicQueryService.findAllRhzxDictByType(Constant.SMS_MODELE_DICT_TYPE);
		model.addAttribute("smsModuleTypeList", smsModuleTypeList);
		model.addAttribute("smsModule", smsModule);
//		model.addAttribute("createTime", new SimpleDateFormat("yyyy-MM-dd").format(smsModule.getCreateTime()));
		model.addAttribute("variablesSettingList", variablesSettingList);
		model.addAttribute("smsSettingRules", JSON.toJSON(smsSettingRules));
		
		return "/sms/editSmsModulePage";
	}
	
	@RequestMapping(value="editSave")
	@ResponseBody
	public Map<String,Object> editSave(HttpServletRequest request, @ModelAttribute SmsModule smsModule, @RequestBody List<SmsSettingRule> smsSettingRuleList){
		Map<String,Object> params = new HashMap<String, Object>();
		try {
			smsService.editModule(smsModule, smsSettingRuleList);
			params.put("msg", "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			params.put("msg", "修改失败！");
		}
		return params;
	}
	/**
	 * 规则管理-发送对象规则数据
	 * @param level
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loadTreeData", method=RequestMethod.POST)
	@ResponseBody
	public String loadTreeData(Integer level, String id, Integer moduleId, String coOrgCode) throws Exception{
		List<EasyUITreeNode> nodes = null;
		if(StringUtils.isBlank(id)){
			nodes = EasyUITreeNodeUtil.convertTreeNode("0", "全部", 0, "closed");
		}else if(level == 1){
			List<RhzxOrg> orgs = publicQueryService.queryOrg();
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "coOrgCode", "coOrgName", "level", "closed");
		}else if(level == 2){
			List<Project> orgs = publicQueryService.queryProjectByCoOrgCode(id);
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "projId", "projName", "level", "closed");
		}else{
			List<RhzxProdure> orgs = publicQueryService.queryProductByProjIdAndCoorgcode(id, coOrgCode);
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "prodCode", "prodName", "level", "open");
		}
		return JSON.toJSONString(nodes);
	}
	
	/**
	 * 规则管理-发送对象规则数据回显
	 * @param level
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loadTreeDataModuleDetail", method=RequestMethod.POST)
	@ResponseBody
	public String loadTreeDataModuleDetail(Integer level, String id, String moduleId, String coOrgCode) throws Exception{
		List<EasyUITreeNode> nodes = null;
		if(StringUtils.isBlank(id)){
			nodes = EasyUITreeNodeUtil.convertTreeNode("0", "全部", 0, "closed");
		}else if(level == 1){
			List<RhzxOrg> orgs = publicQueryService.queryOrg(moduleId);
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "coOrgCode", "coOrgName", "level", "closed");
		}else if(level == 2){
			List<Project> orgs = publicQueryService.queryProjectByCoOrgCode(id, moduleId);
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "projId", "projName", "level", "closed");
		}else{
			List<RhzxProdure> orgs = publicQueryService.queryProductByProjIdAndModuleIdWithCoorgcode(id, moduleId, coOrgCode);
			nodes = EasyUITreeNodeUtil.convertTreeNode(orgs, "prodCode", "prodName", "level", "open");
		}
		return JSON.toJSONString(nodes);
	}
	
	/**
	 * 短信发送明细查询
	 * @return
	 */
	@RequestMapping(value="querySmsSendDetailList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> querySmsSendDetailList(@ModelAttribute SmsSendDetail detail,
			@RequestParam(required=true, defaultValue="1") Integer pageNumber,
			@RequestParam(required=true, defaultValue="10") Integer pageSize){
		logger.info("短信发送明细查询querySmsSendDetailList>>>>>>>>>>>>>>>>>>>>");
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		if("-1".equals(detail.getProjId())){
			detail.setProjId("CSV导入");
		}
		if("-1".equals(detail.getProdCode())){
			detail.setProdCode("CSV导入");
		}
		List<SmsSendDetail> smsList = smsService.querySmsSendDetailList(detail);
		PageInfo<SmsSendDetail> pageInfo = new PageInfo<SmsSendDetail>(smsList);
		map.put("total", pageInfo.getTotal());
		map.put("rows", smsList);
		return map;
	}
	
	/**
	 * 短信发送管理
	 * @return
	 */
	@RequestMapping(value="querySmsSendList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> querySmsSendList(@ModelAttribute SmsBody detail,
			@RequestParam(required=true, defaultValue="1") Integer pageNumber,
			@RequestParam(required=true, defaultValue="10") Integer pageSize){
		logger.info("短信发送管理querySmsSendList>>>>>>>>>>>>>>>>>>>>");
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		if("-1".equals(detail.getProjId())){
			detail.setProjId("CSV导入");
		}
//		if("-1".equals(detail.getProdCode())){
//			detail.setProdCode("CSV导入");
//		}
		List<SmsBody> smsList = smsService.querySmsBodyList(detail);
		PageInfo<SmsBody> pageInfo = new PageInfo<SmsBody>(smsList);
		map.put("total", pageInfo.getTotal());
		map.put("rows", smsList);
		return map;
	}
	/**
	 * 短信发送明细查询
	 * @return
	 */
	@RequestMapping(value="querySmsSendDetailById",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> querySmsSendDetailById(Integer id){
		logger.info("短信发送明细查询querySmsSendDetailList>>>>>>>>>>>>>>>>>>>>");
		Map<String, Object> map = new HashMap<String, Object>();
		SmsSendDetail smsdetail = smsService.querySmsSendDetailById(id);
		String sendDateStr = DateUtils.formatDate(smsdetail.getSendDate(), "yyyy-MM-dd HH:mm:ss");
		smsdetail.setSendDateStr(sendDateStr);
		List<SmsSendDetail> smsList = new ArrayList<>();
		smsList.add(smsdetail);
		PageInfo<SmsSendDetail> pageInfo = new PageInfo<SmsSendDetail>(smsList);
		map.put("total", pageInfo.getTotal());
		map.put("rows", smsList);
		return map;
	}
	
	/**
	 * 查询短信模板列表信息
	 * @param pageNumber
	 * @param pageSize
	 * @param createTime
	 * @return
	 */
	@RequestMapping(value="queryModuleList")
	@ResponseBody
	public Map<String,Object> queryModuleList(
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam String createTime,@RequestParam String smsModuleType){
		
		logger.info("查询短信模板列表,查询条件为登记时间："+createTime+">>>>>>>>>>>>>>>>>>>");
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmsModule> moduleList = smsService.queryModuleList(createTime,smsModuleType);
		PageInfo<SmsModule> pageInfo = new PageInfo<SmsModule>(moduleList);
		map.put("total", pageInfo.getTotal());
		map.put("rows", moduleList);
		return map;
	}
	
	/**
	 * 更新模板的启用/停用状态
	 * @return
	 */
	@RequestMapping(value="updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(Integer moduleId,String status){
		Map<String, Object> map = new HashMap<>();
		try {
			smsService.updateStatus(moduleId, status);
			map.put("status", StatusEnum.SUCCESS.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", StatusEnum.FAIL.getStatus());
			map.put("msg", "数据库执行失败");
		}
		return map;
	}
	
	@RequestMapping(value="queryOrgAndProject")
	@ResponseBody
	public List<RhzxOrg> queryOrgAndProject(){
		List<RhzxOrg> list = publicQueryService.queryAllOrgAndProject();
		return list;
	}
	
	
	/**
	 * 新增保存短信模板
	 * @return
	 */
	@RequestMapping(value="saveSmsModule")
	@ResponseBody
	public Map<String, Object> addSmsModule(HttpServletRequest request, @ModelAttribute SmsModule smsModule, @RequestBody List<SmsSettingRule> smsSettingRuleList){
		Map<String, Object> map = new HashMap<>();
		try {
			LoginUser user = SessionUtil.getUserFromSession(request.getSession());
			smsModule.setOperator(user.getUsername());
			smsModule.setOperatorId(user.getId());
			smsModule.setStatus("0");
			smsService.addSmsModule(smsModule, smsSettingRuleList);
			map.put("status", StatusEnum.SUCCESS.getStatus());
			map.put("msg", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", StatusEnum.FAIL.getStatus());
			map.put("msg", "数据库执行失败");
		}
		return map;
	}
	
	/**
	 * 导出短信详情
	 * @param detail
	 */
	@RequestMapping(value="exportSmsSendDetail")
	@ResponseBody
	public void exportSmsSendDetail(@ModelAttribute SmsSendDetail detail,HttpServletResponse response){
		logger.info("导出短信发送详情>>>>>>>>>>>>>>>>>>>>>>>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 时间戳格式
		List<Object> titleList = ExcelTitle.smsSendDetailTitleAndField();
		Map<String,List<?>> dataMap = new LinkedHashMap<String,List<?>>();
		List<SmsSendDetail> sendDetailList = smsService.querySmsSendDetailList(detail);
		List<Object> dataList = new ArrayList<>();
		dataMap.put("短信发送详细信息", sendDetailList);
		dataList.add(dataMap);
		
		SXSSFWorkbook workbook = ExcelUtil.assemblyExcel(titleList,dataList);
		String time = sdf.format(new Date());
		ExcelUtil.exportExcel2007("短信发送相信信息_" + time, workbook, response);
	}
	
	/**
	 * 查询台账统计信息
	 * @return
	 */
	@RequestMapping(value="querySmsSendStatistical")
	@ResponseBody
	public Map<String,Object> querySmsSendStatistical(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,@ModelAttribute SmsSendDetail detail){
		logger.info("查询短信发送统计台账>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		PageHelper.startPage(pageNumber, pageSize);
		if("-1".equals(detail.getProjId())){
			detail.setProjId("CSV导入");
		}
		if("-1".equals(detail.getProdCode())){
			detail.setProdCode("CSV导入");
		}
		if (detail.getEndMonthStr().length()>0) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Date date1=null;
			try {
				date1 = sdf.parse(detail.getEndMonthStr());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cal.setTime(date1);
			
			int month=cal.get(Calendar.MONTH)+1; 
			cal.add(Calendar.MONTH, 1); 
			//Date date = cal.getTime(); //结果 


			//cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)+1);
			Date date=cal.getTime();
			System.out.println(sdf.format(date));
			
			detail.setEndMonthStr(sdf.format(date));
		}else {
			detail.setEndMonthStr(detail.getEndMonthStr());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmsSendDetail> list = smsService.querySmsSendStatistical(detail);
		PageInfo<SmsSendDetail> pageInfo = new PageInfo<SmsSendDetail>(list);
		map.put("total", pageInfo.getTotal());
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 导出短信统计台账
	 * @param detail
	 * @param response
	 */
	@RequestMapping(value="exportSmsSendStatistical")
	@ResponseBody
	public void exportSmsSendStatistical(@ModelAttribute SmsSendDetail detail,HttpServletResponse response){
		logger.info("导出短信统计台账>>>>>>>>>>>>>>>>>>>>>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 时间戳格式
		List<Object> titleList = ExcelTitle.smsSendStatisticalTitleAndField();
		Map<String,List<?>> dataMap = new LinkedHashMap<String,List<?>>();
		//根据条件查询短信统计台账信息
		
		List<SmsSendDetail> sendStatiscalList = smsService.querySmsSendStatistical(detail);
		List<Object> dataList = new ArrayList<>();
		dataMap.put("短信发送统计台账", sendStatiscalList);
		dataList.add(dataMap);
		
		SXSSFWorkbook workbook = ExcelUtil.assemblyExcel(titleList,dataList);
		String time = sdf.format(new Date());
		ExcelUtil.exportExcel2007("短信发送统计台账_" + time, workbook, response);
		
	}
	/**
	 * 手工短信发送执行 
	 * @param detail
	 * @param smsPrepareSendRecord
	 * @return
	 */
	@RequestMapping(value="manulSendSms")
	@ResponseBody
	public Map<String, Object> manulSendSms(@ModelAttribute SmsSendDetail detail,@ModelAttribute SmsPrepareSendRecord smsPrepareSendRecord){
		logger.info("手工触发短信发送执行>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map<String, Object> map = new HashMap<>();
		//手机号检验
		if(PubMethod.isEmpty(detail.getPhone())){
			map.put("smsCode", "-1");
			return map;
		}else{
			String smsPhoneReg = Constant.SMS_PHONE_REG;
			String phone = detail.getPhone();
			if(!phone.matches(smsPhoneReg)){
				map.put("smsCode", "-1");
				return map;
			}
		}
		//发送前保存将要发送的短息信息
		smsService.saveSmsPrepareSendRecord(smsPrepareSendRecord);
		String smsCode = "";
		String smsCodeDetailDescription = "";
		try {
			//调用发送短信的方法
			String getSmsModuleType = "";
			if (detail.getSmsModuleTypeName().equals("普通类型") ) {
				getSmsModuleType = "1";
			}else if(detail.getSmsModuleTypeName().equals("模板类型") ){
				getSmsModuleType = "2";
			}
			
			smsCode = SmsUtil.SmsSend(detail.getPhone(), detail.getSmsSendContent(),getSmsModuleType);
			logger.info("手动发送短信,返回状态码为：code="+smsCode);
			if(!PubMethod.isEmpty(smsCode)){
				detail.setSmsCode(smsCode);
				SmsCode smsCodeDetail = smsService.querySmsCodeDetailByCode(smsCode);
				detail.setSmsCodeDetail(smsCodeDetail.getSmsCodeDetail());
				smsCodeDetailDescription = smsCodeDetail.getSmsCodeDetail();
			}
		} catch (Exception e) {
			logger.error("手动发送短信异常，异常信息为："+e.getMessage());
			detail.setSmsCode("-1");
			smsCodeDetailDescription = "系统异常,发送失败";
		}
		
		//保存短信发送详细信息
		List<SmsSendDetail> smsSendDetailList = new ArrayList<>();
		smsSendDetailList.add(detail);
		smsService.saveSmsSendDetail(smsSendDetailList);
		map.put("smsCode", smsCode);
		map.put("smsCodeDetail", smsCodeDetailDescription);
		return map;
	}
	
	/**
	 * 短信平台回调修改短信详情的 smsCode
	 * @param ssds
	 * @return
	 */
	@RequestMapping("callBack")
	@ResponseBody
	public boolean updateSmssenddetailOfSmscodeAndSendstatusByUuid(@RequestBody List<SmsSendDetail> ssds){
		try {
			logger.info("短信平台回调.........................................");
			smsService.updateSmssenddetailOfSmscodeAndSendstatusByUuid(ssds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping(value="updateSms", method=RequestMethod.POST)
	@ResponseBody
	public Map<String ,String> updateSms(HttpServletResponse response,HttpServletRequest ret){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String ,String> map = new HashMap<String, String>();
		String account = ret.getParameter("account");//业务号
		String sms = ret.getParameter("sms");
		String createTime = ret.getParameter("createTime");//时间
		long lt = new Long(createTime);
		Date date = new Date(lt);
		String createtime = simpleDateFormat.format(date);
		String credNo = ret.getParameter("credNo");//证件号
		
		Boolean boolean1 = smsSendDetailMapper.upDateSms(sms, account, createtime, credNo);
		if (boolean1 == true) {
			map.put("sussion", "true");
			map.put("msg", "更新成功");
		}else {
			map.put("sussion", "false");
			map.put("msg", "更新失败");
		}
		
		return map;
	} 
}
