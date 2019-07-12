package com.fotic.webproject.jpadata.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_HR_COMMON")
	private Long id;
	
	@ApiModelProperty(value="创建时间", hidden=true)
	private Date createTime;

	@ApiModelProperty(value="最后修改时间", hidden=true)
    private Date lastModifiedTime;
}
