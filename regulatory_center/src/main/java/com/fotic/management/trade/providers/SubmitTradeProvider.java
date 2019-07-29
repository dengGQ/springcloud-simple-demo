package com.fotic.management.trade.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.fotic.common.util.PubMethod;
import com.fotic.management.trade.entity.SubmitTrade;

@SuppressWarnings("unchecked")
public class SubmitTradeProvider {

	public String execCsvFileSql(Map<String, Object> map) {
		List<SubmitTrade> list = (List<SubmitTrade>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat(
				"("
				+ "#'{'list[{0}].name}, #'{'list[{0}].certtype}, #'{'list[{0}].certno},#'{'list[{0}].deptcode}, #'{'list[{0}].generaltype}, #'{'list[{0}].type}, "
				+ "#'{'list[{0}].account}, #'{'list[{0}].tradeid},#'{'list[{0}].areacode}, #'{'list[{0}].dateopened}, #'{'list[{0}].dateclosed}, #'{'list[{0}].currency}, "
				+ "#'{'list[{0}].creditlimit}, #'{'list[{0}].shareaccount},#'{'list[{0}].maxdebt}, #'{'list[{0}].guaranteeway}, #'{'list[{0}].termsfreq}, #'{'list[{0}].monthduration}, "
				+ "#'{'list[{0}].monthunpaid}, #'{'list[{0}].billingdate},#'{'list[{0}].recentpaydate}, #'{'list[{0}].scheduledamount}, #'{'list[{0}].actualpayamount}, #'{'list[{0}].balance}, "
				+ "#'{'list[{0}].curtermspastdue}, #'{'list[{0}].amountpastdue},#'{'list[{0}].amountpastdue30}, #'{'list[{0}].amountpastdue60}, #'{'list[{0}].amountpastdue90}, #'{'list[{0}].apastdue180}, "
				+ "#'{'list[{0}].termspastdue}, #'{'list[{0}].maxtermspastdue},#'{'list[{0}].class5stat}, #'{'list[{0}].accountstat}, #'{'list[{0}].paystat24month}, "
				+ "#'{'list[{0}].dataStatus}, #'{'list[{0}].insertDttm}, #'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append(
					"INTO RHZX_SUBMT_PER_TRADE (NAME,CERTTYPE,CERTNO,DEPTCODE,GENERALTYPE,TYPE,"
					+ "ACCOUNT,TRADEID,AREACODE,DATEOPENED,DATECLOSED,CURRENCY,"
					+ "CREDITLIMIT,SHAREACCOUNT,MAXDEBT,GUARANTEEWAY,TERMSFREQ,MONTHDURATION,"
					+ "MONTHUNPAID,BILLINGDATE,RECENTPAYDATE,SCHEDULEDAMOUNT,ACTUALPAYAMOUNT,BALANCE,"
					+ "CURTERMSPASTDUE,AMOUNTPASTDUE,AMOUNTPASTDUE30,AMOUNTPASTDUE60,AMOUNTPASTDUE90,APASTDUE180,"
					+ "TERMSPASTDUE,MAXTERMSPASTDUE,CLASS5STAT,ACCOUNTSTAT,PAYSTAT24MONTH,"
					+ "DATA_STATUS,INSERT_DTTM,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
	
	public String queryList(String certno,String dataStatus, String dataSrc) {
		String sql = new SQL() {
			{
				SELECT(" * ");
				FROM(" RHZX_SUBMT_PER_TRADE");
				if(!PubMethod.isEmpty(certno)) {
					WHERE(" CERTNO ='"+certno+"'");
				}
				if(!PubMethod.isEmpty(dataStatus) && !"0".equals(dataStatus)) {
					WHERE(" DATA_STATUS ="+dataStatus);
				}
				WHERE(" data_src ="+dataSrc);
				ORDER_BY(" ACCOUNT desc");
			}
		}.toString();
		return sql;
	}
}
