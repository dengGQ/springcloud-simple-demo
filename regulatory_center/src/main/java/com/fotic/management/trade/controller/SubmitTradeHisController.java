package com.fotic.management.trade.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fotic.common.util.StringUtils;
import com.fotic.management.trade.entity.SubmitTradeHis;
import com.fotic.management.trade.service.SubmitTradeHisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
* @Title: SubmitTradeHisController.java 
* @Package com.fotic.management.trade.controller 
* @Description: 合同交易 
* @author 王明月   
* @date 2017年7月3日 上午9:24:04
 */
@Controller
@RequestMapping("trade")
public class SubmitTradeHisController {
	@Autowired
	private SubmitTradeHisService submitTradeHisService;
	/**
	 * 合同交易_历史_跳转到列表页
	 */
	@RequestMapping(value = { "tradehis" })
	public String tradehis(HttpServletRequest request, HttpServletResponse response) {
		return "/trade/submittradehis";
	}
	
	@ResponseBody
	@RequestMapping(value = { "searchListByPage" })
	public Map<String, Object> searchListByPage(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam(required=true,defaultValue="") String certno,
	        @RequestParam(required=true,defaultValue="") String name,
	        @RequestParam(required=true,defaultValue="") String account
			) {
		SubmitTradeHis entity = new SubmitTradeHis();
		if(StringUtils.isNotBlank(name)){
			entity.setName(name);
		}
		if(StringUtils.isNotBlank(certno)){
			entity.setCertno(certno);
		}
		if(StringUtils.isNotBlank(account)){
			entity.setAccount(account);
		}
		PageHelper.startPage(pageNumber, pageSize);	
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubmitTradeHis> findAll = submitTradeHisService.findAll(entity);
		PageInfo<SubmitTradeHis> p=new PageInfo<SubmitTradeHis>(findAll);
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}
}
