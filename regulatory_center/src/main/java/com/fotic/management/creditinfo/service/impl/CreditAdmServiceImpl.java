package com.fotic.management.creditinfo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.management.creditinfo.dao.RhzxSubmtPerJDTradeMapper;
import com.fotic.management.creditinfo.dao.RhzxSubmtPerTradeMapper;
import com.fotic.management.creditinfo.entity.RhzxSubmtPerJDTrade;
import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.creditinfo.service.ICreditAdmService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CreditAdmServiceImpl implements ICreditAdmService {
	@Autowired
	private RhzxSubmtPerTradeMapper rhzxSubmtPerTradeMapper;
	@Autowired
	private RhzxSubmtPerJDTradeMapper rhzxSubmtPerJDTradeMapper;
	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public List<RhzxSubmtPerTrade> findAll() {
		return rhzxSubmtPerTradeMapper.selectAll();
	}

	@Override
	public List<RhzxSubmtPerTrade> findList(String projectId, String coOrgCode, String IOUNo, String credNo,
			String checkResult, String startDate, String endDate,String dataSrc,String curtermspastdue) {
		return rhzxSubmtPerTradeMapper.findList(projectId, coOrgCode, IOUNo, credNo, checkResult, startDate, endDate,dataSrc,curtermspastdue);
	}

	@Override
	public void setEditInfo(HttpServletRequest request, List<Map<String, Object>> editInfo) {
		if (!editInfo.isEmpty()) {
			request.getSession().setAttribute("listInfo", editInfo);
		}
	}

	@Override
	public List<RhzxSubmtPerTrade> findCsvDatas(String dataSouce) {
		Example example = new Example(RhzxSubmtPerTrade.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dataSrc", dataSouce);
		return rhzxSubmtPerTradeMapper.selectByExample(example);
	}

	@Override
	public List<Map<String, Object>> getActualAndRepayInfo(String conNo) {
		return rhzxSubmtPerTradeMapper.queryEditInfoByConNo(conNo);
	}

	//金电信贷信息查询-暂时保留
	@Override
	public List<RhzxSubmtPerJDTrade> findFactoryList(String projectId, String coOrgCode, String IOUNo, String credNo,
			String checkResult, String startDate, String endDate,String dataSrc)  {
		return rhzxSubmtPerJDTradeMapper.findFactoryList(projectId, coOrgCode, IOUNo, credNo, checkResult, startDate, endDate,dataSrc);
	}
}
