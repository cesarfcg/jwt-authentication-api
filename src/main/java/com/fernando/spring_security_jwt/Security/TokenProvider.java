package com.fernando.spring_security_jwt.Security;

import com.fernando.spring_security_jwt.User.User;
import com.fernando.spring_security_jwt.User.UserRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration-time}")
    private String expirationTime;


    private String buildToken(User username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Long.parseLong(expirationTime));
        return Jwts.builder()
                .setSubject(username.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
}
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}

