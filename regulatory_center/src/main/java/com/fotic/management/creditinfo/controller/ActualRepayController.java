package com.fotic.management.creditinfo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.PubMethod;
import com.fotic.management.creditinfo.entity.ActualRepay;
import com.fotic.management.creditinfo.service.IActualRepayService;

/**
 * 小微实际还款信息管理
 * 
 * @author Yaxi
 *
 */
@Controller
@RequestMapping("actualrepay")
public class ActualRepayController {
	private static Logger logger = LoggerFactory.getLogger(ActualRepayController.class);
	@Autowired
	private IActualRepayService actualRepayService;

	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "editData" })
	@ResponseBody
	public Map<String, Object> editData(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String json = request.getParameter("mydata");
			JSONArray jsonArr =(JSONArray) JSONArray.parse(json);
			for(int i=0;i<jsonArr.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				String actualRepayDate = (String) jsonArr.getJSONObject(i).get("actualRepayDate");
				String actualPrnpl = (String) jsonArr.getJSONObject(i).get("actualPrnpl");
				String actualIntes = (String) jsonArr.getJSONObject(i).get("actualIntes");
				String intesPnlty = (String) jsonArr.getJSONObject(i).get("intesPnlty");
				String conNo = (String) jsonArr.getJSONObject(i).get("conNo");
				String actualId = (String) jsonArr.getJSONObject(i).get("actualId");
				map.put("conNo", conNo);
				map.put("actualId", actualId);
				//根据合同号、id查询实际还款信息
				List<ActualRepay> list = actualRepayService.queryListByParams(map);
				if (!PubMethod.isEmpty(list)) {
					for (ActualRepay entity : list) {
						if(!PubMethod.isEmpty(entity)){
							BigDecimal actualPrnplBig = new BigDecimal(actualPrnpl);
							BigDecimal actualIntesBig = new BigDecimal(actualIntes);
							BigDecimal intesPnltyBig = new BigDecimal(intesPnlty);
							Date date = DateUtils.getDateFormat(actualRepayDate);
							BigDecimal actualAmtBig = actualPrnplBig.add(actualIntesBig).add(intesPnltyBig);
							entity.setActualAmt(actualAmtBig);//实还金额
							entity.setActualPrnpl(actualPrnplBig);//实还本金
							entity.setActualIntes(actualIntesBig);//实还利息
							entity.setIntesPnlty(intesPnltyBig);//罚息金额
							entity.setActualRepayDate(date);
							entity.setModifFlag("2");//更新修改标识 1正常 2已修改
							entity.setModifDate(DateUtils.getCurrentDate());//更新修改日期
							actualRepayService.update(entity);//更新实际还款金额、实际还款日期
						}else{
							result.put("success", "false");
							result.put("msg", "没有相关数据,修改失败" );
							return result;
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			result.put("success", "false");
			result.put("msg", "修改失败" );
			return result;
		}
		result.put("success", "true");
		return result;
	}

}