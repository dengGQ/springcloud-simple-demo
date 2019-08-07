package com.dgq.quartz.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QuartzTaskInfoDTO {
	private String taskNo;
	private String executor;
}
