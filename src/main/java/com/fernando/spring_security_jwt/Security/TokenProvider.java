package com.fernando.spring_security_jwt.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fernando.spring_security_jwt.User.User;
import com.fernando.spring_security_jwt.User.UserRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()

                .withSubject(user.getUsername())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(3600)) // 1 hour
                .sign(algorithm);

    }
}
