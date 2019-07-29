package com.dgq.quartz.service;

import com.dgq.quartz.commons.Exception.BusinessException;
import com.dgq.quartz.commons.service.BaseService;
import com.dgq.quartz.entity.TaskExecuteRecord;

public interface TaskExecuteRecordService extends BaseService<TaskExecuteRecord>{

	TaskExecuteRecord addTaskExecuteRecord(TaskExecuteRecord record) throws BusinessException;
	
	void updateTaskExecuteRecord(TaskExecuteRecord record);
}
