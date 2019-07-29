package com.fotic.management.publicquery.service;
import java.util.List;

import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.publicquery.entity.Project;
import com.fotic.management.publicquery.entity.RhzxContract;
import com.fotic.management.publicquery.entity.RhzxDict;
import com.fotic.management.publicquery.entity.RhzxOrg;
import com.fotic.management.publicquery.entity.RhzxProdure;
import com.fotic.management.reported.entity.SubmitPerTrade;
/**
 * 
* @Title: PublicQueryService.java 
* @Package com.fotic.management.publicquery.service 
* @Description: 放大镜，公共查询
* @author 王明月   
* @date 2017年6月19日 下午2:32:37
 */
public interface PublicQueryService {
	/**
	 * 项目
	 */
	List<Project> findAllProject(String projectName);
	
	/**
	 * 通过项目名称或者项目编号查询
	 */
	List<Project> findRhzxOrgByNameOrId(String projNameOrId);
	
	/**
	 * 查询合作机构下面的所有项目
	 * @param coOrgCode
	 * @return
	 */
	List<Project> findProjectByOrgCode(String coOrgCode);
	/**
	 * 
	 * @param project
	 * @return
	 */
	Project getProject(Project project);
	
	/**
	 * 查询合作机构和合作机构下的所有项目
	 * @return
	 */
	List<RhzxOrg> queryAllOrgAndProject();
	/**
	 * 机构
	 */
	List<RhzxOrg> findAllRhzxOrg(String orgName);
	
	/**
	 * 通过机构名称或者机构编号查询机构
	 * @param orgName 机构名称或者机构编号
	 * @return
	 */
	List<RhzxOrg> findRhzxOrgByNameOrCode(String orgNameOrCode);
	
	/**
	 * 产品
	 */
	List<RhzxProdure> findAllRhzxProdure(String prodName);
	/**
	 * 通过产品名称或者合作机构名称查询产品和合作机构
	 * @param prodName
	 * @return
	 */
	List<RhzxProdure> findProductAndOrgByProjNameOrOrgName(String projNameOrOrgName);
	/**
	 * 合同
	 */
	List<RhzxContract> findAllRhzxContract(String conNo);
	
	/**
	 * 字典表
	 */
	List<RhzxDict> findAllRhzxDict(String dictId,String dictColName);
	
	/**
	 * 字典表
	 */
	List<RhzxDict> findAllRhzxDictByType(String dictColName);
	
	List<RhzxOrg> queryOrg();
	
	List<RhzxOrg> queryOrg(String moduleId);
	
	List<Project> queryProjectByCoOrgCode(String coOrgCode);
	
	List<Project> queryProjectByCoOrgCode(String coOrgCode, String moduleId);
	
	List<RhzxProdure> queryProductByProjIdAndCoorgcode(String coOrgCode, String projId);
	
	List<RhzxProdure> queryProductByProjIdAndModuleIdWithCoorgcode(String projId, String moduleId, String coOrgCode);
	
	List<SubmitPerTrade> queryOverdue();
}
