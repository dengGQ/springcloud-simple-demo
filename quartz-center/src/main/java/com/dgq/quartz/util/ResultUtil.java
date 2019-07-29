package com.dgq.quartz.util;


import com.google.gson.Gson;

/**
 * @ClassName ResultUtil
 * @Description TODO
 * @Author simonsfan
 * @Date 2019/1/1
 * Version  1.0	
 */
public class ResultUtil {

    public static <T> String success() {
        return new Gson().toJson(new Result<T>(ResultEnum.SUCCESS));
    }

    public static <T> String success(T t) {
        return new Gson().toJson(new Result<T>(ResultEnum.SUCCESS, t));
    }

    public static <T> String fail() {
        return new Gson().toJson(new Result<T>(ResultEnum.FAIL));
    }
    
    public static <T> String fail(T t) {
        return new Gson().toJson(new Result<T>(ResultEnum.FAIL, t));
    }

    public static <T> String success(Integer code, String message) {
        return new Gson().toJson(new Result<T>(code, message));
    }
    public static <T> String success(Integer code, String message, T t) {
        return new Gson().toJson(new Result<T>(code, message, t));
    }
    
    public static <T> String fail(Integer code, String message) {
        return new Gson().toJson(new Result<T>(code, message));
	}
    
    public static <T> String fail(Integer code, String message, T t) {
    	Result<T> result = new Result<T>(code, message, t);
        return new Gson().toJson(result);
	}

}
