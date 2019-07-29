package com.fotic.management.publicquery.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.publicquery.entity.Project;

import tk.mybatis.mapper.common.Mapper;
public interface ProjectMapper extends Mapper<Project>{

	@Select("SELECT * FROM V_MFS_PROJECT WHERE CO_ORG_CODE= #{coOrgCode}")
	public List<Project> findProjectByOrgCode(@Param(value="coOrgCode")String coOrgCode);
	
	@Select("select * from V_MFS_PROJECT where proj_name like '%'||#{projNameOrId}||'%' or proj_id=#{intProjNameOrId}")
	public List<Project> findRhzxOrgByNameOrId(@Param(value="projNameOrId")String projNameOrId,@Param(value="intProjNameOrId")Integer intProjNameOrId);
   
	@Select("select proj_id, PROJ_NAME from V_RHZX_PROJ_PROD_INFO where co_org_code = #{coOrgCode} group by proj_id, PROJ_NAME")
	public List<Project> queryProjectByCoOrgCode(String coOrgCode);
	
	@Select("select distinct r.co_org_code,r.proj_id, p.PROJ_NAME from RHZX_SMS_SETTING_RULE r left join V_RHZX_PROJ_PROD_INFO p on r.proj_id = p.PROJ_ID where r.co_org_code = #{coOrgCode} and r.module_id = #{moduleId}")
	public List<Project> queryProjectByCoOrgCodeAndModuleId(@Param("coOrgCode")String coOrgCode, @Param("moduleId")String moduleId);
	
	
}