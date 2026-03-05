package com.supidan.aiinterview.exception;


import com.supidan.aiinterview.comon.BaseResponse;
import com.supidan.aiinterview.comon.ErrorCode;
import com.supidan.aiinterview.comon.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 业务异常
    @ExceptionHandler(BizException.class)
    public BaseResponse<?> handleBizException(BizException e) {
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    // @RequestBody @Valid 校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleValidException(MethodArgumentNotValidException e) {
        String description = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResultUtils.error(ErrorCode.PARAM_ERROR, description);
    }

    // 表单参数绑定失败
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> handleBindException(BindException e) {
        String description = e.getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResultUtils.error(ErrorCode.PARAM_ERROR, description);
    }

    // 兜底系统异常
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}