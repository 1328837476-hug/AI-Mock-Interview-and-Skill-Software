package com.supidan.aiinterview.service.impl;


import com.google.gson.Gson;
import com.supidan.aiinterview.entity.enums.ErrorCode;
import com.supidan.aiinterview.entity.po.InterviewMessage;
import com.supidan.aiinterview.entity.po.InterviewQuestionRecord;
import com.supidan.aiinterview.entity.po.InterviewSession;
import com.supidan.aiinterview.entity.po.Question;
import com.supidan.aiinterview.entity.dto.request.StartInterviewRequest;
import com.supidan.aiinterview.entity.dto.response.StartInterviewResponse;
import com.supidan.aiinterview.event.InterviewFinishedEvent;
import com.supidan.aiinterview.exception.BizException;
import com.supidan.aiinterview.mapper.InterviewMessageMapper;
import com.supidan.aiinterview.mapper.InterviewQuestionRecordMapper;
import com.supidan.aiinterview.mapper.InterviewSessionMapper;
import com.supidan.aiinterview.mapper.QuestionMapper;
import com.supidan.aiinterview.service.InterviewService;
import com.supidan.aiinterview.service.QuestionService;
import com.supidan.aiinterview.websocket.InterviewSessionManager;
import com.supidan.aiinterview.websocket.SessionContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewSessionMapper sessionMapper;
    private final InterviewMessageMapper messageMapper;
    private final InterviewQuestionRecordMapper recordMapper;
    private final QuestionService questionService;
    private final InterviewSessionManager sessionManager;
    private final ApplicationEventPublisher eventPublisher;
    private final QuestionMapper questionMapper;
    private final Gson gson = new Gson();

    @Override
    public StartInterviewResponse startInterview(
            StartInterviewRequest req, Long userId) {

        // 1. 检查是否有进行中的面试
        InterviewSession ongoing =
            sessionMapper.selectOngoingByUserId(userId);
        if (ongoing != null) {
            throw new BizException(
                ErrorCode.INTERVIEW_ONGOING, "请先结束当前面试");
        }

        // 2. 抽题（含历史去重）
        List<Question> questions =
            questionService.selectQuestions(req.getPositionCode(), userId);

        List<Long> questionIds = questions.stream()
            .map(Question::getId)
            .collect(Collectors.toList());

        // 3. 创建会话记录
        InterviewSession session = new InterviewSession();
        session.setUserId(userId);
        session.setPositionCode(req.getPositionCode());
        session.setStatus("ONGOING");
        session.setStage("OPENING");
        session.setInterviewMode("GENERAL");
        session.setAudioEnabled(req.getAudioEnabled());
        session.setTotalTurns(0);
        session.setQuestionIds(gson.toJson(questionIds));
        session.setStartTime(LocalDateTime.now());
        sessionMapper.insert(session);

        // 4. 批量写入题目使用记录
        questions.forEach(q -> {
            InterviewQuestionRecord record =
                new InterviewQuestionRecord();
            record.setSessionId(session.getId());
            record.setQuestionId(q.getId());
            record.setIsFollowup(false);
            recordMapper.insert(record);
        });

        // 5. 初始化内存SessionContext
        SessionContext ctx = new SessionContext();
        ctx.setSessionId(String.valueOf(session.getId()));
        ctx.setUserId(userId);
        ctx.setPositionCode(req.getPositionCode());
        ctx.setInterviewMode("GENERAL");
        ctx.setQuestionIds(questionIds);
        sessionManager.put(String.valueOf(session.getId()), ctx);

        log.info("面试开始 sessionId={} userId={} position={} 共{}道题",
            session.getId(), userId,
            req.getPositionCode(), questions.size());

        return new StartInterviewResponse(
            String.valueOf(session.getId()),
            "GENERAL",
            req.getPositionCode(),
            "ws://localhost:8080/ws/interview/" + session.getId()
        );
    }

    @Override
    public void endInterview(Long sessionId, Long userId) {
        // 1. 校验会话归属
        InterviewSession session = sessionMapper.selectById(sessionId);
        if (session == null || !session.getUserId().equals(userId)) {
            throw new BizException(
                ErrorCode.NOT_FOUND, "面试会话不存在");
        }
        if ("FINISHED".equals(session.getStatus())) {
            throw new BizException(
                ErrorCode.PARAM_ERROR, "面试已结束");
        }

        // 2. 更新会话状态
        session.setStatus("FINISHED");
        session.setEndTime(LocalDateTime.now());
        long seconds = java.time.Duration.between(
            session.getStartTime(),
            session.getEndTime()).getSeconds();
        session.setDurationSeconds((int) seconds);
        sessionMapper.updateById(session);

        // 3. 取内存中的完整对话记录
        SessionContext ctx = sessionManager.get(
            String.valueOf(sessionId));

        // 4. 保存全量消息到DB
        if (ctx != null) {
            saveAllMessages(sessionId, ctx);
        }

        // 5. 发布事件，异步触发评估
        eventPublisher.publishEvent(new InterviewFinishedEvent(
            this, sessionId, userId,
            session.getPositionCode(),
            ctx != null ? ctx.getMessages() : List.of()
        ));

        // 6. 清理内存
        sessionManager.remove(String.valueOf(sessionId));

        // 7. 更新题目使用次数
        updateQuestionUseCount(sessionId);

        log.info("面试结束 sessionId={} 时长{}秒",
            sessionId, seconds);
    }

    // ── 私有方法 ──────────────────────────────────────

    private void saveAllMessages(
            Long sessionId, SessionContext ctx) {
        List<InterviewMessage> dbMessages =
            messageMapper.selectBySessionId(sessionId);

        // 只保存内存中比DB多的消息（避免重复插入）
        int savedCount = dbMessages.size();
        List<java.util.Map<String, String>> allMessages =
            ctx.getMessages();

        for (int i = savedCount; i < allMessages.size(); i++) {
            var msg = allMessages.get(i);
            InterviewMessage entity = new InterviewMessage();
            entity.setSessionId(sessionId);
            entity.setRole(msg.get("role"));
            entity.setContent(msg.get("content"));
            entity.setInputType("TEXT");
            entity.setSequence(i);
            messageMapper.insert(entity);
        }
    }

    private void updateQuestionUseCount(Long sessionId) {
        List<Long> questionIds =
                recordMapper.selectQuestionIdsBySessionId(sessionId);
        if (questionIds == null || questionIds.isEmpty()) return;

        questionIds.forEach(qId -> {
            Question q = questionMapper.selectById(qId);
            if (q != null) {
                q.setUseCount(q.getUseCount() + 1);
                questionMapper.updateById(q);
            }
        });
    }
}