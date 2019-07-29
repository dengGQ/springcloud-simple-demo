package com.fotic.management.customer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.common.util.StringUtils;
import com.fotic.management.customer.entity.Customer;
import com.fotic.management.customer.entity.SubmitAddressHis;
import com.fotic.management.customer.entity.SubmitEmpHis;
import com.fotic.management.customer.entity.SubmtPersonHis;
import com.fotic.management.customer.entity.SubmtSpetradfHis;
import com.fotic.management.customer.service.ICustomerService;
import com.fotic.management.customer.service.ISubmitAddressHisService;
import com.fotic.management.customer.service.ISubmtPersonHisService;
import com.fotic.management.customer.service.ISubmtSpetradfHisService;
import com.fotic.management.customer.service.SubmitEmpHisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("customer")
public class CustomerController {
	/**
	 * 客户职位信息_历史
	 */
	@Autowired
	private SubmitEmpHisService submitEmpHisService;
	
	/**
	 * 客户家庭住址信息_历史Service
	 */
	@Autowired
	private ISubmitAddressHisService submitAddressHisService;
	
	/**
	 * 特殊交易信息_历史Service
	 */
	@Autowired
	private ISubmtSpetradfHisService submtSpetradfHisService;
	
	/**
	 * 客户个人信息_历史Service
	 */
	@Autowired
	private ISubmtPersonHisService submtPersonHisService;
	
	/**
	 * 客户基本信息管理Service
	 */
	@Autowired
	private ICustomerService customerService; 
	
	/**
	 * 客户个人信息_跳转到列表页
	 */
	@RequestMapping(value = { "index" })
	public String index() {
		return "/customer/index";
	}
	
	/**
	 * 客户信息管理_跳转到列表页
	 */
	@RequestMapping(value = { "custom" })
	public String custom(){
		return "/customer/custom";
	}
	/**
	 * 客户职位信息_历史 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param certno 证件号
	 * @param name   客户姓名
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "empHisSearchListByPage" })
	public Map<String, Object> empHisSearchListByPage(
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam(required=true,defaultValue="") String certno,
	        @RequestParam(required=true,defaultValue="") String name,
	        @RequestParam(required=true,defaultValue="") String dataStatus
			) {
		SubmitEmpHis submitEmpHis = new SubmitEmpHis();
		if(StringUtils.isNotBlank(name)){
			submitEmpHis.setName(name);
		}
		if(StringUtils.isNotBlank(certno)){
			submitEmpHis.setCertno(certno);
		}
		if(StringUtils.isNotBlank(dataStatus)){
			submitEmpHis.setDataStatus(dataStatus);
		}
		PageHelper.startPage(pageNumber, pageSize);	
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubmitEmpHis> findAll = submitEmpHisService.findList(submitEmpHis);
		PageInfo<SubmitEmpHis> p=new PageInfo<SubmitEmpHis>(findAll);
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
	
	
	/**
	 * 客户家庭住址信息_历史 分页查询
	 * @param name	姓名
	 * @param certNo	证件号码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "addrHisPage" })
	public Map<String, Object> addrHisPage(
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam(required=true,defaultValue="") String name,
	        @RequestParam(required=true,defaultValue="") String certNo,
	        @RequestParam(required=true,defaultValue="") String dataStatus
			) {
		SubmitAddressHis submitAddressHis = new SubmitAddressHis();
		if(StringUtils.isNotBlank(name)){
			submitAddressHis.setName(name);
		}
		
		if(StringUtils.isNotBlank(certNo)){
			submitAddressHis.setCertNo(certNo);
		}
		
		if(StringUtils.isNotBlank(dataStatus)){
			submitAddressHis.setDataStatus(dataStatus);
		}
		PageHelper.startPage(pageNumber, pageSize);	
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubmitAddressHis> findAll = submitAddressHisService.findList(submitAddressHis);
		PageInfo<SubmitAddressHis> p=new PageInfo<SubmitAddressHis>(findAll);
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
	
	/**
	 * 特殊交易信息_历史 分页查询
	 * @param account	业务号
	 * @param tradeid	交易ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "spetradHisPage" })
	public Map<String, Object> spetradHisPage(
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam(required=true,defaultValue="") String account,
	        @RequestParam(required=true,defaultValue="") String tradeid,
	        @RequestParam(required=true,defaultValue="") String dataStatus
			) {
		SubmtSpetradfHis submtSpetradfHis = new SubmtSpetradfHis();
		if(StringUtils.isNotBlank(account)){
			submtSpetradfHis.setAccount(account);
		}
		
		if(StringUtils.isNotBlank(tradeid)){
			submtSpetradfHis.setTradeid(tradeid);
		}
		
		if(StringUtils.isNotBlank(dataStatus)){
			submtSpetradfHis.setDataStatus(dataStatus);
		}
		PageHelper.startPage(pageNumber, pageSize);	
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubmtSpetradfHis> findAll = submtSpetradfHisService.findList(submtSpetradfHis);
		PageInfo<SubmtSpetradfHis> p=new PageInfo<SubmtSpetradfHis>(findAll);
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
	
	/**
	 * 客户个人基本信息_历史 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param certNo 证件号码
	 * @param name   姓名
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submtPersonHis")
	public Map<String, Object> submtPersonHis(
			@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required=true,defaultValue="") String certNo, 
			@RequestParam(required=true,defaultValue="") String name,
			@RequestParam(required=true,defaultValue="") String dataStatus) {
		SubmtPersonHis submtPersonHis = new SubmtPersonHis();
		if(StringUtils.isNotBlank(name)){
			submtPersonHis.setName(name);
		}
		
		if(StringUtils.isNotBlank(certNo)){
			submtPersonHis.setCertno(certNo);
		}
		
		if(StringUtils.isNotBlank(dataStatus)){
			submtPersonHis.setData_status(dataStatus);
		}
		
		PageHelper.startPage(pageNumber, pageSize);
		List<SubmtPersonHis> list =submtPersonHisService.findList(submtPersonHis);
		PageInfo<SubmtPersonHis> p = new PageInfo<SubmtPersonHis>(list);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", list);
		map.put("total", p.getTotal());
		return map;
	}
	
	/**
	 * 客户信息_查询
	 * @param pageNumber
	 * @param pageSize
	 * @param custName 客户姓名
	 * @param credCode 证件号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value= {"customInfoPage"})
	public Map<String,Object> customInfoPage(
			@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String custName,
			@RequestParam(required = false) String credCode){
		
		PageHelper.startPage(pageNumber, pageSize);
		List<Customer> findAll = customerService.findList(custName, credCode);
		PageInfo<Customer> p = new PageInfo<Customer>(findAll);
		Map<String,Object> map = new HashMap<>();
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
	
	/**
	 * 客户详细信息_查看
	 * @param pageNumber
	 * @param pageSize
	 * @param credCode
	 * @return
	 */
	@RequestMapping(value= {"customDetailsPage"})
	public String customDetailsPage(Model model,@RequestParam(required = false) String credCode){
		List<Customer> findAll = customerService.findDetailsList(credCode);
		model.addAttribute("Customer",findAll);
		return "/customer/customDetails";
	}
	
}
