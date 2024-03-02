package com.onboard.backend.service;

import com.onboard.backend.dto.UserDto;

public interface AuthService {

    // create user
    public String createUser(UserDto user);

}
