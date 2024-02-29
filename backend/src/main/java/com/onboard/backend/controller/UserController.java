package com.onboard.backend.controller;

import com.onboard.backend.dto.UserDto;
import com.onboard.backend.entity.User;
import com.onboard.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("{id}")
//    public User getUser(@PathVariable("id") Long id) {
//        return userService.getUserDetails(id);
//    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
         String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
         userDto.setPassword(encryptedPassword);
         UserDto createdUser = userService.createUser(userDto);
         return new ResponseEntity<>(createdUser, null, 201);
    }

//    @GetMapping()
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
}
