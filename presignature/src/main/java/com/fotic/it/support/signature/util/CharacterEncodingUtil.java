package com.fotic.it.support.signature.util;

import com.fotic.it.support.signature.constant.Constant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: mfh
 * @Date: 2019-05-11 15:51
 **/
public class CharacterEncodingUtil {
    /**
     * 将 request 与 response 转换成 gbk 编码
     * @param req
     * @param resp
     * @throws UnsupportedEncodingException
     */
    public static void setGBKCharacterEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(Constant.CHARACTER_ENCODING_GBK);
        resp.setCharacterEncoding(Constant.CHARACTER_ENCODING_GBK);
    }

    public static String gbk2Utf8(String str) throws UnsupportedEncodingException {
        return new String(str.getBytes(Constant.CHARACTER_ENCODING_GBK), StandardCharsets.UTF_8);
    }
}