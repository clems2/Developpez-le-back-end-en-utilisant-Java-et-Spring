package com.chatop.api.services;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.mappers.UserMapper;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toEntity(request);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("Registered user: {}", user.getId());
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        log.info("Try to check credentials");
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UnauthorizedException("Invalid crendentials"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Invalid crendentials");
        }
        log.info("User identified with credentials");

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public UserResponse getMe(String email){
        log.info("Try to get myself user");
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UnauthorizedException("User not found"));
        log.info("User found");
        return userMapper.toDto(user);
    }
}
