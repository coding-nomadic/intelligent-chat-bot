package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTests {

    private static final String TENDAWA = "tendawa123";
    private static final String USER_ID = "12345";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void testSaveUsers() {
        Mockito.when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.of(users()));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(users());
        User responseEntity = userService.saveUser(userDtos());
        Assertions.assertNotNull(responseEntity);
        //Mockito.verify(userRepository,Mockito.times(1)).findByUserName(ArgumentMatchers.anyString());
    }

    @Test
    public void testFetchAllUsers() {
        List<User> lists = List.of(users());
        Mockito.when(userRepository.findAll()).thenReturn(lists);
        List<User> users = userService.fetchAllUsers();
        Assertions.assertNotNull(users);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void testFetchByUserId() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(users()));
        Optional<User> users = userService.fetchUserId(USER_ID);
        Assertions.assertNotNull(users);
        Assertions.assertEquals(TENDAWA, users.get().getUserName());
        Mockito.verify(userRepository,Mockito.times(1)).findById(ArgumentMatchers.anyString());
    }

    @Test
    public void testDeleteByUserId() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(users()));
        userService.deleteUserId(USER_ID);
        Mockito.verify(userRepository,Mockito.times(1)).findById(ArgumentMatchers.anyString());
    }

    private UserDto userDtos() {
        UserDto user = new UserDto();
        user.setUserName("tendawa123");
        user.setAddress("170 hallmark ave");
        user.setEmail("td@gmail.com");
        user.setFirstName("John");
        user.setLastName("Rambo");
        user.setId("12345");
        return user;
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
