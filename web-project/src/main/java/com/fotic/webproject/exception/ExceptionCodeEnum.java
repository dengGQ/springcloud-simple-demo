package com.fotic.webproject.exception;

import lombok.Getter;

/**
 * 异常信息枚举类
 *
 * @Author denggq
 * @ create 2018-11-09 16:12
 */
@Getter
public enum ExceptionCodeEnum {
    /** 服务器内部错误 */
    EXCEPTION_UNKNOW("000000", "未知异常,请联系管理员！"),
    EXCEPTION_HTTP_STATUS_400("400","参数传递错误"),
    EXCEPTION_HTTP_STATUS_404("404","资源未找到异常"),
    EXCEPTION_HTTP_STATUS_405("405","请求方法不支持"),
    EXCEPTION_HTTP_STATUS_406("406","不支持contentType类型异常, 请联系管理员!"),
    EXCEPTION_HTTP_STATUS_415("415","使用的contentType类型错误, 请联系管理员!"),
    EXCEPTION_HTTP_STATUS_500("500","运行时异常,请联系管理员!"),
    EXCEPTION_NULL_POINTER("S00001","空指针异常"),
    EXCEPTION_CLASS_CAST("S00002","数据类型转换异常, 请联系管理员!"),
    EXCEPTION_IO("S00003","IO异常, 请联系管理员!"),
    EXCEPTION_NO_SUCH_METHOD("S00004","未知方法异常, 请联系管理员!"),
    EXCEPTION_INDEX_OUT_OF_BOUNDS("S00005","数组越界异常, 请联系管理员!");

    private String errorCode;
    private String errorMsg;

    ExceptionCodeEnum(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
