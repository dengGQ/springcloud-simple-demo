package com.dgq.quartz.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Table;

import com.dgq.quartz.commons.annotation.QueryType;
import com.dgq.quartz.commons.annotation.QueryTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * 定时任务执行记录表
 * @author dgq
 * @date 2019年7月26日
 */
@Data
@ToString
@Table(name = "task_execute_record")
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecuteRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@ApiModelProperty(notes = "任务Id, taskNo-executor")
	@QueryType(QueryTypeEnum.LIKE)
	private String taskId;
	
	@QueryType(QueryTypeEnum.LIKE)
	@ApiModelProperty(notes = "任务名称")
	private String taskName;
	
	@ApiModelProperty(notes = "最后执行时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastExecuteTime;
	
	@ApiModelProperty(notes = "执行状态")
	private Integer executeStatus; // 0: 成功，1：失败  2：异常
	
	@ApiModelProperty(notes = "失败原因")
	private String failReason;
	
	@ApiModelProperty(notes = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	
	/*
	 * @ApiModelProperty(notes = "请求地址") private String url;
	 * 
	 * @ApiModelProperty(notes = "执行参数") private String executeParameter;
	 */
	
	@ApiModelProperty(notes = "触发规则")
	private String cronExpression;

	public TaskExecuteRecord(String taskId, String taskName, String cronExpression) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.cronExpression = cronExpression;
	}
}
