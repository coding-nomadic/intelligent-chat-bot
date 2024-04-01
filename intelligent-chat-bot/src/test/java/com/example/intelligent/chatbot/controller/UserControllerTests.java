package com.example.intelligent.chatbot.controller;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTests {

    private static final String TENDAWA = "tendawa123";
    private static final String USER_ID = "12345";
    @Mock
    private UserService userService;

    @InjectMocks
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
        assertNotNull(responseEntity.getBody());
        verify(userService, times(1)).fetchAllUsers();
    }

    @Test
    public void testFetchUserById() {
        when(userService.fetchUserId(ArgumentMatchers.anyString())).thenReturn(Optional.of(users()));
        ResponseEntity<User> responseEntity = userController.fetchUserById(USER_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        assertNotNull(responseEntity.getBody());
        assertEquals(TENDAWA, responseEntity.getBody().getUserName());
        verify(userService, times(1)).fetchUserId(ArgumentMatchers.anyString());
    }

    @Test
    public void testDeleteByUserById() {
        ResponseEntity<Void> responseEntity = userController.deleteUserById(ArgumentMatchers.anyString());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
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
