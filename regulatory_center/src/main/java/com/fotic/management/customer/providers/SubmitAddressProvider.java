package com.fotic.management.customer.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.fotic.management.customer.entity.SubmitAddress;

@SuppressWarnings("unchecked")
public class SubmitAddressProvider {

	public String execCsvFileSql(Map<String, Object> map) {
		List<SubmitAddress> list = (List<SubmitAddress>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat(
				"(#'{'list[{0}].name}, #'{'list[{0}].certType}, #'{'list[{0}].certNo},#'{'list[{0}].deptCode}, #'{'list[{0}].residence}, "
						+ "#'{'list[{0}].resZip}, #'{'list[{0}].resCondition}, #'{'list[{0}].dataStatus}, #'{'list[{0}].insertDttm}, #'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append("INTO RHZX_SUBMT_PER_ADDRESS (NAME,CERTTYPE,CERTNO,DEPTCODE,RESIDENCE,RESZIP,RESCONDITION,DATA_STATUS,INSERT_DTTM,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
}
