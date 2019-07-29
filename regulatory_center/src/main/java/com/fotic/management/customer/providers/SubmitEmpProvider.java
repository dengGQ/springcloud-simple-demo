package com.fotic.management.customer.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.fotic.management.customer.entity.SubmitEmp;

@SuppressWarnings("unchecked")
public class SubmitEmpProvider {

	public String execCsvFileSql(Map<String, Object> map) {
		List<SubmitEmp> list = (List<SubmitEmp>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat(
				"(#'{'list[{0}].name}, #'{'list[{0}].certtype}, #'{'list[{0}].certno},#'{'list[{0}].deptcode}, #'{'list[{0}].occupation}, "
						+ "#'{'list[{0}].company}, #'{'list[{0}].industry},"
						+ "#'{'list[{0}].occaddress},#'{'list[{0}].occzip},#'{'list[{0}].startyear}, "
						+ "#'{'list[{0}].duty},#'{'list[{0}].caste},#'{'list[{0}].annualincome},"
						+ "#'{'list[{0}].dataStatus}, #'{'list[{0}].insertDttm}, #'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append(
					"INTO RHZX_SUBMT_PER_EMP (NAME,CERTTYPE,CERTNO,DEPTCODE,OCCUPATION,COMPANY,INDUSTRY,OCCADDRESS,OCCZIP,STARTYEAR,DUTY,CASTE,ANNUALINCOME,DATA_STATUS,INSERT_DTTM,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
}
