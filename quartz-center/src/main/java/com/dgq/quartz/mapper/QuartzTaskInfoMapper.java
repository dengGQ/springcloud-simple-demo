package com.dgq.quartz.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dgq.quartz.commons.mapper.CommonMapper;
import com.dgq.quartz.entity.QuartzTaskInfo;

public interface QuartzTaskInfoMapper extends CommonMapper<QuartzTaskInfo>{
	
	@Select("select * from quartz_task_info q where q.task_no = #{taskNo} and executor = #{executor}")
	QuartzTaskInfo findOne(@Param("taskNo")String taskNo, @Param("executor")String executor);
}
