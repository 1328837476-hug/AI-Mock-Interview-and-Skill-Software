package com.supidan.aiinterview.websocket;

import lombok.Data;

@Data
public class WsMessage {

    // 前端 → 后端
    // USER_TEXT / USER_AUDIO / INTERVIEW_END / PING

    // 后端 → 前端
    // AI_TEXT_CHUNK / AI_AUDIO / ASR_RESULT
    // INTERVIEW_STARTED / STAGE_CHANGED / PONG / ERROR

    private String  type;
    private String  sessionId;
    private String  content;
    private Boolean isEnd;    // 流式文字是否结束
    private String  errorCode;
    private String  stage;    // 阶段变更时携带
}