package com.fernando.spring_security_jwt.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenConfig {
    @Value("${jwt.secret}")
    private String secret;
}
