package com.example.intelligent.chatbot.repository;

import com.example.intelligent.chatbot.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    public List<ChatMessage> findByUserId(String userId);
}
