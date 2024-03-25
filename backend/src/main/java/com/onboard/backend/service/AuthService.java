package com.onboard.backend.service;

import com.onboard.backend.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    // create user
    public String createUser(UserDto user);

    public String signIn(String email, String password);

}
