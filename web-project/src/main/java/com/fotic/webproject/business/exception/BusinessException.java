package com.fotic.webproject.business.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorCode;
    public BusinessException(String errorCode, String errorMsg, Throwable cause){
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public BusinessException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message){
        super(message);
    }

}
