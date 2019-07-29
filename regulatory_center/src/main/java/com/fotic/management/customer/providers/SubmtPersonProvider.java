package com.fotic.management.customer.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.fotic.management.customer.entity.SubmtPerson;

@SuppressWarnings("unchecked")
public class SubmtPersonProvider {

	public String execCsvFileSql(Map<String, Object> map) {
		List<SubmtPerson> list = (List<SubmtPerson>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		MessageFormat mf = new MessageFormat(
				"(#'{'list[{0}].name}, #'{'list[{0}].certtype}, #'{'list[{0}].certno},#'{'list[{0}].deptcode}, #'{'list[{0}].gender}, "
						+ "#'{'list[{0}].birthday}, #'{'list[{0}].marriage}, #'{'list[{0}].edulevel}, #'{'list[{0}].edudegree},"
						+ "#'{'list[{0}].spouse_name}, #'{'list[{0}].spouse_certno},#'{'list[{0}].spouse_certtype}, #'{'list[{0}].spouse_office},"
						+ "#'{'list[{0}].spouse_tel}, #'{'list[{0}].hometel},#'{'list[{0}].mobiletel}, #'{'list[{0}].officetel},"
						+ "#'{'list[{0}].email}, #'{'list[{0}].address},#'{'list[{0}].zip}, #'{'list[{0}].residence},"
						+ "#'{'list[{0}].dataStatus}, #'{'list[{0}].insertDttm}, #'{'list[{0}].dataSrc}) ");
		for (int i = 0; i < list.size(); i++) {
			sb.append( "INTO RHZX_SUBMT_PER_PERSON (NAME,CERTTYPE,CERTNO,DEPTCODE,GENDER,BIRTHDAY,MARRIAGE,EDULEVEL,EDUDEGREE,SPOUSE_NAME,SPOUSE_CERTNO,SPOUSE_CERTTYPE,"
					+ "SPOUSE_OFFICE,SPOUSE_TEL,HOMETEL,MOBILETEL,OFFICETEL,EMAIL,ADDRESS,ZIP,RESIDENCE,DATA_STATUS,INSERT_DTTM,DATA_SRC) VALUES ");
			sb.append(mf.format(new Object[] { i }));
		}
		sb.append(" SELECT 1 FROM dual");
		return sb.toString();
	}
}
