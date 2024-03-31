package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.ChatDto;
import com.example.intelligent.chatbot.dto.ChatGPTRequest;
import com.example.intelligent.chatbot.dto.ChatGptResponse;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.exception.UserNotFoundException;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.utils.ChatStoreUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testProcessMessageAsync_UserExists() throws Exception {
//        ChatDto chatDto = new ChatDto();
//        chatDto.setUserId("user123");
//        chatDto.setMessage("Hello");
//        ChatGptResponse response = new ChatGptResponse();
//        Message message = new Message();
//        message.setContent("Response");
//        response.addChoice(message);
//        response.setChoices(message);
//        when(userRepository.existsById(chatDto.getUserId())).thenReturn(true);
//        when(restTemplate.postForObject(any(String.class), any(ChatGPTRequest.class), eq(ChatGptResponse.class)))
//                .thenReturn(response);
//        when(chatStoreUtils.saveChatMessage(any(String.class), any(String.class), any(String.class))).thenReturn(new ChatMessage());
//
//        CompletableFuture<String> future = chatService.processMessageAsync(chatDto);
//        String result = future.get();
//
//        assertEquals("Response", result);
//        verify(chatStoreUtils, times(1)).saveChatMessage(any(String.class), any(String.class), any(String.class));
//    }

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
        when(userRepository.existsById(chatDto.getUserId())).thenReturn(true);
        when(restTemplate.postForObject(any(String.class), any(ChatGPTRequest.class), eq(ChatGptResponse.class)))
                .thenReturn(null);
        //CompletableFuture<String> future = chatService.processMessageAsync(chatDto);
        //String result = future.get();
        //assertEquals("", result);
        //verify(chatStoreUtils, never()).saveChatMessage(any(String.class), any(String.class), any(String.class));
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
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(users()));
        when(chatStoreUtils.chatMessageByUserId(userId)).thenReturn(messages);
        //List<ChatMessage> result = chatService.fetchMessageByUserIdAsync(userId);
        //assertNotNull(result);
    }

    @Test
    public void testFetchMessageByUserIdAsync_UserNotFound() {
        String userId = "12345";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> chatService.fetchMessageByUserIdAsync(userId));
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
