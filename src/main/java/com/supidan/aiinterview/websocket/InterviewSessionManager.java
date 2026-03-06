package com.supidan.aiinterview.websocket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewSessionManager {

    // 内存存储（主要读写）
    private final ConcurrentHashMap<String, SessionContext>
        localSessions = new ConcurrentHashMap<>();

    private final StringRedisTemplate redisTemplate;
    private final Gson gson = new Gson();

    // Redis Key前缀，TTL 2小时
    private static final String REDIS_PREFIX = "interview:session:";
    private static final long   TTL_HOURS    = 2;

    public void put(String sessionId, SessionContext ctx) {
        localSessions.put(sessionId, ctx);
        // 同步写Redis（仅存轻量数据，不存wsSession）
        syncToRedis(sessionId, ctx);
    }

    public SessionContext get(String sessionId) {
        return localSessions.get(sessionId);
    }

    public void remove(String sessionId) {
        localSessions.remove(sessionId);
        redisTemplate.delete(REDIS_PREFIX + sessionId);
    }

    public boolean exists(String sessionId) {
        return localSessions.containsKey(sessionId);
    }

    /**
     * 刷新Redis TTL（每次对话后调用）
     */
    public void refreshTtl(String sessionId) {
        redisTemplate.expire(
            REDIS_PREFIX + sessionId, TTL_HOURS, TimeUnit.HOURS);
    }

    private void syncToRedis(String sessionId, SessionContext ctx) {
        try {
            // 创建轻量副本（不含wsSession）
            RedisSessionSnapshot snapshot = new RedisSessionSnapshot();
            snapshot.setSessionId(ctx.getSessionId());
            snapshot.setUserId(ctx.getUserId());
            snapshot.setPositionCode(ctx.getPositionCode());
            snapshot.setStage(ctx.getStage());
            snapshot.setMessages(ctx.getMessages());
            snapshot.setQuestionIds(ctx.getQuestionIds());
            snapshot.setStageTurnCount(ctx.getStageTurnCount());

            redisTemplate.opsForValue().set(
                REDIS_PREFIX + sessionId,
                gson.toJson(snapshot),
                TTL_HOURS, TimeUnit.HOURS);
        } catch (Exception e) {
            log.warn("同步Redis失败 sessionId={}", sessionId, e);
        }
    }

    /**
     * Redis快照类（不含WebSocketSession）
     */
    @lombok.Data
    public static class RedisSessionSnapshot {
        private String sessionId;
        private Long userId;
        private String positionCode;
        private String stage;
        private java.util.List<java.util.Map<String, String>> messages;
        private java.util.List<Long> questionIds;
        private int stageTurnCount;
    }
}