package com.fotic.management.publicquery.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.management.creditinfo.entity.RhzxSubmtPerTrade;
import com.fotic.management.publicquery.entity.RhzxProdure;
import com.fotic.management.reported.entity.SubmitPerTrade;

import tk.mybatis.mapper.common.Mapper;
public interface RhzxProdureMapper extends Mapper<RhzxProdure>{
	
	@Select("SELECT a.*,b.co_org_name FROM V_MFS_PRODUCT a,V_MFS_ORG b where a.CO_ORG_CODE=b.co_org_code and a.PROD_NAME like '%'||#{projNameOrOrgName}||'%' or b.co_org_name like '%'||#{projNameOrOrgName}||'%'")
	public List<RhzxProdure> findProductAndOrgByProjNameOrOrgName(@Param(value="projNameOrOrgName")String projNameOrOrgName);
	
	@Select("SELECT a.*,b.co_org_name FROM V_MFS_PRODUCT a,V_MFS_ORG b where a.CO_ORG_CODE=b.co_org_code")
	public List<RhzxProdure> findAllProductAndOrgByProjNameOrOrgName();
	
	
	@Select("select prod_code, prod_name from V_RHZX_PROJ_PROD_INFO where proj_id = #{projId} AND CO_ORG_CODE = #{coOrgCode}")
	public List<RhzxProdure> queryProductByProjId(@Param(value="projId")String projId, @Param(value="coOrgCode")String coOrgCode);
	
	@Select("select distinct r.proj_id, r.prod_id, p1.PROD_NAME from RHZX_SMS_SETTING_RULE r left join V_RHZX_PROJ_PROD_INFO p1 on r.prod_id = p1.PROD_CODE where r.proj_id = #{projId} and r.module_id = #{moduleId} and r.co_org_code = #{coOrgCode}")
	public List<RhzxProdure> queryProductByProjIdAndModuleId(@Param("projId")String projId, @Param("moduleId")String moduleId, @Param("coOrgCode")String coOrgCode);
	@Select("select distinct curtermspastdue  from RHZX_SUBMT_PER_TRADE where curtermspastdue !=0 order by curtermspastdue ")
	public List<SubmitPerTrade> queryOverdue();
}