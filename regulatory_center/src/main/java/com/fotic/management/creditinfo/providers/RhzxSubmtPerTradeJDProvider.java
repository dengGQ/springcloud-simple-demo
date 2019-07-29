package com.fotic.management.creditinfo.providers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class RhzxSubmtPerTradeJDProvider {

	public String findFactoryList(Map<String, String> params) {
		String projectId = params.get("projectId");
		String coOrgCode = params.get("coOrgCode");
		String IOUNo = params.get("IOUNo");
		String credNo = params.get("credNo");
		String checkResult = params.get("checkResult");
		String startDate = params.get("startDate");
		String endDate = params.get("endDate");
		String dataSrc = params.get("dataSrc");
		String sql = new SQL() {
			{
				SELECT("*");
				FROM("V_RHZX_PER_TRADE_INFO_JD");
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
				ORDER_BY("REPAY_DATA");
			}
		}.toString();
		return sql;
	}
}
