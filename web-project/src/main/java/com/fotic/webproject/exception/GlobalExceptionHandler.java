package com.fotic.webproject.exception;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fotic.webproject.business.exception.BusinessException;
import com.fotic.webproject.web.CommonResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局的 Controller 层的异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage()).of(ExceptionCodeEnum.EXCEPTION_UNKNOW);
    }


    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResult businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getCause()).of(e.getErrorCode(), e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResult runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage()).of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_500);
    }

    /**
     * 返回数据格式异常
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public CommonResult outDataExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_500)
        		.of("返回数据格式异常, 请联系管理员!");
    }


    /**
     * 非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public CommonResult illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_500)
        		.of("非法参数异常, 请联系管理员!");
    }

    /**
     * 违反约束异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public CommonResult constraintViolationExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_500)
        		.of("违反约束异常, 请联系管理员!");
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public CommonResult nullPointerExceptionHandler(NullPointerException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_NULL_POINTER.getErrorCode())
        		.of("空指针异常, 请联系管理员!");
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public CommonResult classCastExceptionHandler(ClassCastException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_CLASS_CAST);
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public CommonResult ioExceptionHandler(IOException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_IO);
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public CommonResult noSuchMethodExceptionHandler(NoSuchMethodException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_NO_SUCH_METHOD);
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public CommonResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * 未能正确解析参数异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public CommonResult httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400)
        		.of("未能正确解析参数, 请联系管理员!");
    }

    /**
     * 参数类型匹配失败异常
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public CommonResult typeMismatchExceptionHandler(TypeMismatchException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400)
        		.of("参数类型匹配失败异常, 请联系管理员!");
    }

    /**
     * 缺少必要参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public CommonResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400.getErrorCode())
        		.of("缺少必要参数异常, 请联系管理员!");
    }

    /**
     * 请求地址错误异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public CommonResult noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_404)
        		.of("请求地址错误, 请联系管理员!");
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public CommonResult httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_405)
        		.of("缺少必要参数, 请联系管理员!");
    }

    /**
     * 不支持contentType类型异常
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public CommonResult httpMediaTypeNotAcceptableExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_406);
    }

    /**
     * 使用的contentType类型错误
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public CommonResult httpMediaTypeNotSupportedExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_415);
    }


    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return CommonResult.build(e.getMessage())
        		.of(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400)
        		.of(e.getBindingResult().getAllErrors().stream()
        				.map(objectError -> (FieldError) objectError).
                        map(FieldError::getDefaultMessage).
                        reduce((a, b) -> String.join(";", a, b))
                        .orElse(StringUtils.EMPTY));
    }
}