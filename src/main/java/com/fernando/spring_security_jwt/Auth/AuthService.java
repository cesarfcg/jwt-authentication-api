package com.fernando.spring_security_jwt.Auth;

import com.fernando.spring_security_jwt.Security.JwtService;
import com.fernando.spring_security_jwt.User.User;
import com.fernando.spring_security_jwt.User.UserRepository;
import com.fernando.spring_security_jwt.User.UserRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User register(UserRequestDto userRequestDto){
        User newUser = new User();
        newUser.setUsername(userRequestDto.username());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.password()));
        newUser.setRole(userRequestDto.role());
        return userRepository.save(newUser);
    }
    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
