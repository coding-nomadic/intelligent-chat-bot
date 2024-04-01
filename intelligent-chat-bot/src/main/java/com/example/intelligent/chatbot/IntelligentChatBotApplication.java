package com.example.intelligent.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IntelligentChatBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentChatBotApplication.class, args);
    }
}
