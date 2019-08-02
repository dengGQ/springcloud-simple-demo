package com.dgq.quartz.dto;

import com.dgq.quartz.entity.QuartzTaskInfo;
import com.dgq.quartz.entity.TaskExecuteRecord;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class RequestParams {
	
	private QuartzTaskInfo taskInfo;
	
	private TaskExecuteRecord Record;
	
	@ApiParam(defaultValue = "1")
	private int pageNumber;
	@ApiParam(defaultValue = "30")
	private int pageSize;
}
