package com.fernando.spring_security_jwt.User;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }
    public UserResponseDto findUser(Authentication authentication){
        String email = authentication.getName();
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
