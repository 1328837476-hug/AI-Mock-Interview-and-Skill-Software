package com.supidan.aiinterview.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 面试结束事件监听器
 * @Async 异步执行，不阻塞主线程
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewFinishedListener {

    // 阶段一暂时注释掉，等 ReportService 创建后再开启
    // private final ReportService reportService;

    @Async
    @EventListener
    public void onInterviewFinished(
            InterviewFinishedEvent event) {
        log.info("收到面试结束事件 sessionId={}",
            event.getSessionId());

        // TODO 阶段一末期开启
        // reportService.generateReport(
        //     event.getSessionId(),
        //     event.getUserId(),
        //     event.getPositionCode(),
        //     event.getMessages()
        // );
    }
}