package com.dgq.quartz.commons.Exception;

import com.dgq.quartz.util.ResultEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private Integer errorCode;

    public BusinessException(Integer errorCode, String errorMsg, Throwable cause){
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public BusinessException(Integer errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message){
        super(message);
    }
    
    public BusinessException(ResultEnum e) {
    	super(e.getMessage());
    	this.errorCode = e.getCode();
    }
}
