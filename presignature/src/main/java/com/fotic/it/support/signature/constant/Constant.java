package com.fotic.it.support.signature.constant;

/**
 *
 * add by wz 20190516
 * 添加常量类
 */
public final class Constant {
    /**
     * SwaggerConfig使用
     */
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.fotic.it.support.admin";
    public static final String VERSION = "1.0.0";

    /**
     * 软通
     */
    public static final String RT = "rt";
    /**
     * 系统
     */
    public static final String SYS = "sys";
    /**
     * 文件路径
     */
    public static final String FILE_PATH = "filePath";
    /**
     * 文件名称
     */
    public static final String FILE_NAME = "fileName";
    /**
     * 文件Id
     */
    public static final String ID = "id";

    /**
     * 输入文件路径
     */
    public static final String INPUT_PATH = "inputPath";
    /**
     * 输出文件路径
     */
    public static final String OUTPUT_PATH = "outputPath";
    /**
     * 输入文件名称
     */
    public static final String INPUT_FILE_NAME = "inputFileName";
    /**
     * 输出文件名
     */
    public static final String OUTPUT_FILE_NAME = "outputFileName";

    /**
     * 日期格式
     */
    public static final String DATE_PATTERN = "yyyyMMddHHmmss";

    /**
     * 字符编码
     * GBK
     */
    public static final String CHARACTER_ENCODING_GBK = "GBK";

    /**
     * 字符编码
     * ISO-8859-1
     */
    public static final String CHARACTER_ENCODING_8859 = "ISO-8859-1";

    /**
     * server语言编码
     */
    public static final String LANGUAGE_CODE = "zh";

    /**
     * 反斜杠
     */
    public static final String BACK_SLASH = "\\";
    /**
     * 正斜杠
     */
    public static final String FORWARD_SLASH = "/";

    /** 签章方式-文字定位*/
    public static final String SEAL_TYPE_POSITION_TEXT = "0";
    /** 签章方式-像素定位*/
    public static final String SEAL_TYPE_POSITION_PIXEL = "1";

    /**
     * pdf后缀
     */
    public static final String PDF_SUFFIX = ".pdf";

    /**
     * 要签章的文字如果在 pdf 文件中没有找到，会报 DocumentException 异常，此异常信息钟会包含此字符串
     */
    public static final String NOT_FIND_THE_TEXT = "Not find the text";
}
