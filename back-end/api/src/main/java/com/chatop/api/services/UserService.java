package com.chatop.api.services;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.mappers.UserMapper;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        log.info("User founded with id : {}", id);
        return userMapper.toDto(user);
    }
}
