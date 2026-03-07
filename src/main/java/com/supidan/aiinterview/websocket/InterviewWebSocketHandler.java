package com.supidan.aiinterview.websocket;

import com.google.gson.Gson;
import com.supidan.aiinterview.ai.DifyClient;
import com.supidan.aiinterview.entity.po.InterviewMessage;
import com.supidan.aiinterview.mapper.InterviewMessageMapper;
import com.supidan.aiinterview.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewWebSocketHandler
        extends TextWebSocketHandler {

    private final InterviewSessionManager sessionManager;
    private final DifyClient              difyClient;
    private final InterviewMessageMapper  messageMapper;
    private final QuestionMapper          questionMapper;
    private final Gson gson = new Gson();

    // 各阶段最大轮数
    private static final Map<String, Integer> STAGE_THRESHOLD =
        Map.of(
            "OPENING",    2,
            "TECHNICAL",  6,
            "SCENARIO",   3,
            "BEHAVIORAL", 2,
            "CLOSING",    99
        );

    private static final List<String> STAGE_ORDER = List.of(
        "OPENING", "TECHNICAL",
        "SCENARIO", "BEHAVIORAL", "CLOSING"
    );

    // ── 连接建立 ──────────────────────────────────────
    @Override
    public void afterConnectionEstablished(
            WebSocketSession wsSession) throws Exception {

        String sessionId = extractSessionId(wsSession);
        SessionContext ctx = sessionManager.get(sessionId);

        if (ctx == null) {
            sendError(wsSession, "SESSION_NOT_FOUND",
                "面试会话不存在");
            wsSession.close();
            return;
        }

        ctx.setWsSession(wsSession);
        log.info("WebSocket连接建立 sessionId={}", sessionId);

        // 发送面试开始消息
        WsMessage startMsg = new WsMessage();
        startMsg.setType("INTERVIEW_STARTED");
        startMsg.setContent("连接成功，面试即将开始");
        sendMessage(wsSession, startMsg);

        // 触发AI开场白
        sendOpeningMessage(ctx);
    }

    // ── 消息处理 ──────────────────────────────────────
    @Override
    protected void handleTextMessage(WebSocketSession wsSession,
                                      TextMessage message)
            throws Exception {

        WsMessage msg = gson.fromJson(
            message.getPayload(), WsMessage.class);
        String sessionId = msg.getSessionId();
        SessionContext ctx = sessionManager.get(sessionId);

        if (ctx == null) {
            sendError(wsSession, "SESSION_NOT_FOUND",
                "会话不存在或已过期");
            return;
        }

        switch (msg.getType()) {
            case "USER_TEXT"     ->
                handleUserText(ctx, msg.getContent());
            case "INTERVIEW_END" ->
                handleInterviewEnd(ctx);
            case "PING"          ->
                sendPong(wsSession);
            default ->
                log.warn("未知消息类型: {}", msg.getType());
        }
    }

    // ── 连接关闭 ──────────────────────────────────────
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                       CloseStatus status) {
        log.info("WebSocket连接关闭 status={}", status);
    }

    // ── 处理用户文字输入 ──────────────────────────────
    private void handleUserText(
            SessionContext ctx, String content) {

        if (content == null || content.trim().isEmpty()) return;

        // 1. 实时保存用户消息到DB
        saveMessage(ctx, "user", content);

        // 2. 加入内存上下文
        ctx.addMessage("user", content);

        // 3. 阶段轮数+1，检查是否切换阶段
        ctx.setStageTurnCount(ctx.getStageTurnCount() + 1);
        checkAndSwitchStage(ctx);

        // 4. 构造Prompt + 历史上下文
        String systemPrompt = buildSystemPrompt(ctx);
        List<Map<String, String>> difyMessages =
            buildDifyMessages(ctx, systemPrompt);

        // 5. 流式调用Dify（自动推送AI_TEXT_CHUNK给前端）
        String aiReply = difyClient.streamChat(
            null, difyMessages, ctx.getWsSession());

        // 6. 保存AI回复到DB和内存
        if (aiReply != null && !aiReply.isEmpty()) {
            saveMessage(ctx, "assistant", aiReply);
            ctx.addMessage("assistant", aiReply);
        }

        // 7. 刷新Redis TTL
        sessionManager.refreshTtl(ctx.getSessionId());
    }

    // ── 处理结束面试 ──────────────────────────────────
    private void handleInterviewEnd(SessionContext ctx) {
        WsMessage endMsg = new WsMessage();
        endMsg.setType("INTERVIEW_ENDED");
        endMsg.setContent("面试已结束，正在生成报告，请稍候...");
        sendMessage(ctx.getWsSession(), endMsg);
        // 真正的结束逻辑由 HTTP POST /interview/{id}/end 触发
    }

    // ── AI开场白 ──────────────────────────────────────
    private void sendOpeningMessage(SessionContext ctx) {
        String prompt = String.format(
            "你是一位专业的%s岗位技术面试官。" +
            "请用友好专业的方式开始面试，" +
            "先简短自我介绍，然后请候选人进行自我介绍。" +
            "保持简洁，不超过80字。",
            ctx.getPositionCode()
        );

        List<Map<String, String>> messages = List.of(
            Map.of("role", "user", "content", prompt)
        );

        String opening = difyClient.streamChat(
            null, messages, ctx.getWsSession());

        if (opening != null && !opening.isEmpty()) {
            ctx.addMessage("assistant", opening);
            saveMessage(ctx, "assistant", opening);
        }
    }

    // ── 阶段切换 ──────────────────────────────────────
    private void checkAndSwitchStage(SessionContext ctx) {
        String currentStage = ctx.getStage();
        int threshold =
            STAGE_THRESHOLD.getOrDefault(currentStage, 99);

        if (ctx.getStageTurnCount() >= threshold) {
            int idx = STAGE_ORDER.indexOf(currentStage);
            if (idx < STAGE_ORDER.size() - 1) {
                String nextStage = STAGE_ORDER.get(idx + 1);
                ctx.setStage(nextStage);
                ctx.setStageTurnCount(0);

                // 通知前端阶段变更
                WsMessage stageMsg = new WsMessage();
                stageMsg.setType("STAGE_CHANGED");
                stageMsg.setStage(nextStage);
                sendMessage(ctx.getWsSession(), stageMsg);

                log.info("面试阶段切换 {} → {} sessionId={}",
                    currentStage, nextStage, ctx.getSessionId());
            }
        }
    }

    // ── Prompt构造 ────────────────────────────────────
    private String buildSystemPrompt(SessionContext ctx) {
        String currentQuestion = getCurrentQuestion(ctx);

        return String.format(
            "你是一位专业的%s岗位技术面试官。\n" +
            "当前面试阶段：%s\n" +
            "%s\n" +
            "行为准则：\n" +
            "1. 每次只问一个问题\n" +
            "2. 根据候选人回答关键词进行深度追问\n" +
            "3. 回答不完整时适当引导\n" +
            "4. 保持专业友好，回复不超过120字",
            ctx.getPositionCode(),
            ctx.getStage(),
            currentQuestion.isEmpty()
                ? ""
                : "当前考察题目：" + currentQuestion
        );
    }

    private String getCurrentQuestion(SessionContext ctx) {
        List<Long> ids = ctx.getQuestionIds();
        int idx = ctx.getCurrentQuestionIndex();
        if (ids == null || ids.isEmpty() || idx >= ids.size()) {
            return "";
        }
        var q = questionMapper.selectById(ids.get(idx));
        return q != null ? q.getContent() : "";
    }

    private List<Map<String, String>> buildDifyMessages(
            SessionContext ctx, String systemPrompt) {

        List<Map<String, String>> result = new ArrayList<>();
        result.add(Map.of("role", "system",
            "content", systemPrompt));
        // 取最近10轮历史防止上下文过长
        result.addAll(ctx.getRecentMessages(10));
        return result;
    }

    // ── 工具方法 ──────────────────────────────────────
    private void saveMessage(
            SessionContext ctx, String role, String content) {
        InterviewMessage msg = new InterviewMessage();
        msg.setSessionId(Long.parseLong(ctx.getSessionId()));
        msg.setRole(role);
        msg.setContent(content);
        msg.setInputType("TEXT");
        msg.setSequence(ctx.getMessages().size());
        messageMapper.insert(msg);
    }

    private void sendMessage(
            WebSocketSession session, WsMessage msg) {
        if (session == null || !session.isOpen()) return;
        try {
            session.sendMessage(
                new TextMessage(gson.toJson(msg)));
        } catch (Exception e) {
            log.warn("推送消息失败: {}", e.getMessage());
        }
    }

    private void sendError(WebSocketSession session,
                            String errorCode, String message) {
        WsMessage msg = new WsMessage();
        msg.setType("ERROR");
        msg.setErrorCode(errorCode);
        msg.setContent(message);
        sendMessage(session, msg);
    }

    private void sendPong(WebSocketSession session) {
        WsMessage pong = new WsMessage();
        pong.setType("PONG");
        sendMessage(session, pong);
    }

    private String extractSessionId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}