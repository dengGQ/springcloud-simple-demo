package com.fotic.management.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.fotic.common.constant.Constant;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.management.sms.providers.SmsSendDetailProvider;
import com.fotic.sms.SmsBody;

import tk.mybatis.mapper.common.Mapper;
/**
 * 
 * @author h
 *
 */
public interface SmsSendDetailMapper extends Mapper<SmsSendDetail>{

	@SelectProvider(type=SmsSendDetailProvider.class,method="querySmsSendDetail")
	public List<SmsSendDetail> querySmsSendDetail(SmsSendDetail smsSendDetail);
	
	@Select("select a.*,b.SMS_CODE_DETAIL,CASE WHEN a.SMS_CODE='00' THEN '成功' ELSE '失败' END AS SEND_STATUS,t.sms_module_type,dm.dict_name as sms_module_type_name from rhzx_sms_send_detail a left join RHZX_SMS_MODULE t on t.id=a.MODULE_ID left join (select * from RHZX_DIM_COM_DICT where dict_col_name='"+Constant.SMS_MODELE_DICT_TYPE+"') dm on dm.dict_id=t.sms_module_type left join RHZX_SMS_CODE b on a.sms_code = b.sms_code where a.id = #{id}")
	public SmsSendDetail querySmsSendDetailById(@Param(value="id")Integer id);
	
	@SelectProvider(type=SmsSendDetailProvider.class,method="querySmsSendStatistical")
	public List<SmsSendDetail> querySmsSendStatistical(SmsSendDetail smsSendDetail);
	
	//and  to_char(m.update_time,'yyyy-mm-dd') < to_char(current_date, 'yyyy-mm-dd')
	@Select("select a.*, m.sms_module_content "
			+ "as smsSendContent, m.status,"
			+ " B.co_org_name as coOrgName,"
			+ "r.module_id,m.sms_module_type "
			+ "from (SELECT DISTINCT B.NAME as custName, "
			+ "CASE WHEN C.CO_ORG_CODE IS NULL THEN "
			+ "CASE WHEN A.ACCOUNT LIKE 'C%' THEN '008' "
			+ "WHEN A.ACCOUNT LIKE 'PPD%' THEN '015' "
			+ "WHEN A.ACCOUNT LIKE 'ZJ%' THEN '011' "
			+ "WHEN A.ACCOUNT LIKE '0L%' THEN '011' "
			+ "WHEN A.ACCOUNT LIKE 'LX%' THEN '019' "
			+ "WHEN A.ACCOUNT LIKE 'SMY%' THEN '006' "
			+ "WHEN A.ACCOUNT LIKE 'P%' THEN '002' "
			+ "WHEN A.ACCOUNT LIKE 'YG%' THEN '002' "
			+ "WHEN A.ACCOUNT LIKE 'XHY%' THEN '045'  "
			+ "WHEN A.ACCOUNT LIKE 'YFHX%' THEN '013' "
			+ "ELSE '001' END ELSE C.CO_ORG_CODE END coOrgCode, "
			+ "CASE WHEN C.PROJ_ID IS NULL THEN 'CSV导入' "
			+ "ELSE TO_CHAR(C.PROJ_ID) END projId, "
			+ "CASE WHEN C.PROD_CODE IS NULL THEN 'CSV导入' "
			+ "ELSE C.PROD_CODE END prodCode, "
			+ "CASE WHEN B.GENDER='1' THEN '男士' "
			+ "WHEN B.GENDER='2' THEN '女士' END AS GENDER, B.MOBILETEL as phone, B.CERTNO as CREDNO,A.ACCOUNT as iouNo,A.Insert_Dttm as insertDttm, D.CUST_CODE as custCode"
			+ " FROM RHZX_SUBMT_PER_TRADE_HIS A "
			+ "	JOIN RHZX_SUBMT_PER_PERSON_HIS B ON A.CERTNO=B.CERTNO AND A.ACCOUNTSTAT='2' "
			+ "LEFT JOIN RHZX_PER_CONTRACT C ON A.ACCOUNT='XD'||C.IOU_NO LEFT"
			+ " JOIN FDM.F_MFS_CUSTOM@ODS_DWDB D ON A.CERTNO=D.CRED_CODE "
			+ "where (A.Insert_Dttm <= TO_DATE(#{currentDate }, 'yyyy-mm-dd hh24:mi:ss'))"
			+ "AND (A.Insert_Dttm > TO_DATE(#{theDayBefore }, 'yyyy-mm-dd hh24:mi:ss'))) a inner join RHZX_SMS_SETTING_RULE r on r.co_org_code = a.coOrgCode and r.proj_id = a.projId and r.prod_id = a.prodCode left join RHZX_SMS_MODULE m on r.module_id = m.id LEFT JOIN FDM.F_MFS_COORG@ODS_DWDB B ON a.coOrgCode=B.CO_ORG_CODE where m.status = 2")
	public List<SmsSendDetail> queryOverdueUserInfo(@Param("currentDate")String currentDate, @Param("theDayBefore")String theDayBefore);
	@SelectProvider(type=SmsSendDetailProvider.class,method="querySmsBody")
	public List<SmsBody> querySmsBodyList(SmsBody smsBody ) ;

	@InsertProvider(type=SmsSendDetailProvider.class, method="batchInsertSmsSendDetail")
	public void insertEntity(@Param("list")List<SmsSendDetail> sendDetails);
	
	@Select("select * from RHZX_SMS_SEND_DETAIL where uuid = #{uuid}")
	public SmsSendDetail querySmsSendDetailByUuid(String uuid);
	
	@Update("UPDATE RHZX_SMS_SEND_DETAIL SET SMS_CODE = #{smscode} WHERE UUID=#{uuid}")
	public void editSmsSendDetail(@Param("smscode")String smscode, @Param("uuid")String uuid);
	
	@Update("UPDATE RHZX_SMS_SEND_DETAIL SET SMS_CODE = #{smscode},repeat_send_count = #{repeatSendCount} WHERE UUID=#{uuid}")
	public void editSmsSendDetailForSmscodeAndRepeatSendCountByUUID(@Param("smscode")String smscode, @Param("repeatSendCount")Integer repeatSendCount, @Param("uuid")String uuid);
	
	@Update("UPDATE SMS_BODY SET STATUS = '3' ,sms = #{sms} WHERE TO_CHAR(CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') = #{createtime} "
			+ "AND CERTNO = #{credNo} AND ACCOUNT = #{account} AND STATUS = '0'")
	public Boolean upDateSms(@Param("sms")String sms, @Param("account")String account, @Param("createtime")String createtime,@Param("credNo") String credNo);


}
