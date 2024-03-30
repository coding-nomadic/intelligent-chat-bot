package com.example.intelligent.chatbot.utils;

import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.repository.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatStoreUtils {

    private static final Logger logger = LoggerFactory.getLogger(ChatStoreUtils.class);
    @Autowired
    private ChatMessageRepository chatMessageRepository;


    public void saveChatMessage(String userId, String message, String response) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(userId);
        chatMessage.setMessage(message);
        chatMessage.setResponse(response);
        chatMessageRepository.save(chatMessage);
        logger.debug("Saved chat message: {}", chatMessage);
    }


    public List<ChatMessage> chatMessageList() {
        return chatMessageRepository.findAll();
    }


    public List<ChatMessage> chatMessageByUserId(String userId) {
        return chatMessageRepository.findByUserId(userId);
    }
}
