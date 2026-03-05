package com.supidan.aiinterview.comon;

public class ResultUtils {

    // 成功
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "ok");
    }

    // 成功（无数据）
    public static BaseResponse<Void> success() {
        return new BaseResponse<>(200, null, "ok");
    }

    // 失败：直接传 ErrorCode
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    // 失败：ErrorCode + 补充描述
    public static BaseResponse<?> error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(
                errorCode.getCode(), null,
                errorCode.getMessage(), description);
    }

    // 失败：完全自定义
    public static BaseResponse<?> error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }
}