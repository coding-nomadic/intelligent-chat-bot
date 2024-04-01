package com.example.intelligent.chatbot.controller;

import com.example.intelligent.chatbot.dto.ChatDto;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.exception.ChatProcessException;
import com.example.intelligent.chatbot.exception.UserNotFoundException;
import com.example.intelligent.chatbot.service.ChatService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChatControllerTests {
    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @Test
    public void testChatMessage_Success() throws Exception {
        ChatDto chatDto = new ChatDto();
        chatDto.setMessage("Hello");
        String userId = "user123";
        when(chatService.processMessageAsync(chatDto)).thenReturn(CompletableFuture.completedFuture("Response"));
        ResponseEntity<ChatDto> response = chatController.chatMessage(chatDto).join();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response", response.getBody().getMessage());
        verify(chatService, times(1)).processMessageAsync(chatDto);
    }

    @Test
    public void testChatMessage_Exception() {
        ChatDto chatDto = new ChatDto();
        chatDto.setMessage("Hello");
        when(chatService.processMessageAsync(chatDto)).thenThrow(new UserNotFoundException("User not found","102"));
        try {
            chatController.chatMessage(chatDto).join();
        } catch (Exception e) {
            assertEquals(ChatProcessException.class, e.getCause().getClass());
            assertEquals("User not found", e.getCause().getMessage());
        }
    }

    @Test
    public void testFetchAllMessages_Success() throws Exception {
        List<ChatMessage> messages = new ArrayList<>();
        when(chatService.fetchAllMessageAsync()).thenReturn(messages);
        ResponseEntity<List<ChatMessage>> response = chatController.fetchAllMessages().join();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
        verify(chatService, times(1)).fetchAllMessageAsync();
    }

    @Test
    public void testFetchMessageByUserId_Success() throws Exception {
        String userId = "user123";
        List<ChatMessage> messages = new ArrayList<>();
        when(chatService.fetchMessageByUserIdAsync(userId)).thenReturn(messages);
        ResponseEntity<List<ChatMessage>> response = chatController.fetchMessageByUserId(userId).join();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
        verify(chatService, times(1)).fetchMessageByUserIdAsync(userId);
    }
}
