package com.fotic.management.configuration.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.PubMethod;
import com.fotic.management.configuration.dao.NotSumitConfgInfoMapper;
import com.fotic.management.configuration.dao.NotSumitConfgMapper;
import com.fotic.management.configuration.entity.NotSumitConfg;
import com.fotic.management.configuration.entity.NotSumitConfgInfo;
import com.fotic.management.configuration.service.INotSumitConfgService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class NotSumitConfgServiceImpl implements INotSumitConfgService{
	@Autowired
	private NotSumitConfgMapper notSumitConfgMapper;
	@Autowired
	private NotSumitConfgInfoMapper notSumitConfgInfoMapper;
	@Override
	public int insertNotSumitConfg(NotSumitConfg entity) {
		return notSumitConfgMapper.insertSelective(entity);
	}
	@Override
	public List<NotSumitConfg> findAll() {
		return notSumitConfgMapper.selectAll();
	}
	@Override
	public List<NotSumitConfg> findAllValid(String project, String prod, String org, String conno) {
		/**
		 * 入参码值：1项目 2产品 3机构 4合同
		 */
		Example example = new Example(NotSumitConfg.class);
		Criteria  criteria = example.createCriteria();
		criteria.andEqualTo("ifValid", "0");//0有效
		if(!PubMethod.isEmpty(project)){
			criteria.andEqualTo("ruleType", "1");
			criteria.andEqualTo("value",project);
		}else if(!PubMethod.isEmpty(prod)){
			criteria.andEqualTo("ruleType", "2");
			criteria.andEqualTo("value",prod);
		}else if(!PubMethod.isEmpty(org)){
			criteria.andEqualTo("ruleType", "3");
			criteria.andEqualTo("value",org);
		}else if(!PubMethod.isEmpty(conno)){
			criteria.andEqualTo("ruleType", "4");
			criteria.andEqualTo("value",conno);
		}
		example.setOrderByClause(" VALID_DATE DESC ");
		return notSumitConfgMapper.selectByExample(example);
	}

	@Override
	public List<NotSumitConfgInfo> findList(String project, String prod, String org, String conno, String dataSrc) {
		Example example = new Example(NotSumitConfgInfo.class);
		Criteria  criteria = example.createCriteria();
		if(!PubMethod.isEmpty(project)) {
			criteria.andCondition(" PROJ_ID ='"+project+"'");
		}
		if(!PubMethod.isEmpty(prod)) {
			criteria.andCondition(" PROD_CODE ='"+prod+"'");
		}
		if(!PubMethod.isEmpty(org)) {
			criteria.andCondition(" CO_ORG_CODE ='"+org+"'");
		}
		if(!PubMethod.isEmpty(conno)) {
			criteria.andCondition(" CON_NO ='"+conno+"'");
		}
		if(!PubMethod.isEmpty(dataSrc) && !"0".equals(dataSrc)) {
			criteria.andCondition(" DATA_SRC ='"+dataSrc+"'");
		}
		example.setOrderByClause(" VALID_DATE DESC ");
		return notSumitConfgInfoMapper.selectByExample(example);

	}
	@Override
	public int updateNotSumitConfg(NotSumitConfg entity) {
		Example example = new Example(NotSumitConfg.class);
		Criteria  criteria = example.createCriteria();
		criteria.andEqualTo("ruleType", entity.getRuleType());
		criteria.andEqualTo("value", entity.getValue());
		return notSumitConfgMapper.updateByExampleSelective(entity, example);
	}
	@Override
	public NotSumitConfg findOne(NotSumitConfg entity) {
		return notSumitConfgMapper.selectOne(entity);
	}
	
	@Override
	public void insertNotsubMitDatas(List<NotSumitConfg> list) {
		notSumitConfgMapper.insertAll(list);
	}
}
