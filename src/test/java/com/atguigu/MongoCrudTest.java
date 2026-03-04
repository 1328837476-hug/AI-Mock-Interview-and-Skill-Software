package com.atguigu;

import com.atguigu.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testInsert() {
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录列表");
        mongoTemplate.insert(chatMessages);
    }

    @Test
    public void testFind() {
        ChatMessages chatMessages = mongoTemplate.findById("696ba16bc806671af8048269", ChatMessages.class);
        System.out.println(chatMessages);
    }

    @Test   
    public void testUpdate() {
        Criteria criteria = Criteria.where("id").is("696ba16bc806671af8048269");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "更新后的聊天记录列表");
        
        mongoTemplate.upsert(query, update, ChatMessages.class);

    }

    @Test
    public void testDelete() {
        Criteria criteria = Criteria.where("id").is("696ba16bc806671af8048269");
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);

    }
}