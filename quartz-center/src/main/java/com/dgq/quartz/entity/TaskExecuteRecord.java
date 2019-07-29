package com.dgq.quartz.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * 定时任务执行记录表
 * @author dgq
 * @date 2019年7月26日
 */
@AllArgsConstructor
@Data
@ToString
@Table(name = "task_execute_record")
public class TaskExecuteRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(hidden = true)
	private Long id;
	
	//taskNo+"-"+executor
	private String taskId;
	
	private LocalDateTime lastExecuteTime;
	
	private Integer executeStatus; // 0: 成功，1：失败  2：异常
	
	private String failReason;
	
	private LocalDateTime createTime;
	
	private String url;
	
	private String executeParameter;
	
	private String cronExpression;

	public TaskExecuteRecord() {
		super();
	}

	public TaskExecuteRecord(String taskId, String url, String executeParameter, String cronExpression) {
		super();
		this.taskId = taskId;
		this.url = url;
		this.executeParameter = executeParameter;
		this.cronExpression = cronExpression;
	}
}
