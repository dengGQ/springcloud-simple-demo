package com.fotic.management.errormessage.service;

import java.util.List;

import com.fotic.management.errormessage.entity.CheckResult;

/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月14日 下午2:53:14 
 * @version 1.0 * @parameter  
 */
public interface ICheckResultService {
	public List<CheckResult> findList(String coOrgNo,String iouNo,String credType,String credCode,String dataSrc);
	
	/**
	 * 根据条件删除数据
	 * @param coOrgNo
	 * @param iouNo
	 * @param credType
	 * @param credCode
	 * @param dataSrc
	 * @return
	 */
	public int delBySelected(String coOrgNo,String iouNo,String credType,String credCode,String dataSrc);
}
