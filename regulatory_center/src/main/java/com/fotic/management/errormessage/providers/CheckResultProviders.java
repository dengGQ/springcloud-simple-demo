package com.fotic.management.errormessage.providers;

import org.apache.ibatis.jdbc.SQL;

import com.fotic.common.util.PubMethod;

public class CheckResultProviders {
	
	public String delBySelected(String coOrgNo, String iouNo, String credType, String credCode, String dataSrc) {
		String sql = new SQL(){{
	        DELETE_FROM("RHZX_CHECK_RESULT A");
	        if(!PubMethod.isEmpty(coOrgNo)) {
	        	WHERE(" A.CO_ORG_NO ='"+coOrgNo+"'");
	        }if(!PubMethod.isEmpty(iouNo)) {
	        	WHERE(" A.IOU_NO ='"+iouNo+"'");
	        }if(!PubMethod.isEmpty(credType)) {
	        	WHERE(" A.CRED_TYPE ='"+credType+"'");
	        }if(!PubMethod.isEmpty(credCode)) {
	        	WHERE(" A.CRED_CODE ='"+credCode+"'");
	        }if(!PubMethod.isEmpty(dataSrc) && !"0".equals(dataSrc)) {
	        	WHERE(" A.DATA_SRC ='"+dataSrc+"'");
	        }
	    }}.toString();
	    return sql;
	}
}
