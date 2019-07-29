package com.fotic.management.trade.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.management.trade.entity.Contract;
import com.fotic.management.trade.entity.RhzxRebuy;
import com.fotic.management.trade.entity.SubmitTrade;
import com.fotic.management.trade.service.ContractService;
import com.fotic.management.trade.service.SubmitTradeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 顾亚玺
 * @date 创建时间：2017年7月18日 上午9:59:20
 * @version 1.0 * @parameter 交易信息管理
 */
@Controller
@RequestMapping("trademanage")
public class TradeController {

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private SubmitTradeService submitTradeService;

	/**
	 * 合同信息_跳转列表页
	 * 
	 * @return
	 */
	@RequestMapping(value = { "index" })
	private String contractInfoList() {
		return "/trade/contractInfoList";
	}

	
	/**
	 *  * 交易信息_查询
	 * @param pageNum
	 * @param pageSize
	 * @param certno     证件号码
	 * @param dataStatus 校验结果
	 * @param dataSrc    数据来源
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "findList" })
	private Map<String, Object> findList(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String certno,
			@RequestParam(required = false) String dataStatus,
			@RequestParam(required = false) String dataSrc) {
		PageHelper.startPage(pageNum, pageSize);
		List<SubmitTrade> findAll =submitTradeService.findList(certno,dataStatus,dataSrc);
		PageInfo<SubmitTrade> p = new PageInfo<SubmitTrade>(findAll);
		Map<String, Object> map = new HashMap<>();
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
	
	/**
	 * 合同信息_查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param conNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "findContractPage" })
	private Map<String, Object> findContractPage(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String conNo,@RequestParam(required = false) String iouNo,
			@RequestParam(required = false) String dataSrc) {
		PageHelper.startPage(pageNum, pageSize);
		List<Contract> findAll = contractService.findContractList(conNo,iouNo,dataSrc);
		PageInfo<Contract> p = new PageInfo<Contract>(findAll);
		Map<String, Object> map = new HashMap<>();
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}

	/**
	 * 根据合同号查看合同详细信息_跳转页面
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param conNo
	 * @return
	 */
	@RequestMapping(value = { "findDetailsContract" })
	private String findDetailsContract(Model model, @RequestParam(required = false) String conNo) {
		List<Contract> list = contractService.findDetailsContract(conNo);
		model.addAttribute("conNo", conNo);
		model.addAttribute("list", list);
		return "/trade/contractDetails";
	}

	/**
	 * 特殊交易信息_查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "findRebuyPage" })
	private Map<String, Object> findRebuyPage(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String conNo) {
		PageHelper.startPage(pageNum, pageSize);
		List<RhzxRebuy> findAll = contractService.findRebuyList(conNo);
		PageInfo<RhzxRebuy> p = new PageInfo<RhzxRebuy>(findAll);
		Map<String, Object> map = new HashMap<>();
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
}
