package com.fernando.spring_security_jwt.User;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }
}
