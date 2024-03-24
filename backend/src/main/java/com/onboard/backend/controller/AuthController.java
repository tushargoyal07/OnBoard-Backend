package com.onboard.backend.controller;

import com.onboard.backend.dto.LoginDto;
import com.onboard.backend.dto.UserDto;
import com.onboard.backend.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        String token = authService.signIn(email, password);

        if (token != null) {
            Cookie cookie = new Cookie("access_token", token);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            response.addCookie(cookie);

            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // google auth

    // log out

}

// google auth

// log out
