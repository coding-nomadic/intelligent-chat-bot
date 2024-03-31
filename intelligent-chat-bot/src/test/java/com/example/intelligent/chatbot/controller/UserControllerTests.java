package com.example.intelligent.chatbot.controller;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserService userService;

    @Autowired
    private UserController userController;

    @Mock
    private UserRepository userRepository;


    @Test
    public void testCreateUsers() {
        UserDto userDto = new UserDto();
        User savedUser = new User();
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(savedUser);
        when(userService.saveUser(userDto)).thenReturn(savedUser);
        ResponseEntity<User> responseEntity = userController.createUsers(userDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(savedUser, responseEntity.getBody());
    }

    @Test
    public void testFetchAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.fetchAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = userController.fetchAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(users, responseEntity.getBody());
    }
}
