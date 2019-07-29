package com.fotic.management.unreported.providers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

/**
 * 应报未报, 编写复杂sql
 * @author zhaoqh
 *
 */
@Component
public class UnreportedProvider {

	/**
	 * 根据月份查询, 应报未报数据
	 * @param params
	 * @return
	 */
	public String findList(Map<String, String> params){
		String startMonth = params.get("startMonth");
		String endMonth = params.get("endMonth");
		String dataSrc = params.get("dataSrc");
		String sql = new SQL(){{
            SELECT("*");
            FROM("V_RHZX_PER_LACK_DATA");
            if(StringUtils.isNotBlank(startMonth)){
                WHERE("to_date(repay_date,'yyyy-MM-dd') >= to_date('"+startMonth+"','yyyy-MM-dd')");
            }
            if(StringUtils.isNotBlank(endMonth)){
                WHERE("to_date(repay_date,'yyyy-MM-dd') <= to_date('"+endMonth+"','yyyy-MM-dd')");
            }
            if(StringUtils.isNotBlank(dataSrc) && !"0".equals(dataSrc)){
                WHERE(" data_Src ='"+dataSrc+"'");
            }
            ORDER_BY("proj_id desc");
        }}.toString();
        return sql;
	}
}
