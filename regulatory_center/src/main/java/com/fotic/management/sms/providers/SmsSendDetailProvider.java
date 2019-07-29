package com.fotic.management.sms.providers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.alibaba.druid.sql.visitor.functions.Left;
import com.fotic.common.constant.Constant;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.PubMethod;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.SmsBody;

public class SmsSendDetailProvider {

	/**
	 * 查询短信明细
	 * @param smsSendDetail
	 * @return
	 */
	public String querySmsSendDetail(SmsSendDetail smsSendDetail) {

		String sql = new SQL() {
			{
				SELECT("a.*,case when a.proj_id = 'CSV导入' then a.proj_id else b.PROJ_NAME end as PROJ_NAME,c.CO_ORG_NAME,dm.DICT_NAME as sms_module_type_name,"
						+ "case when a.prod_code = 'CSV导入' then a.prod_code else d.PROD_NAME end as PROD_NAME,"
						+ "e.SMS_CODE_DETAIL,CASE WHEN a.SMS_CODE='00' THEN '成功' ELSE '失败' END AS SEND_STATUS");
				FROM("RHZX_SMS_SEND_DETAIL a left join V_MFS_PROJECT b on a.PROJ_ID = to_char(b.PROJ_ID) "
						+ "left join V_MFS_ORG c on a.CO_ORG_CODE = c.CO_ORG_CODE "
						+ "left join V_MFS_PRODUCT d on a.prod_code = to_char(d.PROD_CODE) "
						+ "left join RHZX_SMS_CODE e on a.SMS_CODE = e.SMS_CODE "
						+ "left join RHZX_SMS_MODULE t on t.id=a.MODULE_ID left join (select * from RHZX_DIM_COM_DICT where dict_col_name='"
						+ Constant.SMS_MODELE_DICT_TYPE
						+ "') dm "
						+ "on dm.dict_id=t.sms_module_type ");
				/*
				 * if(!"csv导入".equalsIgnoreCase(smsSendDetail.getProjId()) &&
				 * !"csv导入".equalsIgnoreCase(smsSendDetail.getProdCode())){
				 * WHERE(
				 * "a.PROJ_ID=to_char(b.PROJ_ID) AND a.prod_code=to_char(d.PROD_CODE)"
				 * ); }
				 * WHERE("a.CO_ORG_CODE=c.CO_ORG_CODE AND a.SMS_CODE=e.SMS_CODE"
				 * ); WHERE(
				 * "to_char(a.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate-1,'yyyy-MM-dd')"
				 * );
				 */
				WHERE("to_char(a.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate,'yyyy-MM-dd') AND A.SMS_CODE IS NOT NULL");
				if (!PubMethod.isEmpty(smsSendDetail.getId())) {
					WHERE("a.ID = " + smsSendDetail.getId());
				}
				if (!PubMethod.isEmpty(smsSendDetail.getCoOrgCode())) {
					WHERE("a.CO_ORG_CODE = '" + smsSendDetail.getCoOrgCode()
							+ "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getProjId())) {
					WHERE("a.PROJ_ID = '" + smsSendDetail.getProjId() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getStartDate())) {
					String startDateStr = DateUtils.formatDate(
							smsSendDetail.getStartDate(), "yyyy-MM-dd");
					WHERE("a.SEND_DATE >= to_date('" + startDateStr
							+ "','yyyy-MM-dd')");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getEndDate())) {
					String endDateStr = DateUtils.formatDate(
							smsSendDetail.getEndDate(), "yyyy-MM-dd");
					WHERE("a.SEND_DATE <= to_date('" + endDateStr
							+ "','yyyy-MM-dd')");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getProdCode())) {
					WHERE("a.PROD_CODE = '" + smsSendDetail.getProdCode() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getSmsSendContent())) {
					WHERE("a.SMS_SEND_CONTENT = '"
							+ smsSendDetail.getSmsSendContent() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getCustCode())) {
					WHERE("a.CUST_CODE = '" + smsSendDetail.getCustCode() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getCustName())) {
					WHERE("a.CUST_NAME = '" + smsSendDetail.getCustName() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getCredType())) {
					WHERE("a.CRED_TYPE = '" + smsSendDetail.getCredType() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getCredNo())) {
					WHERE("a.CRED_NO = '" + smsSendDetail.getCredNo() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getPhone())) {
					WHERE("a.PHONE = '" + smsSendDetail.getPhone() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getIouNo())) {
					WHERE("a.IOU_NO = '" + smsSendDetail.getIouNo() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getSmsModuleTypeName())) {
					WHERE("dm.dict_id = '"
							+ smsSendDetail.getSmsModuleTypeName() + "'");
				}
				if (!PubMethod.isEmpty(smsSendDetail.getSmsCode())
						&& !"0".equals(smsSendDetail.getSmsCode())) {
					if ("00".equals(smsSendDetail.getSmsCode())) {
						WHERE("a.SMS_CODE = '00'");
					} else {
						WHERE("a.SMS_CODE != '00'");
					}
				}
				ORDER_BY("a.id desc");
			}
		}.toString();

		return sql;
	}

	public String querySmsSendDetailById(Integer id) {

		String sql = new SQL() {
			{
				SELECT("a.*,case when a.proj_id = 'CSV导入' then a.proj_id else b.PROJ_NAME end as PROJ_NAME,c.CO_ORG_NAME,dm.DICT_NAME as sms_module_type_name,"
						+ "case when a.prod_code = 'CSV导入' then a.prod_code else d.PROD_NAME end as PROD_NAME,"
						+ "e.SMS_CODE_DETAIL,CASE WHEN a.SMS_CODE='00' THEN '成功' ELSE '失败' END AS SEND_STATUS");
				FROM("RHZX_SMS_SEND_DETAIL a left join V_MFS_PROJECT b on a.PROJ_ID = to_char(b.PROJ_ID) "
						+ "left join V_MFS_ORG c on a.CO_ORG_CODE = c.CO_ORG_CODE "
						+ "left join V_MFS_PRODUCT d on a.prod_code = to_char(d.PROD_CODE) "
						+ "left join RHZX_SMS_CODE e on a.SMS_CODE = e.SMS_CODE "
						+ "left join RHZX_SMS_MODULE t on t.id=a.MODULE_ID left join (select * from RHZX_DIM_COM_DICT where dict_col_name='"
						+ Constant.SMS_MODELE_DICT_TYPE
						+ "') dm "
						+ "on dm.dict_id=t.sms_module_type ");
				WHERE("to_char(a.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate,'yyyy-MM-dd') AND A.SMS_CODE IS NOT NULL");

				ORDER_BY("a.id desc");
			}
		}.toString();

		return sql;
	}

	/**
	 * 查询统计台账信息
	 * 
	 * @return
	 */
	public String querySmsSendStatistical(SmsSendDetail detail) {
		String string = detail.getStartMonthStr();
		String sql = new SQL() {
			{
				StringBuffer sb = new StringBuffer();
				sb.append("where to_char(a.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate,'yyyy-MM-dd')");
				if (!PubMethod.isEmpty(detail.getCoOrgCode())) {
					sb.append("and a.co_org_code='" + detail.getCoOrgCode()
							+ "'");
				}
				if (!PubMethod.isEmpty(detail.getProjId())) {
					sb.append(" and a.proj_id='" + detail.getProjId() + "'");
				}
				if (!PubMethod.isEmpty(detail.getProdCode())) {
					sb.append(" and a.prod_code='" + detail.getProdCode() + "'");
				}
				if (!PubMethod.isEmpty(detail.getStartMonthStr())) {
					sb.append(" and a.send_date>=to_date('"
							+ detail.getStartMonthStr() + "','YYYYMM')");
				}
				if (!PubMethod.isEmpty(detail.getEndMonthStr())) {
					sb.append(" and a.send_date<=to_date('"
							+ detail.getEndMonthStr() + "','YYYYMM')");
				}
				SELECT("a1.*, nvl(b1.send_success_num,0) as send_success_num, nvl(send_total_num-nvl(b1.send_success_num,0),0) as send_fail_num");
				FROM("(select to_char(a.send_date, 'YYYYMM') as send_month_str,"
						+ "a.co_org_code,a.proj_id,a.prod_code,b.co_org_name,c.PROJ_NAME as PROJ_NAME,"
						+ "PROD_NAME as PROD_NAME,count(*) as send_total_num "
						+ "from RHZX_SMS_SEND_DETAIL a left join V_MFS_ORG b on a.co_org_code = b.co_org_code "
						+ "left join V_MFS_PROJECT c on a.proj_id = to_char(c.PROJ_ID) "
						+ "left join V_MFS_PRODUCT d on a.prod_code = to_char(d.PROD_CODE) "
						+ sb.toString()
						+ " group by to_char(a.send_date, 'YYYYMM'),b.co_org_name,a.co_org_code,c.PROJ_NAME,"
						+ "a.proj_id,d.PROD_NAME,a.prod_code order by send_month_str) a1 "
						+ "left join (select b.co_org_code,b.proj_id,b.prod_code,to_char(b.send_date, 'YYYYMM') as send_month_str,"
						+ "count(*) as send_success_num from RHZX_SMS_SEND_DETAIL b where b.sms_code = '00' and to_char(b.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate-1,'yyyy-MM-dd') "
						+ "group by to_char(b.send_date, 'YYYYMM'),b.co_org_code,b.proj_id,b.prod_code) b1 "
						+ "on a1.co_org_code = b1.co_org_code and (a1.proj_id = b1.proj_id or (a1.proj_id is null and b1.proj_id is null)) "
						+ "and (a1.prod_code = b1.prod_code or (a1.prod_code is null and b1.prod_code is null)) and a1.send_month_str = b1.send_month_str "
						+ "left join (select c.co_org_code,c.proj_id,c.prod_code,to_char(c.send_date, 'YYYYMM') as send_month_str,sum(c.REPEAT_SEND_COUNT) as send_fail_num "
						+ "from RHZX_SMS_SEND_DETAIL c where c.sms_code != '00' and to_char(c.SEND_DATE,'yyyy-MM-dd')<=to_char(sysdate-1,'yyyy-MM-dd') group by to_char(c.send_date, 'YYYYMM'),c.co_org_code,"
						+ "c.proj_id,c.prod_code) c1 on a1.co_org_code = c1.co_org_code and (a1.proj_id = c1.proj_id or "
						+ "(a1.proj_id = 'CSV导入' and c1.proj_id = 'CSV导入')) and (a1.prod_code = c1.prod_code or (a1.prod_code = 'CSV导入' and c1.prod_code = 'CSV导入')) "
						+ "and a1.send_month_str = c1.send_month_str");
			}
		}.toString();
		return sql;
	}

	/**
	 * 持久化
	 * 
	 * @param map
	 * @return
	 */
	public String batchInsertSmsSendDetail(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<SmsSendDetail> smsSends = (List<SmsSendDetail>) map.get("list");
		StringBuilder sb = new StringBuilder(
				"insert into RHZX_SMS_SEND_DETAIL(" + "id," + "module_id, "
						+ "CO_ORG_CODE, " + "PROJ_ID, " + "PROD_CODE,"
						+ "send_date, " + "sms_send_content, " + "cust_name, "
						+ "phone, " + "sms_code, " + "cust_code, " + "iou_no, "
						+ "gender, " + "uuid, " + "cred_type, "
						+ "cred_no) (select APP_SEQUENCE.NEXTVAL, cd.* from (");
		MessageFormat mf = new MessageFormat(
				"select "
						+ "#'{'list[{0}].moduleId,javaType=integer,jdbcType=INTEGER'}' as module_id,"
						+ "#'{'list[{0}].coOrgCode,javaType=string,jdbcType=VARCHAR'}' as b,"
						+ "#'{'list[{0}].projId,javaType=string,jdbcType=VARCHAR'}' as c, "
						+ "#'{'list[{0}].prodCode,javaType=string,jdbcType=VARCHAR'}' as d,"
						+ "current_date as e, "
						+ "#'{'list[{0}].smsSendContent,javaType=string,jdbcType=VARCHAR'}' as f, "
						+ "#'{'list[{0}].custName,javaType=string,jdbcType=VARCHAR'}' as g, "
						+ "#'{'list[{0}].phone, javaType=string,jdbcType=VARCHAR'}' as j, "
						+ "#'{'list[{0}].smsCode, javaType=string,jdbcType=VARCHAR'}' as k, "
						+ "#'{'list[{0}].custCode, javaType=string,jdbcType=VARCHAR'}' as l, "
						+ "#'{'list[{0}].iouNo, javaType=string,jdbcType=VARCHAR'}' as m, "
						+ "#'{'list[{0}].gender, javaType=string,jdbcType=VARCHAR'}' as n, "
						+ "#'{'list[{0}].uuid,javaType=string,jdbcType=VARCHAR'}' as q,  "
						+ "#'{'list[{0}].credType,javaType=string,jdbcType=VARCHAR'}' as r, "
						+ "#'{'list[{0}].credNo,javaType=string,jdbcType=VARCHAR'}' as s "

						+ "from dual");
		for (int i = 0; i < smsSends.size(); i++) {
			sb.append(mf.format(new Object[] { i }));
			if (i < smsSends.size() - 1) {
				sb.append(" union all ");
			}
		}
		return sb.append(") cd)").toString();
	}
	
	public String querySmsBody(SmsBody smsBody) {
		
		String sql = new SQL(){
			{
				SELECT("sms.cust_name custName ,"
						+ "sms.certno credNo ,"
						+ "sms.phone,"
						+ "sms.account account,"
						+ "sms.sms_content smsContent ,"
						+ "sms.create_time createTime,"
						+ "fdm.co_org_name coOrgCode,"
						+ "sms.proj_id projId,"
						+ "CASE WHEN sms.status = '0' THEN '待发送'"
						+ "WHEN sms.status = '3' THEN '暂缓发送' "
						+ "END AS status ");
				FROM("sms_body sms LEFT JOIN v_Mfs_Org fdm ON sms.co_org_code = fdm.co_org_code ");
				
				WHERE("sms.status <> '1' ");
				if (!PubMethod.isEmpty(smsBody.getCoOrgCode())) {
					WHERE("sms.CO_ORG_CODE = '" + smsBody.getCoOrgCode()
							+ "'");
				}
				if (!PubMethod.isEmpty(smsBody.getProjId())) {
					WHERE("sms.PROJ_ID = '" + smsBody.getProjId() + "'");
				}
				if (!PubMethod.isEmpty(smsBody.getCreateTime())) {
					String startDateStr = DateUtils.formatDate(
							smsBody.getCreateTime(), "yyyy-MM-dd");
					WHERE("sms.SEND_DATE >= to_date('" + startDateStr
							+ "','yyyy-MM-dd')");
				}
				if (!PubMethod.isEmpty(smsBody.getAccount())) {
					/*String startDateStr = DateUtils.formatDate(
							smsBody.getCreateTime(), "yyyy-MM-dd");*/
					WHERE("sms.account = '" + smsBody.getAccount() + "'");
				}
				if (!PubMethod.isEmpty(smsBody.getCredNo())) {
					/*String startDateStr = DateUtils.formatDate(
							smsBody.getCreateTime(), "yyyy-MM-dd");*/
					WHERE("sms.certno = '" + smsBody.getCredNo() + "'");
				}
				if (!PubMethod.isEmpty(smsBody.getPhone())) {
					/*String startDateStr = DateUtils.formatDate(
							smsBody.getCreateTime(), "yyyy-MM-dd");*/
					WHERE("sms.phone = '" + smsBody.getPhone() + "'");
				}
				if (!PubMethod.isEmpty(smsBody.getStatus())) {
					/*String startDateStr = DateUtils.formatDate(
							smsBody.getCreateTime(), "yyyy-MM-dd");*/
					WHERE("sms.status = '" + smsBody.getStatus() + "'");
				}
			}
			
		}.toString();
		return sql;
		
	}
}
