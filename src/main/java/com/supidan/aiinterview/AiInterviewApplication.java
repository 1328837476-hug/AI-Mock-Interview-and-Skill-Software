package com.supidan.aiinterview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.supidan.aiinterview.mapper")
@SpringBootApplication
public class AiInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiInterviewApplication.class, args);
    }

}
