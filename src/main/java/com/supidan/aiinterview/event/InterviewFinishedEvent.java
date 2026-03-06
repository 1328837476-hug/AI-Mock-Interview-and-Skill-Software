package com.supidan.aiinterview.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import java.util.List;
import java.util.Map;

/**
 * 面试结束事件
 * 由 InterviewServiceImpl.endInterview() 发布
 * 由 InterviewFinishedListener 异步监听处理
 */
@Getter
public class InterviewFinishedEvent extends ApplicationEvent {

    /** 面试会话ID */
    private final Long sessionId;

    /** 用户ID */
    private final Long userId;

    /** 岗位代码 */
    private final String positionCode;

    /** 完整对话记录 */
    private final List<Map<String, String>> messages;

    public InterviewFinishedEvent(Object source,
                                   Long sessionId,
                                   Long userId,
                                   String positionCode,
                                   List<Map<String, String>> messages) {
        super(source);
        this.sessionId    = sessionId;
        this.userId       = userId;
        this.positionCode = positionCode;
        this.messages     = messages;
    }
}