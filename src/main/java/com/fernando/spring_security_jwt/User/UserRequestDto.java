package com.fernando.spring_security_jwt.User;

public record UserRequestDto(
        String username,
        String password,
        UserRole role) {
}
