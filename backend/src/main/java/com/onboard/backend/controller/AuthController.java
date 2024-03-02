package com.onboard.backend.controller;

import com.onboard.backend.dto.UserDto;
import com.onboard.backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.createUser(userDto), HttpStatus.CREATED);
    }

    // login
    /*
    * check mail present
    * password match
    * token generation
    * return token
    */
    // @PostMapping("/login")
    // public ResponseEntity<String> login(@RequestBody UserDto userDto) {
    // return new ResponseEntity<>(authService.createUser(userDto),
    // HttpStatus.CREATED);
    // }

    // google auth

    // log out

}
