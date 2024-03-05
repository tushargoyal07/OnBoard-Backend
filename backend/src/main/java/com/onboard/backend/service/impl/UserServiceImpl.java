package com.onboard.backend.service.impl;

import com.onboard.backend.dto.UserDto;
import com.onboard.backend.entity.User;
import com.onboard.backend.exception.UserNotFoundException;
import com.onboard.backend.repository.UserRepository;
import com.onboard.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserById(Long id) {
        @SuppressWarnings("null")
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id does not exist"));
        return convertToDtoWithoutPassword(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDtoWithoutPassword).toList();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserDto userExisting = getUserById(id);
        // if (userExisting == null) {
        // throw exception("User not found");
        // }
        if (userDto.getFirstname() != null)
            userExisting.setFirstname(userDto.getFirstname());
        if (userDto.getLastname() != null)
            userExisting.setLastname(userDto.getLastname());
        if (userDto.getEmail() != null)
            userExisting.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
            userExisting.setPassword(encryptedPassword);
        }
        User userUpdated = this.modelMapper.map(userExisting, User.class);
        @SuppressWarnings("null")
        User savedUser = userRepository.save(userUpdated);
        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User convertToEntity(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto convertToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    private UserDto convertToDtoWithoutPassword(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto dtoWithoutPassword = new UserDto();
        dtoWithoutPassword.setId(userDto.getId());
        dtoWithoutPassword.setFirstname(userDto.getFirstname());
        dtoWithoutPassword.setLastname(userDto.getLastname());
        dtoWithoutPassword.setEmail(userDto.getEmail());
        dtoWithoutPassword.setPassword(null);
        return dtoWithoutPassword;
    }

}
