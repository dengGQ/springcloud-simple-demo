package com.fotic.common.quartz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fotic.management.sms.entity.RhzxSubmtSmsTime;
import com.fotic.sms.SmsBody;

import tk.mybatis.mapper.common.Mapper;

/*
* @Description: public class quartzDao{ }
* @author dgq 
* @date 2018年5月9日
*/
public interface QuartzDao extends Mapper<SmsBody>{
	
	@Select("select j.sched_name schedName, j.job_name jobName, j.job_group jobGroup, j.job_class_name jobClassName, t.trigger_state triggerState, c.cron_expression cronExpression ,c.state state,c.startdate startdate,c.stopdate stopdate from qrtz_job_details j left join qrtz_triggers t on j.job_name = t.job_name and j.job_group = t.job_group and j.sched_name = t.sched_name left join qrtz_cron_triggers c on t.trigger_name = c.trigger_name and t.trigger_group = c.trigger_group and t.sched_name = c.sched_name")
	public List<Map<String, Object>> jobList();
	
	@Select("update QRTZ_CRON_TRIGGERS set startdate=#{jobName} ,state ='1' where trigger_name = 'push_overduedata_trigger'")
	public String startJob(@Param(value="jobName")String jobName);
	
	@Select("update QRTZ_CRON_TRIGGERS set stopdate=#{jobName} , state ='2' where trigger_name = 'push_overduedata_trigger'")
	public String stopJob(@Param(value="jobName")String jobName);
	@Insert("insert into RHZX_SUBMT_SMS_TIME (starttime,num) values(#{jobName},#{num})")
	public Boolean insertJob(@Param(value="jobName")String jobName,@Param(value="num")String num);
	@Select("update RHZX_SUBMT_SMS_TIME set stoptime = #{jobName} where starttime is not null AND stoptime is null")
	public String insertJob2(@Param(value="jobName")String jobName);
	@Select("select max(num) from RHZX_SUBMT_SMS_TIME")
	public Integer insertJob3();
	@Select("select starttime, stoptime from RHZX_SUBMT_SMS_TIME")
	public List<RhzxSubmtSmsTime> insertJob4(@Param(value="firdate")String firdate, @Param(value="endate")String endate);



	
	


	
}
