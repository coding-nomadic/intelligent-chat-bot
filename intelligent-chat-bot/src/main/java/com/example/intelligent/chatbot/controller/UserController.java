package com.example.intelligent.chatbot.controller;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUsers(@RequestBody UserDto userDto) {
        final User savedUser = userService.saveUser(userDto);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUsers() {
        final List<User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> fetchUserById(@PathVariable String userId) {
        final Optional<User> users = userService.fetchUserId(userId);
        return users.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new User()));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        userService.deleteUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
