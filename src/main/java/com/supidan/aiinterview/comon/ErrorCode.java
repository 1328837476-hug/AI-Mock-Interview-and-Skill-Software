package com.supidan.aiinterview.comon;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ── 通用 ──────────────────────────────────────────
    SUCCESS(200,        "操作成功",          ""),
    PARAM_ERROR(400,    "请求参数错误",       ""),
    UNAUTHORIZED(401,   "未登录或Token失效",  ""),
    FORBIDDEN(403,      "无权限访问",         ""),
    NOT_FOUND(404,      "资源不存在",         ""),
    DATA_CONFLICT(409,  "数据冲突",           ""),
    SYSTEM_ERROR(500,   "服务器内部错误",     ""),

    // ── AI 服务 ───────────────────────────────────────
    AI_SERVICE_ERROR(600,           "AI服务异常",           ""),
    ASR_FAILED(601,                 "语音识别失败",          ""),
    TTS_FAILED(602,                 "语音合成失败",          ""),
    PDF_PARSE_FAILED(603,           "PDF解析失败",           ""),
    RESUME_NOT_FOUND(604,           "用户未上传简历",        ""),
    INTERVIEW_ONGOING(605,          "已有进行中的面试",      ""),
    STAGE_TRANSITION_DENIED(606,    "非法面试阶段跳转",      "");

    private final int    code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code        = code;
        this.message     = message;
        this.description = description;
    }
}