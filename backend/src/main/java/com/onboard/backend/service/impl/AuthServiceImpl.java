package com.onboard.backend.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onboard.backend.dto.UserDto;
import com.onboard.backend.entity.User;
import com.onboard.backend.exception.InvalidEmailException;
import com.onboard.backend.exception.UserAlreadyExistsException;
import com.onboard.backend.repository.UserRepository;
import com.onboard.backend.service.AuthService;

import java.security.Key;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String createUser(UserDto userDto) {
        String email = userDto.getEmail();
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email address: " + email);
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        User newUser = this.modelMapper.map(userDto, User.class);
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        userRepository.save(newUser);
        return "User created successfully";
    }

    @Override
    public String signIn(String email, String password) {
        // Retrieve user from the database based on email
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return generateAccessToken(user.getId());
    }

    private String generateAccessToken(Long userId) {
        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}