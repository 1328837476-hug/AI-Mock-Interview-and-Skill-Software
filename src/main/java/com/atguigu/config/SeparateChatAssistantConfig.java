package com.atguigu.config;

import com.atguigu.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  SeparateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(10)
                //.chatMemoryStore(new InMemoryChatMemoryStore()) // 聊天记忆以键值对的形式存储在内存中 持久化聊天记忆
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }
}
