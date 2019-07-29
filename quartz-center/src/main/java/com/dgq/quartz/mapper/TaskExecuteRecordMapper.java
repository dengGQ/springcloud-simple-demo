package com.dgq.quartz.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dgq.quartz.commons.mapper.CommonMapper;
import com.dgq.quartz.entity.TaskExecuteRecord;

public interface TaskExecuteRecordMapper extends CommonMapper<TaskExecuteRecord>{

	@Select("select * from task_execute_record q where q.task_id = #{taskId} order by last_execute_time asc")
	List<TaskExecuteRecord> find(@Param("taskId")String taskId);
}
