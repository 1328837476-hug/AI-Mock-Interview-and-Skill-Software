package com.supidan.aiinterview.websocket;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;
import java.util.*;

@Data
public class SessionContext {

    private String     sessionId;
    private Long       userId;
    private String     positionCode;
    private String     stage;              // 当前阶段
    private String     interviewMode;      // GENERAL/PERSONALIZED
    private List<Map<String, String>> messages; // 完整历史对话
    private List<Long> questionIds;        // 本次题目ID列表
    private int        currentQuestionIndex;
    private int        stageTurnCount;     // 当前阶段已对话轮数
    private WebSocketSession wsSession;

    public SessionContext() {
        this.messages             = new ArrayList<>();
        this.stage                = "OPENING";
        this.stageTurnCount       = 0;
        this.currentQuestionIndex = 0;
    }

    public void addMessage(String role, String content) {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", role);
        msg.put("content", content);
        this.messages.add(msg);
    }

    /**
     * 取最近N轮，防止上下文过长撑爆Dify
     */
    public List<Map<String, String>> getRecentMessages(int n) {
        int size = messages.size();
        if (size <= n) return new ArrayList<>(messages);
        return new ArrayList<>(messages.subList(size - n, size));
    }
}