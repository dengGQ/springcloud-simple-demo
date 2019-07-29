package com.fotic.management.datacheck.providers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class DataCheckProvider {

	public String queryDataCheck(Map<String, String> params){
		String year = params.get("year");
		String sql = new SQL(){{
            SELECT(" DISTINCT A.SEQ_ID,A.QUATR,A.FILE_NAME,to_char(A.BUSS_DATE,'yyyy-MM-dd') BUSS_DATE ");
            FROM("RHZX_SUBMT_Q_CHECK_FILE A");
            if(StringUtils.isNotBlank(year)){
            	WHERE("A.QUATR LIKE '%" + year + "%'");
            }
            ORDER_BY("  A.QUATR DESC ");
        }}.toString();
        return sql;
	}
	
	public String queryFileNamesByQuatr(Map<String, String> params){
		String quarter = params.get("quatr");
		String sql = new SQL(){{
            SELECT("FILE_NAME");
            FROM("RHZX_SUBMT_Q_CHECK_FILE");
            if(StringUtils.isNotBlank(quarter)){
                WHERE("QUATR IN ( "+quarter+")");
            }
        }}.toString();
        return sql;
	}
}
