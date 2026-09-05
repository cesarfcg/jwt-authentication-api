package com.fernando.spring_security_jwt.User;

public record UserResponseDto(
        Long id,
        String username,
        UserRole role) {
}
