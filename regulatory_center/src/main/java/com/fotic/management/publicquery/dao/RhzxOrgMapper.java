package com.fotic.management.publicquery.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.publicquery.entity.RhzxOrg;

import tk.mybatis.mapper.common.Mapper;
public interface RhzxOrgMapper extends Mapper<RhzxOrg>{
	
//	@Results({
//		@Result(property="projectList",column="CO_ORG_CODE",javaType=List.class,many=@Many(select="com.fotic.management.publicquery.dao.ProjectMapper.findProjectByOrgCode"))
//		@Result(property="productList",column="CO_ORG_CODE",javaType=List.class,many=@Many(select="com.fotic.management.publicquery.dao.RhzxProdureMapper.findProductByOrgCode"))
//	})
	@Select("select o.co_org_code coOrgCode, o.co_org_name coOrgName from V_MFS_ORG o")
	public List<RhzxOrg> queryAllOrgAndProject();
	
	@Select("select * from V_MFS_ORG where co_org_name like '%'||#{orgNameOrCode}||'%' or co_org_code=#{orgNameOrCode}")
	public List<RhzxOrg> findRhzxOrgByNameOrCode(@Param(value="orgNameOrCode")String orgNameOrCode);
	
	@Select("select co_org_code, CO_ORG_NAME from V_RHZX_PROJ_PROD_INFO group by co_org_code,CO_ORG_NAME")
	public List<RhzxOrg> queryOrg();
	
	@Select("select distinct r.co_org_code,o.co_org_name from RHZX_SMS_SETTING_RULE r left join V_MFS_ORG o on r.co_org_code = o.co_org_code where r.module_id = #{moduleId}")
	public List<RhzxOrg> queryOrgByModuleId(String moduleId);
	
	
}