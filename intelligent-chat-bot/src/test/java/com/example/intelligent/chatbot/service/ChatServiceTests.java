package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.ChatDto;
import com.example.intelligent.chatbot.dto.ChatGPTRequest;
import com.example.intelligent.chatbot.dto.ChatGptResponse;
import com.example.intelligent.chatbot.dto.Message;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.exception.UserNotFoundException;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.utils.ChatStoreUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChatServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ChatStoreUtils chatStoreUtils;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void testProcessMessageAsync_WhenEmptyResponse() throws Exception {
        ChatGptResponse chatGptResponses = mock(ChatGptResponse.class);
        ChatDto chatDto = new ChatDto();
        chatDto.setUserId("12345");
        chatDto.setMessage("Hello");
        List<ChatGptResponse.Choice> lists=listsChatGptResponse();
        when(userRepository.existsById(chatDto.getUserId())).thenReturn(true);
        when(restTemplate.postForObject(any(String.class), any(ChatGPTRequest.class), eq(ChatGptResponse.class))).thenReturn(new ChatGptResponse());
        when(chatGptResponses.getChoices()).thenReturn(lists);
        CompletableFuture<String> future = chatService.processMessageAsync(chatDto);
        String result = future.get();
        assertEquals("", result);
    }


    @Test
    public void testProcessMessageAsync_UserNotFound() {
        ChatDto chatDto = new ChatDto();
        chatDto.setUserId("user123");
        chatDto.setMessage("Hello");
        when(userRepository.existsById(chatDto.getUserId())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> chatService.processMessageAsync(chatDto).join());
    }

    @Test
    public void testProcessMessageAsync_NullResponse() throws Exception {
        ChatDto chatDto = new ChatDto();
        chatDto.setUserId("12345");
        chatDto.setMessage("Hello");
        when(userRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true);
        when(restTemplate.postForObject(any(String.class), any(ChatGPTRequest.class), eq(ChatGptResponse.class))).thenReturn(null);
        CompletableFuture<String> future = chatService.processMessageAsync(chatDto);
        String result = future.get();
        assertEquals("", result);
        verify(chatStoreUtils, never()).saveChatMessage(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void testFetchAllMessageAsync() {
        List<ChatMessage> messages = new ArrayList<>();
        when(chatStoreUtils.chatMessageList()).thenReturn(messages);
        List<ChatMessage> result = chatService.fetchAllMessageAsync();
        assertEquals(messages, result);
    }

    @Test
    public void testFetchMessageByUserIdAsync_UserExists() {
        String userId = "12345";
        List<ChatMessage> messages = new ArrayList<>();
        when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(users()));
        when(chatStoreUtils.chatMessageByUserId(ArgumentMatchers.anyString())).thenReturn(messages);
        List<ChatMessage> result = chatService.fetchMessageByUserIdAsync(userId);
        assertNotNull(result);
    }

    @Test
    public void testFetchMessageByUserIdAsync_UserNotFound() {
        String userId = "12345";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> chatService.fetchMessageByUserIdAsync(userId));
    }

    private List<ChatGptResponse.Choice> listsChatGptResponse() {
        ChatGptResponse response = new ChatGptResponse();
        Message message = new Message();
        message.setContent("");
        message.setRole("");
        ChatGptResponse.Choice choice = new ChatGptResponse.Choice();
        choice.setIndex(1);
        choice.setMessage(message);
        List<ChatGptResponse.Choice> lists = new ArrayList<>();
        lists.add(choice);
        response.setChoices(lists);
        return lists;
    }

    private User users() {
        User user = new User();
        user.setUserName("tendawa123");
        user.setAddress("170 hallmark ave");
        user.setEmail("td@gmail.com");
        user.setFirstName("John");
        user.setLastName("Rambo");
        user.setId("12345");
        return user;
    }
}
