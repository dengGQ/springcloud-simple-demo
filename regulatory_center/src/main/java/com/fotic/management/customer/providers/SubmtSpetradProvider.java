package com.fotic.management.customer.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.fotic.management.customer.entity.SubmtSpetrad;

@SuppressWarnings("unchecked")
public class SubmtSpetradProvider {

	public String execCsvFileSql(Map<String, Object> map) {
		List<SubmtSpetrad> list = (List<SubmtSpetrad>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat(
				"(#'{'list[{0}].account}, #'{'list[{0}].tradeid}, #'{'list[{0}].deptcode}, #'{'list[{0}].spectype}, "
						+ "#'{'list[{0}].specdate}, #'{'list[{0}].specmonth}, #'{'list[{0}].specmoney},#'{'list[{0}].specdetail},"
						+ " #'{'list[{0}].dataStatus}, #'{'list[{0}].insertDttm}, #'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append("INTO RHZX_SUBMT_PER_SPETRADE (ACCOUNT,TRADEID,DEPTCODE,SPECTYPE,SPECDATE,SPECMONTH,SPECMONEY,SPECDETAIL,DATA_STATUS,INSERT_DTTM,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
}
