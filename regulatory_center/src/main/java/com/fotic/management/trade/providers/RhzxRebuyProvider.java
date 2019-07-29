package com.fotic.management.trade.providers;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import com.fotic.common.util.PubMethod;

@Component
public class RhzxRebuyProvider {

	/**
	 * 获取特殊交易数据
	 * @param conNo
	 * @param projName
	 * @param coOrgNo
	 * @return
	 */
	public String findList(String conNo) {
		String sql = new SQL() {
			{
				SELECT("a.CO_ORG_CODE as coOrgCode,c.CO_ORG_NAME as coOrgName,a.CON_NO as conNo,a.PROJ_ID as projId,b.PROJ_NAME as projName ");
				FROM(" REG.RHZX_PER_REBUY a LEFT JOIN REG.V_MFS_PROJECT b on a.PROJ_ID=b.PROJ_ID LEFT JOIN REG.V_MFS_ORG c on a.CO_ORG_CODE=c.CO_ORG_CODE");
				if(!PubMethod.isEmpty(conNo)){
					WHERE(" a.CON_NO LIKE '%"+conNo+"%'");
				}
				ORDER_BY(" a.BUY_BACK_DATE DESC");
			}
		}.toString();
		return sql;
	}
}
