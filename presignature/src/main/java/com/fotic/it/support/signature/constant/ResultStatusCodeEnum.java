package com.fotic.it.support.signature.constant;

/**
 * @Author: mfh
 * @Date: 2019-04-26 14:25
 **/
public enum ResultStatusCodeEnum {
    /**
     * 签章成功
     */
    SUCCESS(0, "正常"),
    /**
     * 电子印章没有系统权限
     */
    NO_SYSTEM_PERMISSIONS(1, "电子印章没有系统权限"),
    /**
     * 访问的IP地址没有在系统中授权
     */
    ACCESS_IP_UNAUTHORIZED(2, "访问的IP地址没有在系统中授权"),
    /**
     * 签章编号不是数字
     */
    ILLEGAL_SIGNATURE_NO(3, "签章编号不是由纯数字组成"),
    /**
     * 电子印章安全码不正确
     */
    ERROR_SIGNATURE_SECURITY_CODE(4, "电子印章安全码不正确"),
    /**
     * 请求异常
     */
    REQUEST_EXCEPTION(99, "异常"),
    ILLEGAL_PARAMETER(5, "非法参数"),

    ILLEGAL_INPUT_PATH(-1, "输入路径为空"),
    ILLEGAL_OUTPUT_PATH(-2, "输出路径为空"),
    ILLEGAL_SIGNATURE_RULES(-3, "签章规则对象集合为空"),
    ILLEGAL_ATTRIBUTE_VALUE(4, "签章规则对象属性值存在错误，需要检查的值有 签章编号[sealIndex]、定位方式[sealPositionByText]、签章方式[sealType]"),
    ERROR_SIGNATURE_INFO_DATA(-5, "签章信息表 SIGNATURE_INFO 数据有误，针对一个 signId 存在多条数据的情况或者查询出的数据为空"),
    ILLEGAL_SEAL_POSITION(-6, "签章位置异常，需要盖章的文字未找到或者被定位的文字是一张图片或者提供的像素信息错误"),
    VERIFY_SUCCESS(10, "验证成功，可以继续往下执行"),
    ERROR_CERTIFICATE(-7, "许可证书相关信息存在异常情况"),
    ERROR_FILE_TYPE(-8, "错误的文件类型"),
    SIGNATURE_TASK_TIMEOUT(-9, "签章任务超时"),
    SIGNATURE_TASK_INTERRUPTED(-10, "签章任务被打断"),
    ILLEGAL_SIGNATURE_TYPE(-11, "签章方式既不是文字定位也不是像素定位"),
    ILLEGAL_SIGNATURE_INDEX(-12, "签章编号异常"),
    ILLEGAL_PATH(-13, "所给定的路径是没有以 / 结尾，并且也不是 .pdf 结尾"),
    NO_EXIST_FILE(-14, "单文件不存在"),
    ERROR_MKD(-15, "在 ftp 服务器创建路径文件夹异常"),
    MISMATCH_PATH(-16, "输入路径与输出路径不匹配，即输入路径是文件夹，但输出路径非文件夹"),
    ERROR_FILE(-17, "pdf 文件本身存在问题"),
    ERROR_FTP_CONNECTION(-18, "FTP 服务器连接异常"),
    ERROR_RETRIEVE_FILE(-19, "文件不存在或者文件名存在乱码"),
    ERROR_STORE_FILE(-20, "路径没有正确的指向文件"),
    EMPTY_FILENAME(-21, "空文件名"),
    LOG_OUTPUT_TRANS_CODE(-22, "log 日志输出转码异常，不影响正常签章")
    ;
    private int code;
    private String description;

    ResultStatusCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public static String getDescription(int code) {
        for (ResultStatusCodeEnum eachState : values()) {
            if (code == eachState.code) {
                return eachState.description;
            }
        }
        return ILLEGAL_PARAMETER.description;
    }
}