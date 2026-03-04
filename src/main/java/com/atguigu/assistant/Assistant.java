package com.atguigu.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel="qwenChatModel"
         ) // 这个注解会让这个接口自动注入到spring容器中
public interface Assistant {

    String chat(String userMapper);
}
