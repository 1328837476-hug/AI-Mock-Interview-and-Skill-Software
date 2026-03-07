package com.supidan.aiinterview.exception;


import com.supidan.aiinterview.entity.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final int    code;
    private final String message;
    private final String description;

    // 最常用：只传 ErrorCode
    public BizException(ErrorCode errorCode) {
        this.code        = errorCode.getCode();
        this.message     = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }

    // 需要补充描述时
    public BizException(ErrorCode errorCode, String description) {
        this.code        = errorCode.getCode();
        this.message     = errorCode.getMessage();
        this.description = description;
    }
}