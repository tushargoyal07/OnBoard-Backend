package com.onboard.backend.controller;

import com.onboard.backend.entity.User;
import com.onboard.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
     private BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getDetails(@PathVariable("id") Long id) {
        return userService.getUserDetails(id);
    }

    @PostMapping("/create")
    public String createDetails(@RequestBody User user) {
         String encryptedPassword = passwordEncoder.encode(user.getPassword());
         user.setPassword(encryptedPassword);
        userService.createUser(user);
        return "User created";
    }

    @GetMapping()
    public List<User> getAllUserDetails() {
        return userService.getAllUsers();
    }
}
