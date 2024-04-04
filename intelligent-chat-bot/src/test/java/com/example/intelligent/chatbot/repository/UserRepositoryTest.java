package com.example.intelligent.chatbot.repository;

import com.example.intelligent.chatbot.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        User user = new User();
        user.setUserName("");
        user.setId("234");
        userRepository.save(user);
        String userNameWithMultipleResults = "tester34566333";
        assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
            userRepository.findByUserName(userNameWithMultipleResults).orElseThrow();
        });
    }

    @Test
    public void testSaveUser() {
        String userName = "tester1";
        User user = new User();
        user.setUserName(userName);
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
    }

    @Test
    public void testDeleteById() {
        String userId = "tester1245";
        User users = new User();
        users.setId(userId);
        userRepository.save(users);
        userRepository.deleteById(userId);
        assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(userId).orElseThrow();
        });
    }

    @Test
    public void testFindById() {
        String userId = "tester12";
        User users = new User();
        users.setId(userId);
        userRepository.save(users);
        Optional<User> savedUser = userRepository.findById(userId);
        System.out.println(savedUser.get());
        assertNotNull(savedUser.get());
    }
}
