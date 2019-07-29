package com.dgq.quartz.util;

/**
 * @ClassName ResultEnum
 * @Description 定义接口统一返回的code、message，方便维护
 * @Author simonsfan
 * @Date 2019/1/1
 * Version  1.0
 */
public enum  ResultEnum {
    SUCCESS(200,"success"),
    FAIL(500,"system error"),
    INIT(600,"record init"),

    TASKNO_EXIST(1001,"该任务已经存在"),
    TASKNO_NO_EXIST(1002,"该任务不存在"),
    CRON_INVALID(1003, "调度规则无效"),
    RECORD_COUNT_LIMIT(1004, "同一条任务执行记录达到上限（5条）"),

    PARAM_EMPTY(6001,"参数是空"),


    FROZEN(10001,"FROZEN"),
    UNFROZEN(10002,"UNFROZEN"),

    RUN_NOW_FAIL(7001,"立即运行失败"),

    HTTP(10003,"http"),
    KAFKA(10004,"kafka"),

    UPDATE_FAIL(1002,"更新失败"),
    NO_DATA(1003,"无此定时任务编号");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
