package com.fernando.spring_security_jwt.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtService jwtService;
    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication){
        return jwtService.generateToken(authentication);

    }

}
