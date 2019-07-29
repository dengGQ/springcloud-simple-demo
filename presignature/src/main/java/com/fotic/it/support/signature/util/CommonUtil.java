package com.fotic.it.support.signature.util;

import com.fotic.it.support.signature.constant.Constant;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @Author: mfh
 * @Date: 2019-05-17 21:19
 **/
public class CommonUtil {
    /**
     * 根据给定路径后缀判断是否为文件夹
     * @param path  被检测的路径
     * @return      文件夹：true；非文件夹：false
     */
    public static boolean isFolder(String path) {
        if (Objects.nonNull(path)) {
            return StringUtils.cleanPath(path).endsWith(Constant.FORWARD_SLASH);
        }
        return false;
    }

    /**
     * 根据文件名后缀判断是否为 Pdf 类型文件
     * @param path  被检测的路径
     * @return      文件：true；非文件：false
     */
    public static boolean isPdf(String path) {
        return path.toLowerCase().endsWith(Constant.PDF_SUFFIX);
    }

    public static String array2Str(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int pageNum : arr) {
            sb.append(pageNum).append(",");
        }
        if (StringUtils.endsWithIgnoreCase(String.valueOf(sb), ",")) {
            return sb.substring(0, sb.length() - 1);
        }
        return String.valueOf(sb);
    }
}
