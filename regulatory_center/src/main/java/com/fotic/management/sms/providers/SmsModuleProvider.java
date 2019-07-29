package com.fotic.management.sms.providers;

import org.apache.ibatis.jdbc.SQL;

import com.fotic.common.constant.Constant;
import com.fotic.common.util.PubMethod;
import com.fotic.management.sms.entity.SmsModule;

public class SmsModuleProvider {

	/**
	 * 新增保存短信模板
	 * @return
	 */
	public String addSmsModule(SmsModule smsModule){
		/*SmsModule smsModule = (SmsModule)param.get("smsModule");
		String sql = new SQL(){
			{
				INSERT_INTO("RHZX_SMS_MODULE");
				VALUES("id","#{id,javaType=Integer,jdbcType=INTEGER}");
				if(!PubMethod.isEmpty(smsModule.getSmsModuleName())){
					VALUES("SMS_MODULE_NAME", "'"+smsModule.getSmsModuleName()+"'");
				}
				if(!PubMethod.isEmpty(smsModule.getSmsModuleType())){
					VALUES("SMS_MODULE_TYPE", "'"+smsModule.getSmsModuleType()+"'");
				}
				if(!PubMethod.isEmpty(smsModule.getSmsModuleContent())){
					VALUES("SMS_MODULE_CONTENT", "'"+smsModule.getSmsModuleContent()+"'");
				}
				if(!PubMethod.isEmpty(smsModule.getOperatorId())){
					VALUES("OPERATOR_ID", "'"+smsModule.getOperatorId()+"'");
				}
				if(!PubMethod.isEmpty(smsModule.getOperator())){
					VALUES("OPERATOR", "'"+smsModule.getOperator()+"'");
				}
				if(!PubMethod.isEmpty(smsModule.getStatus())){
					VALUES("STATUS", "'"+smsModule.getStatus()+"'");
				}
					VALUES("CREATE_TIME","current_date");
					VALUES("UPDATE_TIME","current_date");
			}
		}.toString();*/
		
		String sql = new SQL() {
			{
				INSERT_INTO("RHZX_SMS_MODULE");
				VALUES("id","#{id,javaType=Integer,jdbcType=INTEGER}");
				VALUES("SMS_MODULE_NAME","#{smsModuleName}");
				VALUES("SMS_MODULE_TYPE","#{smsModuleType,javaType=string,jdbcType=VARCHAR}");
				VALUES("SMS_MODULE_CONTENT","#{smsModuleContent,javaType=string,jdbcType=VARCHAR}");
				VALUES("OPERATOR_ID","#{operatorId,javaType=string,jdbcType=VARCHAR}");
				VALUES("OPERATOR","#{operator,javaType=string,jdbcType=VARCHAR}");
				VALUES("STATUS","#{status,javaType=string,jdbcType=VARCHAR}");
				VALUES("CREATE_TIME","current_date");
				VALUES("UPDATE_TIME","current_date");
			}
		}.toString();
		return sql;
	}
	
	public String queryModuleListByCondition(String createTime,String smsModuleType){
		String sql = new SQL(){
			{
				SELECT("T.*,D.DICT_NAME AS SMS_MODULE_TYPE_NAME");
				FROM("RHZX_SMS_MODULE T LEFT JOIN (SELECT * FROM RHZX_DIM_COM_DICT WHERE DICT_COL_NAME='"+Constant.SMS_MODELE_DICT_TYPE+"') D "
					+" ON D.DICT_ID=T.SMS_MODULE_TYPE ");
				if(!PubMethod.isEmpty(createTime)){
					WHERE("TO_CHAR(T.CREATE_TIME,'yyyy-MM-dd') ='"+createTime+"'");
				}
				if(!PubMethod.isEmpty(smsModuleType)){
					WHERE("T.SMS_MODULE_TYPE ='"+smsModuleType+"'");
				}
				ORDER_BY("T.CREATE_TIME DESC");
			}
		}.toString();
		return sql;
	}
	
	public String getEntityById(Integer id){
			String sql = new SQL(){
				{
					SELECT("T.*,D.DICT_NAME AS SMS_MODULE_TYPE_NAME");
					FROM("RHZX_SMS_MODULE T LEFT JOIN (SELECT * FROM RHZX_DIM_COM_DICT WHERE DICT_COL_NAME='"+Constant.SMS_MODELE_DICT_TYPE+"') D "
						+" ON D.DICT_ID=T.SMS_MODULE_TYPE ");
					WHERE("T.ID ="+id);
					//ORDER_BY("T.ID DESC");
				}
			}.toString();
			return sql;
		}
	}
