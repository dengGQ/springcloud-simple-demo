package com.dgq.quartz.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dgq.quartz.commons.annotation.QueryType;
import com.dgq.quartz.commons.annotation.QueryTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @ClassName: 定时任务详细表
 * @author dgq
 * @date 2019年7月26日
 */
@Data
@ToString
@Table(name = "quartz_task_info")
@AllArgsConstructor
@NoArgsConstructor
public class QuartzTaskInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(hidden = true)
	private Long id;

	@ApiModelProperty(notes = "任务编号", example = "1004")
    private String taskNo;

    @ApiModelProperty(notes = "执行方", example = "once")
    @QueryType(QueryTypeEnum.EQUALS)
    private String executor;

    @ApiModelProperty(notes = "任务名称", example = "werrrr")
    @QueryType(QueryTypeEnum.LIKE)
    private String taskName;

    @ApiModelProperty(notes = "触发规则", example = "0 55 9 26 7 ? 2019")
    private String cronExpression;
    
    @ApiModelProperty(notes = "开始时间，结合triggerType使用，只有triggerType为simple时，触发器才会使用该字段")
    @Transient
    private LocalDateTime startDate;
    
    @ApiModelProperty(notes = "触发类型",example = "simple | cron")
    @Transient
    private String triggerType;
    
    @ApiModelProperty(notes = "触发类型", example = "http")
    private String sendType;

    @ApiModelProperty(notes = "请求地址", example = "https://www.baidu.com")
    private String url;

    @ApiModelProperty(notes = "执行参数", example = "{\"id\":\"1234565\"}")
    private String executeParamter;

    @ApiModelProperty(notes = "冻结状态", example = "0")
    private String frozenStatus;
    
    @ApiModelProperty(notes = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @ApiModelProperty(notes = "最后修改时间", hidden = true)
    private LocalDateTime lastModifyTime;

	public QuartzTaskInfo(String taskNo, String executor) {
		super();
		this.taskNo = taskNo;
		this.executor = executor;
	}
}
