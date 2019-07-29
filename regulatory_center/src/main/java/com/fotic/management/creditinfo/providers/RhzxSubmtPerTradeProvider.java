package com.fotic.management.creditinfo.providers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class RhzxSubmtPerTradeProvider {

	public String findList(Map<String, String> params) {
		String projectId = params.get("projectId");
		String coOrgCode = params.get("coOrgCode");
		String IOUNo = params.get("IOUNo");
		String credNo = params.get("credNo");
		String checkResult = params.get("checkResult");
		String startDate = params.get("startDate");
		String endDate = params.get("endDate");
		String dataSrc = params.get("dataSrc");
		String curtermspastdue = params.get("curtermspastdue");
		String sql = new SQL() {
			{
				SELECT("*");
				FROM("V_RHZX_PER_TRADE_INFO");
				if (StringUtils.isNotBlank(projectId)) {
					WHERE("PROJ_ID = " + Integer.parseInt(projectId) );
				}
				if (StringUtils.isNotBlank(coOrgCode)) {
					WHERE("CO_ORG_CODE = '" + coOrgCode + "'");
				}
				if (StringUtils.isNotBlank(IOUNo)) {
					WHERE("IOU_NO like" + "'%" + IOUNo + "%'");
				}
				if (StringUtils.isNotBlank(credNo)) {
					WHERE("CRED_NO like " + "'%" + credNo + "%'");
				}
				if (StringUtils.isNotBlank(checkResult) && !"0".equals(checkResult)) {
					WHERE("CHECK_RESULT = " + checkResult);
				}
				if (StringUtils.isNotBlank(startDate)) {
					WHERE("CON_START_DATE >= TO_DATE('" + startDate + "','yyyy-MM-dd')");
				}
				if (StringUtils.isNotBlank(endDate)) {
					WHERE("CON_START_DATE <= TO_DATE('" + endDate + "','yyyy-MM-dd')");
				}
				if(StringUtils.isNotBlank(dataSrc)) {
					WHERE("DATA_SRC = " + dataSrc);
				}
				if(StringUtils.isNotBlank(curtermspastdue)) {
					WHERE("curtermspastdue = TO_NUMBER('" + curtermspastdue + "')");
				}
				ORDER_BY("IOU_NO DESC");
			}
		}.toString();
		return sql;
	}

	/**
	 * 获取修改数据
	 * 
	 * @param conNo
	 * @param projName
	 * @param coOrgNo
	 * @return
	 */
	public String queryEditInfoByConNo(String conNo) {
		String sql = new SQL() {
			{
				SELECT("b.CON_NO as conNo,a.ID as actualId,a.ACTUAL_AMT as actualAmt,a.ACTUAL_PRNPL AS actualPrnpl,a.ACTUAL_INTES AS actualIntes,a.INTES_PNLTY AS intesPnlty,a.ACTUAL_REPAY_DATE as actualRepayDate,b.REPAY_AMT as repayAmt,b.REPAY_DATE as repayDate");
				FROM(" REG.RHZX_PER_REPAY_PLAN b LEFT JOIN REG.RHZX_PER_ACTUAL_REPAY a ON  a.CON_NO=b.CON_NO AND a.TERM_NO=b.TERM_NO");
				if (StringUtils.isNotBlank(conNo)) {
					WHERE(" b.CON_NO = "+conNo);
				}
				ORDER_BY("b.TERM_NO DESC");
			}
		}.toString();
		return sql;
	}
}
