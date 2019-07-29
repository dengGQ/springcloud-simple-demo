package com.fotic.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.SmsBody;
import com.fotic.sms.provider.SmsBodyProvider;

import tk.mybatis.mapper.common.Mapper;

/*
* @Description: public class SmsBodyMapper{ }
* @author dgq 
* @date 2018年4月18日
*/
public interface SmsBodyMapper extends Mapper<SmsBody>{
	/**
	 * 持久化一条实体
	 * @param sb
	 * @return
	 */
	@Insert("insert into sms_body(id, phone, sms_content, send_date) values(#{id,javaType=Integer,jdbcType=INTEGER}, #{phone, javaType=String, jdbcType=VARCHAR}, #{smsContent, javaType=String, jdbcType=VARCHAR}, #{sendDate, javaType= java.util.Date, jdbcType=DATE})")
	@SelectKey(keyProperty="id", before=true, statement="SELECT APP_SEQUENCE.NEXTVAL FROM DUAL", resultType =java.lang.Integer.class)
	Integer inserEntity(SmsBody sb);
	
	
	/**
	 * 批量持久化
	 * @param sbs
	 */
	@InsertProvider(type=SmsBodyProvider.class, method="batchInsertSmsBody")
	public void batchInsertEntity(@Param("list")List<SmsBody> sbs);
	
	/**
	 * 查询待发送的短信
	 * @return
	 */
	@Select("select * from sms_body where send_date <= current_date and status = 0 for update")
	List<SmsBody> querySmsBody();
	@Select("select * from sms_body where send_date <= current_date and status = 1 AND account = #{account} AND sms_code='00'  AND to_char(send_date ,'yyyy-mm')= #{sendDate}")
	List<SmsBody> querySmsBodys(@Param(value="account") String account,@Param(value="sendDate") String sendDate);
	
	@Select("select * from RHZX_SMS_SEND_DETAIL where iou_no = #{iouNo} AND to_char(send_date,'YYYY-mm-dd hh24:mi:ss')>=#{firstday} AND to_char(send_date,'YYYY-mm-dd hh24:mi:ss')<=#{lastday} AND sms_code ='00'")
	List<SmsSendDetail> querySendDetail(@Param(value="iouNo") String iouNo,@Param(value="firstday")String firstday,@Param(value="lastday")String lastday);

	@Select(" SELECT DISTINCT  le.sms_module_content smsSendContent FROM RHZX_SMS_SETTING_RULE ru ,RHZX_PER_CONTRACT ct,RHZX_SMS_MODULE le WHERE RU.PROJ_ID <> 'CSV导入' AND ru.proj_id=ct.proj_id AND ru.module_id = le.id AND le.status = 2 AND ru.proj_id = (SELECT proj_id　FROM v_Mfs_Project vt WHERE vt.PROJ_NAME = #{projName})")
	String querySendSmsMontent(@Param(value="projName") String projName);
	/**
	 * 更新待发送短信的status和smsCode
	 * @param sb
	 */
	@Update("update sms_body set status = #{status}, sms_code = #{smsCode}, repeat_send_count = #{repeatSendCount} where id = #{id}")
	void updateEntity(SmsBody sb);
	
	@Update("update sms_body set status = 1 where to_char(id) = #{id}")
	void updateEntitys(@Param(value="id")String id);
}
