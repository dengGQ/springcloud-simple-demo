package com.dgq.quartz.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgq.quartz.commons.Exception.BusinessException;
import com.dgq.quartz.commons.service.BaseAbstractServiceImpl;
import com.dgq.quartz.entity.TaskExecuteRecord;
import com.dgq.quartz.mapper.TaskExecuteRecordMapper;
import com.dgq.quartz.service.TaskExecuteRecordService;

@Service
public class TaskExecuteRecordServiceImpl extends BaseAbstractServiceImpl<TaskExecuteRecord	, TaskExecuteRecordMapper> implements TaskExecuteRecordService{
	
	@Autowired
	public TaskExecuteRecordMapper mapper;
	
	@Override
	public TaskExecuteRecord addTaskExecuteRecord(TaskExecuteRecord record) throws BusinessException{
		try {
			List<TaskExecuteRecord> records = mapper.find(record.getTaskId());
			if(records.size() < 5) {
				
				record = new TaskExecuteRecord();
				
				record.setCreateTime(LocalDateTime.now());
				record.setTaskId(record.getTaskId());
				record.setLastExecuteTime(LocalDateTime.now());
				
				record.setUrl(record.getUrl());
				record.setCronExpression(record.getCronExpression());
				record.setExecuteParameter(record.getExecuteParameter());
				mapper.insert(record);
			}else{
				record = records.get(0);
				record.setLastExecuteTime(LocalDateTime.now());
				record.setUrl(record.getUrl());
				record.setCronExpression(record.getCronExpression());
				record.setExecuteParameter(record.getExecuteParameter());
				mapper.updateByPrimaryKey(record);
			}
			return record;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void updateTaskExecuteRecord(TaskExecuteRecord record) {
		
	}
}
