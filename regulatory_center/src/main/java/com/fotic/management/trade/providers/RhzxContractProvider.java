package com.fotic.management.trade.providers;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import com.fotic.common.util.PubMethod;

@Component
public class RhzxContractProvider {

	/**
	 * 获取合同基本信息
	 * 
	 * @param conNo
	 * @return
	 */
	public String findContractList(String conNo,String iouNo,String dataSrc) {
		String sql = new SQL() {
			{
				SELECT("a.CON_NO as conNo,a.iou_no as iouNo,a.data_src as dataSrc,a.CON_START_DATE as conStartDate,a.CON_END_DATE as conEndDate,"
						+ "a.CRED_TYPE as credType,a.CRED_CODE as credCode,a.LOAN_TERM as loanTerm, "
						+ "b.CO_ORG_NAME as orgName ");
				FROM(" RHZX_PER_CONTRACT a LEFT JOIN V_MFS_ORG b ON a.CO_ORG_CODE=b.CO_ORG_CODE");
				if (!PubMethod.isEmpty(conNo)) {
					WHERE(" a.CON_NO LIKE '%" + conNo.trim() + "%'");
				}
				if (!PubMethod.isEmpty(iouNo)) {
					WHERE(" a.iou_no ='" + iouNo.trim() + "'");
				}
				if (!PubMethod.isEmpty(dataSrc)) {
					WHERE(" a.data_src ='" + dataSrc + "'");
				}
				ORDER_BY(" a.CO_ORG_CODE");
			}
		}.toString();
		return sql;
	}
}
