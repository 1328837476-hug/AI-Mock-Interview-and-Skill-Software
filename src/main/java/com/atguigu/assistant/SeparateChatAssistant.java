package com.atguigu.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools" // 配置工具
)
public interface SeparateChatAssistant {

    @SystemMessage("你是一个语音助手，请用湖南临湘话回答我。今天是{{current_date}}")
//    @SystemMessage(fromResource = "system_message.txt")
    String chat(@MemoryId String memoryId,@UserMessage String userMessage);

    // @UserMessage默认不能使用这么多参数 需使用@V可实现多参数
    @UserMessage("你是一个语音助手，请用湖南临湘话回答我。今天是{{current_date}}。{{message}}")
    String chat2(@MemoryId String memoryId,@V("message") String message, @V("current_date") String currentDate);
}
