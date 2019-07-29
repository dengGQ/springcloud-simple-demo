package com.fotic.management.configuration.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.fotic.common.util.PubMethod;
import com.fotic.management.configuration.entity.NotSumitConfg;

@SuppressWarnings("unchecked")
public class NotSumitProvider {

	public String insertBatchDatas(Map<String, Object> map) {
		List<NotSumitConfg> list = (List<NotSumitConfg>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].ruleType}, #'{'list[{0}].value}, #'{'list[{0}].value_name},"
				+ "#'{'list[{0}].iUserCode},#'{'list[{0}].ifValid},#'{'list[{0}].validDate},#'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append("INTO RHZX_CFG_NOT_SUMIT_INFO (RULE_TYPE,VALUE,VALUE_NAME,I_USER_CODE,IF_VALID,VALID_DATE,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
	
	public String queryList(String project, String prod, String org, String conno, String dataSrc) {
		String sql = new SQL() {
			{
				SELECT(" PROJ_ID,PROJ_NAME,CO_ORG_CODE,CO_ORG_NAME,PROD_CODE,PROD_NAME,CON_NO,VALID_DATE,DATA_SRC ");
				FROM(" V_RHZX_PER_NOT_SUMIT_INFO");
				if(!PubMethod.isEmpty(project)) {
					WHERE(" PROJ_ID ='"+project+"'");
				}
				if(!PubMethod.isEmpty(prod)) {
					WHERE(" PROD_CODE ='"+prod+"'");
				}
				if(!PubMethod.isEmpty(org)) {
					WHERE(" CO_ORG_CODE ='"+org+"'");
				}
				if(!PubMethod.isEmpty(conno)) {
					WHERE(" CON_NO ='"+conno+"'");
				}
				if(!PubMethod.isEmpty(dataSrc) && !"0".equals(dataSrc)) {
					WHERE(" DATA_SRC ="+dataSrc);
				}
				ORDER_BY(" VALID_DATE desc");
			}
		}.toString();
		return sql;
	}
}
