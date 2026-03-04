package com.atguigu.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 初级智能体
 */
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

    @UserMessage("你是一个语音助手，请用湖南临湘话回答我。今天是{{current_date}}。{{message}}")
    String chat(@V("message") String message);

}
