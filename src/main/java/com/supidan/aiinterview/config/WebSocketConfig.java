package com.supidan.aiinterview.config;

import com.supidan.aiinterview.websocket.InterviewWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final InterviewWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry) {
        registry
            .addHandler(handler, "/ws/interview/{sessionId}")
            .setAllowedOrigins("*");
    }
}