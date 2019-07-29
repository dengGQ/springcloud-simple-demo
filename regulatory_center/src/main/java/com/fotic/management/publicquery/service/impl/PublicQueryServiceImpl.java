package com.fotic.management.publicquery.service.impl;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.PubMethod;
import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.publicquery.dao.DictMapper;
import com.fotic.management.publicquery.dao.ProjectMapper;
import com.fotic.management.publicquery.dao.RhzxContractMapper;
import com.fotic.management.publicquery.dao.RhzxOrgMapper;
import com.fotic.management.publicquery.dao.RhzxProdureMapper;
import com.fotic.management.publicquery.entity.Project;
import com.fotic.management.publicquery.entity.RhzxContract;
import com.fotic.management.publicquery.entity.RhzxDict;
import com.fotic.management.publicquery.entity.RhzxOrg;
import com.fotic.management.publicquery.entity.RhzxProdure;
import com.fotic.management.publicquery.service.PublicQueryService;
import com.fotic.management.reported.entity.SubmitPerTrade;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class PublicQueryServiceImpl implements PublicQueryService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private RhzxOrgMapper rhzxOrgMapper;
	@Autowired
	private RhzxProdureMapper rhzxProdureMapper;
	@Autowired
	private RhzxContractMapper rhzxContractMapper;
	@Autowired
	private DictMapper dictMapper;
	
	public Project getProject(Project project){
		return projectMapper.selectOne(project);
	}

	@Override
	public List<RhzxOrg> findAllRhzxOrg(String orgName) {
		Example example = new Example(RhzxOrg.class);
		Criteria  criteria = example.createCriteria();
		if(PubMethod.isEmpty(orgName)){
			return rhzxOrgMapper.selectAll();
		}else{
			criteria.andCondition("co_org_name like '%"+orgName+"%'");
			return rhzxOrgMapper.selectByExample(example);
		}
	}
	
	@Override
	public List<RhzxOrg> findRhzxOrgByNameOrCode(String orgNameOrCode) {
		if(PubMethod.isEmpty(orgNameOrCode)){
			return rhzxOrgMapper.selectAll();
		}else{
			return rhzxOrgMapper.findRhzxOrgByNameOrCode(orgNameOrCode);
		}
	}
	
	@Override
	public List<Project> findAllProject(String projectName) {
		Example example = new Example(Project.class);
		Criteria  criteria = example.createCriteria();
		if(PubMethod.isEmpty(projectName)){
			return projectMapper.selectAll();
		}else{
			criteria.andCondition("proj_name like '%"+projectName+"%'");
			return projectMapper.selectByExample(example);
		}
	}
	
	@Override
	public List<Project> findRhzxOrgByNameOrId(String projNameOrId) {
		if(PubMethod.isEmpty(projNameOrId)){
			return projectMapper.selectAll();
		}else{
			Integer intProjNameOrId = -1;
			boolean isInteger = isInteger(projNameOrId);
			if(isInteger){
				intProjNameOrId = Integer.valueOf(projNameOrId);
			}
			return projectMapper.findRhzxOrgByNameOrId(projNameOrId,intProjNameOrId);
		}
	}
	

	@Override
	public List<RhzxProdure> findAllRhzxProdure(String prodName) {
		Example example = new Example(RhzxProdure.class);
		Criteria  criteria = example.createCriteria();
		if(PubMethod.isEmpty(prodName)){
			return rhzxProdureMapper.selectAll();
		}else{
			criteria.andCondition("prod_name like '%"+prodName+"%'");
			return rhzxProdureMapper.selectByExample(example);
		}
	}
	
	@Override
	public List<RhzxProdure> findProductAndOrgByProjNameOrOrgName(String projNameOrOrgName) {
		if(PubMethod.isEmpty(projNameOrOrgName)){
			return rhzxProdureMapper.findAllProductAndOrgByProjNameOrOrgName();
		}
		return rhzxProdureMapper.findProductAndOrgByProjNameOrOrgName(projNameOrOrgName);
	}

	@Override
	public List<RhzxContract> findAllRhzxContract(String conNo) {
		Example example = new Example(RhzxContract.class);
		Criteria  criteria = example.createCriteria();
		if(PubMethod.isEmpty(conNo)){
			return rhzxContractMapper.selectAll();
		}else{
			criteria.andCondition("con_no like '%"+conNo+"%'");
			return rhzxContractMapper.selectByExample(example);
		}
	}

	@Override
	public List<RhzxDict> findAllRhzxDict(String dictId, String dictColName) {
		Example example = new Example(RhzxDict.class);
		Criteria  criteria = example.createCriteria();
		criteria.andCondition("dict_id = '"+dictId+"'");
		criteria.andCondition("dict_col_name = '"+dictColName+"'");
		return dictMapper.selectByExample(example);
	}
	
	@Override
	public List<RhzxDict> findAllRhzxDictByType(String dictColName) {
		Example example = new Example(RhzxDict.class);
		Criteria  criteria = example.createCriteria();
		criteria.andCondition("dict_col_name = '"+dictColName+"'");
		return dictMapper.selectByExample(example);
	}

	@Override
	public List<Project> findProjectByOrgCode(String coOrgCode) {
		List<Project> projectList = projectMapper.findProjectByOrgCode(coOrgCode);
		return projectList;
	}

	@Override
	public List<RhzxOrg> queryAllOrgAndProject() {
		List<RhzxOrg> list = rhzxOrgMapper.queryAllOrgAndProject();
		return list;
	}

	//判断字符串是否为整数
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  }

	@Override
	public List<RhzxOrg> queryOrg() {
		// TODO Auto-generated method stub
		return rhzxOrgMapper.queryOrg();
	}

	@Override
	public List<Project> queryProjectByCoOrgCode(String coOrgCode) {
		// TODO Auto-generated method stub
		return projectMapper.queryProjectByCoOrgCode(coOrgCode);
	}

	@Override
	public List<RhzxProdure> queryProductByProjIdAndCoorgcode(String projId, String coOrgCode) {
		// TODO Auto-generated method stub
		return rhzxProdureMapper.queryProductByProjId(projId, coOrgCode);
	}

	@Override
	public List<RhzxOrg> queryOrg(String moduleId) {
		// TODO Auto-generated method stub
		return rhzxOrgMapper.queryOrgByModuleId(moduleId);
	}

	@Override
	public List<Project> queryProjectByCoOrgCode(String coOrgCode, String moduleId) {
		// TODO Auto-generated method stub
		return projectMapper.queryProjectByCoOrgCodeAndModuleId(coOrgCode, moduleId);
	}

	@Override
	public List<RhzxProdure> queryProductByProjIdAndModuleIdWithCoorgcode(String projId, String moduleId, String coOrgCode) {
		// TODO Auto-generated method stub
		return rhzxProdureMapper.queryProductByProjIdAndModuleId(projId, moduleId, coOrgCode);
	}

	@Override
	public List<SubmitPerTrade> queryOverdue() {
		// TODO Auto-generated method stub
		return rhzxProdureMapper.queryOverdue();
	}

	

	
}
