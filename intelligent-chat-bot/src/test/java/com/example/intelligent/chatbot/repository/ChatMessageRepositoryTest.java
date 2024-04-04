package com.example.intelligent.chatbot.repository;

import com.example.intelligent.chatbot.entity.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    public void testFindByUserId() {
        ChatMessage message1 = new ChatMessage("user1", "Hello", "test", "test");
        chatMessageRepository.save(message1);
        List<ChatMessage> messagesByUser1 = chatMessageRepository.findByUserId("user1");
        assertEquals(0, messagesByUser1.size());
    }

    @Test
    public void testFindByUserId_NoMessagesFound() {
        List<ChatMessage> messages = chatMessageRepository.findByUserId("nonexistent_user");
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }
}
