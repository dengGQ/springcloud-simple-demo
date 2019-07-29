package com.fotic.management.errormessage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.PubMethod;
import com.fotic.management.errormessage.dao.CheckResultMapper;
import com.fotic.management.errormessage.entity.CheckResult;
import com.fotic.management.errormessage.service.ICheckResultService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月14日 下午2:56:36 
 * @version 1.0 * @parameter  
 */
@Service
public class CheckResultImpl implements ICheckResultService {

	@Autowired
	private CheckResultMapper checkResultMapper;

	@Override
	public List<CheckResult> findList(String coOrgNo, String iouNo, String credType, String credCode, String dataSrc) {
		Example example = new Example(CheckResult.class);
		Criteria  criteria = example.createCriteria();
		if(!PubMethod.isEmpty(coOrgNo)){
			criteria.andCondition("co_org_code = '"+coOrgNo+"'");
		}
		if(!PubMethod.isEmpty(iouNo)){
			criteria.andCondition("IOU_NO = '"+iouNo+"'");
		}
		if(!PubMethod.isEmpty(credType) && !"".equals(credType)){
			criteria.andCondition("cred_type = '"+credType+"'");
		}
		if(!PubMethod.isEmpty(credCode)){
			criteria.andCondition("cred_code like '%"+credCode+"%'");
		}
		if(!PubMethod.isEmpty(dataSrc) && !"0".equals(dataSrc)){
			criteria.andCondition("data_src ='"+dataSrc+"'");
		}
		return checkResultMapper.selectByExample(example);
	}

	@Override
	public int delBySelected(String coOrgNo, String iouNo, String credType, String credCode, String dataSrc) {
		Example example = new Example(CheckResult.class);
		Criteria  criteria = example.createCriteria();
		if(!PubMethod.isEmpty(coOrgNo)){
			criteria.andCondition("co_org_code = '"+coOrgNo+"'");
		}
		if(!PubMethod.isEmpty(iouNo)){
			criteria.andCondition("IOU_NO = '"+iouNo+"'");
		}
		if(!PubMethod.isEmpty(credType) && !"".equals(credType)){
			criteria.andCondition("cred_type = '"+credType+"'");
		}
		if(!PubMethod.isEmpty(credCode)){
			criteria.andCondition("cred_code like '%"+credCode+"%'");
		}
		if(!PubMethod.isEmpty(dataSrc) && !"0".equals(dataSrc)){
			criteria.andCondition("data_src ='"+dataSrc+"'");
		}
		return checkResultMapper.deleteByExample(example);
	}
	
	

}
