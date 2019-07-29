package com.fotic.management.configuration.controller;

import java.util.ArrayList;
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
import com.fotic.common.util.FastJsonUtils;
import com.fotic.common.util.PropertiesUtil;
import com.fotic.common.util.PubMethod;
import com.fotic.common.util.SessionUtil;
import com.fotic.management.configuration.entity.NotSumitConfg;
import com.fotic.management.configuration.entity.NotSumitConfgInfo;
import com.fotic.management.configuration.service.INotSumitConfgService;
import com.fotic.management.customer.service.ISubmitAddressService;
import com.fotic.management.customer.service.ISubmitEmpService;
import com.fotic.management.customer.service.ISubmtPersonService;
import com.fotic.management.customer.service.ISubmtSpetradService;
import com.fotic.management.trade.service.SubmitTradeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @Title: DataReportCfgController.java
 * @Package com.fotic.management.configuration.controller
 * @Description: 不报配置表查询保存，不报信息view查询
 * @author 王明月
 * @date 2017年7月11日 下午2:10:46
 */
@Controller
@RequestMapping("datareportcfg")
public class DataReportCfgController extends BaseController {
	@Autowired
	private INotSumitConfgService notSumitConfgService;
	@Autowired
	private ISubmtSpetradService submtSpetradService;
	@Autowired
	private ISubmitEmpService submitEmpService;
	@Autowired
	private ISubmtPersonService submtPersonService;
	@Autowired
	private ISubmitAddressService submitAddressService;
	@Autowired
	private SubmitTradeService submitTradeService;

	/**
	 * 进入不报配置信息主页
	 */
	@RequestMapping(value = { "index" })
	public String index() {
		return "/datareportcfg/index";
	}

	/**
	 * 新增不报页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "toAdd" })
	public String toAdd() {
		return "/datareportcfg/add";
	}

	/**
	 * 不报配置 查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param project
	 * @param prod
	 * @param org
	 * @param conno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "searchListByPage" })
	public Map<String, Object> searchListByPage(
			@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true, defaultValue = "") String project,
			@RequestParam(required = true, defaultValue = "") String prod,
			@RequestParam(required = true, defaultValue = "") String org,
			@RequestParam(required = true, defaultValue = "") String conno) {
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		List<NotSumitConfg> findAll = notSumitConfgService.findAllValid(
				project, prod, org, conno);
		PageInfo<NotSumitConfg> p = new PageInfo<NotSumitConfg>(findAll);
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}

	/**
	 * 不报信息查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param project
	 * @param prod
	 * @param org
	 * @param conno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "searchListByPage4Info" })
	public Map<String, Object> searchListByPage4Info(
			@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true, defaultValue = "") String project,
			@RequestParam(required = true, defaultValue = "") String prod,
			@RequestParam(required = true, defaultValue = "") String org,
			@RequestParam(required = true, defaultValue = "") String conno,
			@RequestParam(required = true, defaultValue = "") String dataSrc) {
		PageHelper.startPage(pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		List<NotSumitConfgInfo> findList = notSumitConfgService.findList(
				project, prod, org, conno, dataSrc);
		PageInfo<NotSumitConfgInfo> p = new PageInfo<NotSumitConfgInfo>(
				findList);
		map.put("total", p.getTotal());
		map.put("rows", findList);
		return map;
	}

	/**
	 * 批量 更新为 重新上报状态
	 * 
	 * @param strJson
	 *            前端返回的json串
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "tableEdit" })
	public Map<String, Object> tableEdit(
			@RequestParam(required = true, defaultValue = "") String strJson,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		int errCount = 0;
		int i = 0;
		if (!PubMethod.isEmpty(strJson)) {
			List<NotSumitConfg> listentity = FastJsonUtils.toList(strJson,
					NotSumitConfg.class);
			for (NotSumitConfg entity : listentity) {
				entity.setdUserCode(SessionUtil.getUserFromSession(
						request.getSession()).getUsercode());
				entity.setInvldDate(new Date());
				entity.setIfValid("1");// 失效状态
				i = this.notSumitConfgService.updateNotSumitConfg(entity);
				if (i == 1) {
					msg += " 类型：" + entity.getRuleType() + " 名称："
							+ entity.getValue_name();
				} else {
					errCount++;
				}
			}
			if (errCount > 0) {
				map.put("success", "false");
				map.put("msg", msg);
			} else {
				map.put("success", "true");
				map.put("msg", msg);
			}

		}
		return map;
	}

	/**
	 * 保存 不报信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "save" })
	public Map<String, String> datareportcfgsave(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		int i = 0;
		NotSumitConfg entity = new NotSumitConfg();
		entity.setIfValid("0");// 0是有效
		entity.setiUserCode(SessionUtil
				.getUserFromSession(request.getSession()).getUsercode());// 操作人
		entity.setValidDate(new Date());// 生效日期
		String proj = request.getParameter("project");
		String projname = request.getParameter("projectname");
		String prod = request.getParameter("prod");

		String prodname = request.getParameter("prodname");
		String org = request.getParameter("org");
		String orgname = request.getParameter("orgname");
		String conno = request.getParameter("conno");
		String dataSrc = request.getParameter("dataSrc");// 数据来源 1 信贷 2 CSV
		if (PubMethod.isEmpty(proj) && PubMethod.isEmpty(prod)
				&& PubMethod.isEmpty(org) && PubMethod.isEmpty(conno)) {
			map.put("success", "false");
			map.put("msg", "不能为空！");
			return map;
		}

		/*String[] strconno = conno.split(",");
		List<String> connolist = new ArrayList<String>();
		for (int j = 0; j < strconno.length; j++) {
			connolist.add(strconno[j]);
		}*/

		// 查询条件
		NotSumitConfg findCondition = new NotSumitConfg();

		if (!PubMethod.isEmpty(proj)) {
			List<String> projlist = new ArrayList<String>();
			String[] strproj = projname.split(",");
			for (int j = 0; j < strproj.length; j++) {
				projlist.add(strproj[j]);
			}
			for (int k = 0; k < projlist.size(); k++) {

				entity.setRuleType("1");// 项目
				findCondition.setRuleType("1");
				findCondition.setValue(projlist.get(k).split("&")[1]);
				entity.setValue(projlist.get(k).split("&")[0]);
				entity.setValue_name(projlist.get(k).split("&")[1]);
				entity.setDataSrc(dataSrc);
				NotSumitConfg one = this.notSumitConfgService
						.findOne(findCondition);
				if (!PubMethod.isEmpty(one)) {
					map.put("success", "false");
					map.put("msg", "信托项目：" + projname + " 在数据库中存在记录！不能重复");
					return map;
				} else {
					i = notSumitConfgService.insertNotSumitConfg(entity);
					if (i != 1) {
						map.put("success", "false");
						map.put("msg", "信托项目：" + projname + " 保存异常！");
						return map;
					}
				}
			}
		}
		if (!PubMethod.isEmpty(prod)) {
			String[] strprod = prod.split(",");
			List<String> prodlist = new ArrayList<String>();
			for (int j = 0; j < strprod.length; j++) {
				prodlist.add(strprod[j]);
			}
			for (int k = 0; k < prodlist.size(); k++) {

				entity.setRuleType("2");// 产品
				entity.setValue(prodlist.get(k).split("&")[0]);
				entity.setValue_name(prodlist.get(k).split("&")[1]);
				entity.setDataSrc(dataSrc);
				findCondition.setRuleType("2");
				findCondition.setValue(prodlist.get(k).split("&")[1]);
				NotSumitConfg one = this.notSumitConfgService
						.findOne(findCondition);
				if (!PubMethod.isEmpty(one)) {
					map.put("success", "false");
					map.put("msg", "信托产品：" + prodname + " 在数据库中存在记录！不能重复");
					return map;
				} else {
					i = notSumitConfgService.insertNotSumitConfg(entity);
					if (i != 1) {
						map.put("success", "false");
						map.put("msg", "信托产品：" + prodname + " 保存异常！");
						return map;
					}
				}
			}
		}

		/*
		 * //String toooString = list.get(k).split("-")[0]; map2.put("org",
		 * list.get(k).split("-")[0]); map2.put("orgname",
		 * list.get(k).split("-")[1]);
		 */
		if (!PubMethod.isEmpty(org)) {
			List<String> orglist = new ArrayList<String>();
			String[] strOrg = orgname.split(",");

			for (int j = 0; j < strOrg.length; j++) {
				orglist.add(strOrg[j]);
			}
			for (int k = 0; k < orglist.size(); k++) {

				entity.setRuleType("3");// 机构
				entity.setValue(orglist.get(k).split("-")[1]);
				entity.setValue_name(orglist.get(k).split("-")[0]);
				entity.setDataSrc(dataSrc);
				findCondition.setRuleType("3");
				findCondition.setValue(orglist.get(k).split("-")[1]);
				NotSumitConfg one = this.notSumitConfgService
						.findOne(findCondition);
				if (!PubMethod.isEmpty(one)) {
					map.put("success", "false");
					map.put("msg", "合作机构：" + orgname + " 在数据库中存在记录！不能重复");
					return map;
				} else {
					i = notSumitConfgService.insertNotSumitConfg(entity);
					if (i != 1) {
						map.put("success", "false");
						map.put("msg", "合作机构：" + orgname + " 保存异常！");
						return map;
					}
				}
			}
		}

		if (!PubMethod.isEmpty(conno)) {
			String[] split = conno.split(",");
			String msg = "";
			int errCount = 0;
			for (String c : split) {
				entity.setRuleType("4");// 合同
				entity.setValue(c);
				entity.setValue_name(c);
				entity.setDataSrc(dataSrc);
				findCondition.setRuleType("4");
				findCondition.setValue(c);
				NotSumitConfg one = this.notSumitConfgService
						.findOne(findCondition);
				if (!PubMethod.isEmpty(one)) {
					msg += " 合同号：" + conno + " 在数据库中存在记录！不能重复";
					errCount++;
				} else {
					i = notSumitConfgService.insertNotSumitConfg(entity);
					if (i != 1) {
						msg += " 合同号：" + conno + " 保存异常！";
						errCount++;
					}
				}
			}
			if (errCount > 0) {
				map.put("success", "false");
				map.put("msg", msg);
				return map;
			}
		}

		map.put("success", "true");
		return map;
	}

	/**
	 * 不报展示信息控制器
	 */
	@RequestMapping(value = { "info" })
	public String info(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "/datareportcfg/info";
	}

	/**
	 * 重报
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param datatype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "renewReport" })
	public Map<String, Object> renewReport(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(required = true, defaultValue = "1") String datatype) {
		String v = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			v = super.procedureInvokeRetString(
					PropertiesUtil.get("SP_RHZX_PER_MAIN"), datatype);
		} catch (Exception e) {
			map.put("error", "true");
			map.put("msg", e.getMessage());
			e.printStackTrace();
			return map;
		}

		if (PubMethod.isEmpty(v)) {
			map.put("success", "false");
			map.put("msg", v);
		} else {
			map.put("success", "true");
			map.put("msg", v);
		}
		return map;
	}

	/**
	 * 机构导入信息管理_新增不报
	 * 
	 * @param conNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "notSubMitDatas")
	public Map<String, Object> notSubMitDatas(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<>();
		String account = request.getParameter("account");// 业务编号
		String dataSrc = request.getParameter("dataSrc");// 数据来源 1 信贷 2 CSV
		String certNo = request.getParameter("certNo");// 证件号
		String certType = request.getParameter("certType");// 证件号
		if (PubMethod.isEmpty(account)) {
			returnMap.put("success", "false");
			returnMap.put("msg", "不能为空！");
			return returnMap;
		}

		// 拼接account
		String[] splits = account.split(",");
		StringBuffer sb = new StringBuffer();
		for (String string : splits) {
			sb.append("'");
			sb.append(string);
			sb.append("'");
			sb.append(",");
		}
		String accounts = sb.toString().substring(0,
				sb.toString().trim().length() - 1);

		List<NotSumitConfg> list = new ArrayList<>();
		if (!PubMethod.isEmpty(account)) {
			String[] split = account.split(",");
			for (String c : split) {
				NotSumitConfg entity = new NotSumitConfg();
				entity.setRuleType("4");// 合同
				entity.setValue(c);
				entity.setValue_name(c);
				entity.setiUserCode(SessionUtil.getUserFromSession(
						request.getSession()).getUsercode());// 操作人
				entity.setIfValid("0");// 0是有效
				entity.setValidDate(new Date());// 生效日期
				entity.setDataSrc(dataSrc);
				list.add(entity);
			}

		}

		// 批量插入不报信息
		notSumitConfgService.insertNotsubMitDatas(list);
		// 根据account、dataSrc,批量删除特殊交易信息
		submtSpetradService.deleteByAccountAndDataSrc(accounts, dataSrc);// 交易信息
		// 根据证件类型、证件号、来源,批量删除数据
		submitEmpService.deleteByCertTypeAndCertNo(certType, certNo, dataSrc);
		submtPersonService.deleteByCertTypeAndCertNo(certType, certNo, dataSrc);
		submitAddressService.deleteByCertTypeAndCertNo(certType, certNo,
				dataSrc);
		// 根据account、dataSrc,批量删除交易信息
		submitTradeService.deleteByAccountAndDataSrc(accounts, dataSrc);
		returnMap.put("success", "true");
		return returnMap;
	}

}