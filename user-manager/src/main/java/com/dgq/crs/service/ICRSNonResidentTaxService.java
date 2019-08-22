package com.dgq.crs.service;

import java.util.List;
import java.util.Map;

import com.dgq.crs.dto.ReportTaxInfoDto;
import com.dgq.crs.entity.CrsDataCheckResult;
import com.dgq.crs.entity.ReportTaxInfo;
import com.dgq.crs.xml.util.BuisnessChecker;

/**
 * CRS非居民金融账户涉税信息报送业务层接口
 * @author lishuaihao
 * @ create 2018-12-04 8:54
 */
public interface ICRSNonResidentTaxService {
	
	/**
	 * 根据账户分组按条件查询
	 * @param rtf
	 * @return
	 */
	public List<ReportTaxInfo> queryReportTaxInfoWithGroupByAccountNumber(ReportTaxInfoDto dto);

	/**
	 * 按条件查询
	 * @param currentYearStartDate 当前年第一天
	 * @param currentYearEndDate 当前年最后一天
	 * @return
	 */
	public List<ReportTaxInfo> queryReportInfoByCondition(ReportTaxInfoDto dto);

	/**
	 * 按条件分页查询
	 * @param accountNumber
	 * @return
	 */
	public Map<String, Object> queryReportTaxInfoByConditionForPage(int pageNum, int pageSize, ReportTaxInfoDto dto);
	
	/**
	 * 分页查询数据修改日志
	 * @param pageNum
	 * @param pageSize
	 * @param accountNumber
	 * @param dataYear
	 * @return
	 */
	Map<String, Object> dataEditListForPage(int pageNum, int pageSize, String accountNumber, String dataYear);
	
	/**
	 * 根据账户查询控制人信息
	 * @param accountNumber
	 * @return
	 */
	public List<ReportTaxInfo> queryControllingPersonByAccountNumber(String accountNumber);
	
	/**
	 * 查询数据校验结果集
	 * @return
	 */
	public List<CrsDataCheckResult> queryCrsDataCheckResult();
	

	/**
	 * 将DocRefId和MessageRefId回写到库
	 * @param map
	 */
	void fillDocRefIdAndMessageRefId(BuisnessChecker businessChecker);
}