package com.fotic.management.errormessage.dao;

import org.apache.ibatis.annotations.DeleteProvider;

import com.fotic.management.errormessage.entity.CheckResult;
import com.fotic.management.errormessage.providers.CheckResultProviders;

import tk.mybatis.mapper.common.Mapper;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月14日 下午2:50:48 
 * @version 1.0 * @parameter  
 */
public interface CheckResultMapper extends Mapper<CheckResult>{
	
	@DeleteProvider(method = "delBySelected", type = CheckResultProviders.class)
	public int delBySelected(String coOrgNo, String iouNo, String credType, String credCode, String dataSrc);
}
