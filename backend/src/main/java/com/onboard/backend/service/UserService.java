package com.onboard.backend.service;

import com.onboard.backend.dto.UserDto;
import java.util.List;

public interface UserService {

    public UserDto updateUser(Long id, UserDto user);

    public UserDto getUserById(Long id);

    public List<UserDto> getAllUsers();

    public void deleteUser(Long id);

}
/*
 * in this we will create,
 * -update
 * -delete
 * -get
 * -get all
 *
 */